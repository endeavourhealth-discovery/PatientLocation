package org.endeavourhealth.patientlocation.dal;

import org.endeavourhealth.patientlocation.models.OngoingEncounter;

import java.util.List;

public interface Encounter_DAL {
    List<OngoingEncounter> getOngoingEncountersForMyPatientsAtOtherOrganisations(String userServiceId, List<String> serviceIds);
}
