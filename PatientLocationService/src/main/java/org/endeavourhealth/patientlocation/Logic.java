package org.endeavourhealth.patientlocation;

import com.google.common.base.Strings;
import org.endeavourhealth.common.cache.ParserPool;
import org.endeavourhealth.core.database.dal.DalProvider;
import org.endeavourhealth.core.database.dal.eds.PatientSearchDalI;
import org.endeavourhealth.core.database.dal.ehr.ResourceDalI;
import org.endeavourhealth.core.database.dal.ehr.models.ResourceWrapper;
import org.endeavourhealth.core.database.dal.reference.EncounterCodeDalI;
import org.endeavourhealth.core.database.dal.reference.models.EncounterCode;
import org.endeavourhealth.patientlocation.dal.MPI_DAL;
import org.endeavourhealth.patientlocation.models.ActiveEncounter;
import org.endeavourhealth.patientlocation.models.RegisteredPatient;
import org.hl7.fhir.instance.model.Encounter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Logic {
    private static final Logger LOG = LoggerFactory.getLogger(Logic.class);
    // TODO: Set correct concept Id's based on Davids map
    private static final Long INPATIENT_ADMISSION_CONCEPT = 8003L;
    private static final Long INPATIENT_DISCHARGE_CONCEPT = 8004L;
    private static final Long OUTPATIENT_ADMISSION_CONCEPT = 8005L;
    private static final Long OUTPATIENT_DISCHARGE_CONCEPT = 8006L;
    private static final Long EMERGENCY_ADMISSION_CONCEPT = 8007L;
    private static final Long EMERGENCY_DISCHARGE_CONCEPT = 8008L;

    public void execute() {
        List<RegisteredPatient> registeredPatients = getRegisteredPatientList();

        if (registeredPatients == null || registeredPatients.size() == 0)
            return;

        for (RegisteredPatient patient : registeredPatients) {
            try {
                List<ActiveEncounter> activeEncounters = getPatientActiveEncounters(patient);
                // TODO: Put active encounters into own database
            } catch (Exception e) {
                LOG.error("Unable to get active encounters for patient " + patient.getPatientId(), e);
            }
        }
    }

    private List<RegisteredPatient> getRegisteredPatientList() {
        MPI_DAL patientDal = new MPI_DAL();
        return patientDal.getRegisteredPatients();
    }

    private List<ActiveEncounter> getPatientActiveEncounters(RegisteredPatient patient) throws Exception {
        Encounter latestInpatientAdmission = null;
        Encounter latestInpatientDischarge = null;
        Encounter latestOutpatientAdmission = null;
        Encounter latestOutpatientDischarge = null;
        Encounter latestEmergencyAdmission = null;
        Encounter latestEmergencyDischarge = null;

        List<ActiveEncounter> activeEncounters = new ArrayList<>();

        List<ResourceWrapper> encounters = getPatientEncounters(patient);
        if (encounters == null || encounters.size() == 0)
            return activeEncounters;

        for (ResourceWrapper encounter: encounters) {
                Encounter fhirEncounter = (Encounter)ParserPool.getInstance().parse("Encounter", encounter.getResourceData());
                Long conceptId = getConceptId(fhirEncounter);

                if (INPATIENT_ADMISSION_CONCEPT.equals(conceptId))
                    latestInpatientAdmission = mostRecentEncounter(latestInpatientAdmission, fhirEncounter);
                else if (INPATIENT_DISCHARGE_CONCEPT.equals(conceptId))
                    latestInpatientDischarge = mostRecentEncounter(latestInpatientDischarge, fhirEncounter);
                else if (OUTPATIENT_ADMISSION_CONCEPT.equals(conceptId))
                    latestOutpatientAdmission = mostRecentEncounter(latestOutpatientAdmission, fhirEncounter);
                else if (OUTPATIENT_DISCHARGE_CONCEPT.equals(conceptId))
                    latestOutpatientDischarge = mostRecentEncounter(latestOutpatientDischarge, fhirEncounter);
                else if (EMERGENCY_ADMISSION_CONCEPT.equals(conceptId))
                    latestEmergencyAdmission = mostRecentEncounter(latestEmergencyAdmission, fhirEncounter);
                else if (EMERGENCY_DISCHARGE_CONCEPT.equals(conceptId))
                    latestEmergencyDischarge = mostRecentEncounter(latestEmergencyDischarge, fhirEncounter);
        }

        if (latestInpatientAdmission != null && mostRecentEncounter(latestInpatientDischarge, latestInpatientAdmission) == latestInpatientAdmission)
            activeEncounters.add(ActiveEncounter.createFrom(patient, latestInpatientAdmission, "Inpatient"));

        if (latestOutpatientAdmission != null && mostRecentEncounter(latestOutpatientDischarge, latestOutpatientAdmission) == latestOutpatientAdmission)
            activeEncounters.add(ActiveEncounter.createFrom(patient, latestOutpatientAdmission, "Outpatient"));

        if (latestEmergencyAdmission != null && mostRecentEncounter(latestEmergencyDischarge, latestEmergencyAdmission) == latestEmergencyAdmission)
            activeEncounters.add(ActiveEncounter.createFrom(patient, latestEmergencyAdmission, "Emergency"));

        return activeEncounters;
    }

    private List<ResourceWrapper> getPatientEncounters(RegisteredPatient patient) {
        ResourceDalI resourceDal = DalProvider.factoryResourceDal();

        try {
            return resourceDal.getResourcesByPatientAllSystems(
                UUID.fromString(patient.getServiceId()),
                UUID.fromString(patient.getPatientId()),
                "Encounter");
        } catch (Exception e) {
            LOG.error("Error getting encounters for patient " + patient.getPatientId(), e);
            return null;
        }
    }

    private Long getConceptId(Encounter encounter) throws Exception {
        // TODO: extend to return mapped "summary" concept (i.e. Inpatient/Outpatient/etc)
        EncounterCodeDalI encounterCodeDal = DalProvider.factoryEncounterCodeDal();
        String originalTerm = org.endeavourhealth.transform.enterprise.transforms.EncounterTransformer.findEncounterTypeTerm(encounter);
        if (!Strings.isNullOrEmpty(originalTerm)) {
            EncounterCode ret = encounterCodeDal.findOrCreateCode(originalTerm);
            return ret.getCode();
        }
        return null;
    }

    private Encounter mostRecentEncounter(Encounter encounter1, Encounter encounter2) {
        if (encounter1 == null)
            return encounter2;

        if (encounter2 == null)
            return encounter1;

        if (!encounter1.hasPeriod())
            return encounter2;

        if (!encounter2.hasPeriod())
            return encounter1;

        if (encounter1.getPeriod().hasStart() && !encounter2.getPeriod().hasStart())
            return encounter1;

        if (encounter2.getPeriod().hasStart() && !encounter1.getPeriod().hasStart())
            return encounter2;

        if (encounter1.getPeriod().getStart().getTime() > encounter2.getPeriod().getStart().getTime())
            return encounter1;
        else
            return encounter2;
    }
}
