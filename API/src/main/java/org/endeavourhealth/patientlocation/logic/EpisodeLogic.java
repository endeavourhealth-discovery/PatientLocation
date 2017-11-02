package org.endeavourhealth.patientlocation.logic;

import org.endeavourhealth.patientlocation.dal.MPI_DAL;
import org.endeavourhealth.patientlocation.dal.MPI_DAL_JDBC;
import org.endeavourhealth.patientlocation.dal.Resource_DAL;
import org.endeavourhealth.patientlocation.dal.Resource_DAL_Cassandra;
import org.endeavourhealth.patientlocation.helpers.Security;
import org.endeavourhealth.patientlocation.models.OpenEpisode;
import org.endeavourhealth.patientlocation.models.ServicePatient;
import org.hl7.fhir.instance.model.CodeableConcept;
import org.hl7.fhir.instance.model.Coding;
import org.hl7.fhir.instance.model.Encounter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.SecurityContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EpisodeLogic {
    static MPI_DAL mpiDal;
    static Resource_DAL resourceDal;

    public EpisodeLogic() {
        if (mpiDal == null)
            mpiDal = new MPI_DAL_JDBC();

        if (resourceDal == null)
            resourceDal = new Resource_DAL_Cassandra();
    }

    private static final Logger LOG = LoggerFactory.getLogger(EpisodeLogic.class);

    public List<OpenEpisode> getOpenEpisodes(SecurityContext context, List<String> serviceIds) {
        if (serviceIds == null || serviceIds.size()==0)
            serviceIds = new ArrayList<>(Security.getUserAllowedOrganisationIdsFromSecurityContext(context));

        if (!Security.userIsAllowedAccess(context, serviceIds))
            return null;

        String userServiceId = Security.getUsersDefaultOrganisation(context);

        List<ServicePatient> servicePatients = mpiDal.getMyRegisteredPatientsAtOtherServices(userServiceId, serviceIds);

        List<OpenEpisode> openEpisodes = new ArrayList<>();

        for (ServicePatient servicePatient : servicePatients) {
            Encounter encounter = getLatestEncounterForServicePatient(servicePatient);
            if (isActiveEncounter(encounter)) {
                openEpisodes.add(createOpenEpisode(servicePatient, encounter));
            }
        }

        return openEpisodes;
    }

    private Encounter getLatestEncounterForServicePatient(ServicePatient servicePatient) {
        return resourceDal.getLastestEncounterForServicePatient(servicePatient);
    }

    boolean isActiveEncounter(Encounter encounter) {
        if (encounter == null)
            return false;

        if (encounter.hasPeriod() && encounter.getPeriod().hasEnd())
            return false;

        Encounter.EncounterState state = encounter.getStatus();

        switch (state) {
            case ARRIVED:
            case INPROGRESS:
                return true;
            case PLANNED:
            case ONLEAVE:
            case FINISHED:
            case CANCELLED:
            case NULL:
            default:
                return false;
        }
    }

    OpenEpisode createOpenEpisode(ServicePatient servicePatient, Encounter encounter) {
        return new OpenEpisode()
            .setServicePatient(servicePatient)
            .setGroup(getGroup(encounter))
            .setStatus(getStatus(encounter))
            .setDate(getDate(encounter))
            .setProblem(getProblem(encounter));
    }

    String getGroup(Encounter encounter) {
        if (encounter == null)
            return "Outpatient";

        Encounter.EncounterClass encounterClass = encounter.getClass_();
        switch (encounterClass) {
            case INPATIENT:
                return "Inpatient";
            case AMBULATORY:
            case EMERGENCY:
                return "A&E";
            case OUTPATIENT:
            case HOME:
            case FIELD:
            case DAYTIME:
            case VIRTUAL:
            case OTHER:
            case NULL:
            default:
                return "Outpatient";
        }
    }

    String getProblem(Encounter encounter) {
        if (encounter == null)
            return null;

        List<CodeableConcept> reasons = encounter.getReason();

        if (reasons.size() == 0)
            return null;

        CodeableConcept reasonConcept = reasons.get(0);

        if (reasonConcept.hasText())
            return reasonConcept.getText();

        if (reasonConcept.hasCoding()) {
            List<Coding> codings = reasonConcept.getCoding();
            Coding coding = codings.get(0);
            if (coding.hasDisplay())
                return coding.getDisplay();

            if (coding.hasCode())
                return coding.getCode();
        }

        return null;
    }

    private String getStatus(Encounter encounter) {
        if (encounter == null)
            return null;

        if (!encounter.hasStatus())
            return null;

        return encounter.getStatus().getDisplay();
    }

    private Date getDate(Encounter encounter) {
        if (encounter == null)
            return null;

        if (!encounter.hasPeriod())
            return null;

        return encounter.getPeriod().getStart();
    }
}
