package org.endeavourhealth.patientlocation.dal;

import org.endeavourhealth.patientlocation.models.ServicePatient;
import org.hl7.fhir.instance.model.Encounter;
import org.hl7.fhir.instance.model.EpisodeOfCare;

import java.util.List;

public interface Resource_DAL {
    Encounter getLastestEncounterForServicePatient(ServicePatient servicePatient);
    List<EpisodeOfCare> getOpenEpisodesForServicePatient(ServicePatient servicePatient);
}
