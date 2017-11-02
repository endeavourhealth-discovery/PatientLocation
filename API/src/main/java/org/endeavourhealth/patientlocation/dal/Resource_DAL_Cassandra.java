package org.endeavourhealth.patientlocation.dal;

import org.endeavourhealth.common.cache.ObjectMapperPool;
import org.endeavourhealth.common.cache.ParserPool;
import org.endeavourhealth.core.database.dal.DalProvider;
import org.endeavourhealth.core.database.dal.ehr.ResourceDalI;
import org.endeavourhealth.core.database.dal.ehr.models.ResourceWrapper;
import org.endeavourhealth.patientlocation.models.ServicePatient;
import org.hl7.fhir.instance.model.Encounter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

public class Resource_DAL_Cassandra implements Resource_DAL {
    private static final Logger LOG = LoggerFactory.getLogger(Resource_DAL_Cassandra.class);

    @Override
    public Encounter getLastestEncounterForServicePatient(ServicePatient servicePatient) {
        ResourceDalI resourceRepository = DalProvider.factoryResourceDal();
        try {
            List<ResourceWrapper> resources = resourceRepository.getResourcesByPatientAllSystems(
                UUID.fromString(servicePatient.getServiceId()),
                UUID.fromString(servicePatient.getPatientId()),
                "Encounter");

            if (resources.size() == 0)
                return null;

            Encounter result = null;

            for (ResourceWrapper resource : resources) {
                Encounter encounter = (Encounter)ParserPool.getInstance().parse("Encounter", resource.getResourceData());
                result = newest(result, encounter);
            }

            return result;

        } catch (Exception e) {
            LOG.error("Error fetching encounters for service patient", e);
        }
        return null;
    }

    private Encounter newest(Encounter encounter1, Encounter encounter2) {
        if (encounter1 == null)
            return encounter2;

        if (encounter2 == null)
            return encounter1;

        if (!encounter1.hasPeriod())
            return encounter2;

        if (!encounter2.hasPeriod())
            return encounter1;

        if (encounter1.getPeriod().hasEnd())
            return encounter2;

        if (encounter2.getPeriod().hasEnd())
            return encounter1;

        if (!encounter1.getPeriod().hasStart())
            return encounter2;

        if (!encounter2.getPeriod().hasStart())
            return encounter1;

        if (encounter1.getPeriod().getStart().getTime() > encounter2.getPeriod().getStart().getTime())
            return encounter1;
        else
            return encounter2;
    }
}
