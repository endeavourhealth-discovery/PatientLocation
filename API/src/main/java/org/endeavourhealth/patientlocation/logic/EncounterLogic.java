package org.endeavourhealth.patientlocation.logic;

import org.endeavourhealth.patientlocation.dal.Encounter_DAL;
import org.endeavourhealth.patientlocation.dal.Encounter_DAL_JDBC;
import org.endeavourhealth.patientlocation.helpers.Security;
import org.endeavourhealth.patientlocation.models.OngoingEncounter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.SecurityContext;
import java.util.ArrayList;
import java.util.List;

public class EncounterLogic {
    static Encounter_DAL encounterDal;

    public EncounterLogic() {
        if (encounterDal == null)
            encounterDal = new Encounter_DAL_JDBC();
    }

    public EncounterLogic(Encounter_DAL aEncounterDal) {
        encounterDal = aEncounterDal;
    }

    private static final Logger LOG = LoggerFactory.getLogger(EncounterLogic.class);

    public List<OngoingEncounter> getOpenEncounters(SecurityContext context, List<String> serviceIds) {
        if (serviceIds == null || serviceIds.size() == 0)
            serviceIds = new ArrayList<>(Security.getUserAllowedOrganisationIdsFromSecurityContext(context));

        if (!Security.userIsAllowedAccess(context, serviceIds))
            return null;

        String userServiceId = Security.getUsersDefaultOrganisation(context);
        serviceIds.remove(userServiceId);

        return encounterDal.getOngoingEncountersForMyPatientsAtOtherOrganisations(userServiceId, serviceIds);
    }
}
