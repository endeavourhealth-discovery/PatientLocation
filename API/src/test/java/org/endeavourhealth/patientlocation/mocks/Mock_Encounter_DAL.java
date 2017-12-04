package org.endeavourhealth.patientlocation.mocks;

import org.endeavourhealth.patientlocation.dal.Encounter_DAL;
import org.endeavourhealth.patientlocation.models.OngoingEncounter;

import java.util.List;

public class Mock_Encounter_DAL implements Encounter_DAL {
    public List<OngoingEncounter> ongoingEncounters;

    @Override
    public List<OngoingEncounter> getOngoingEncountersForMyPatientsAtOtherOrganisations(String userServiceId, List<String> serviceIds) {
        return ongoingEncounters;
    }
}
