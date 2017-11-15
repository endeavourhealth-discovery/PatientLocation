package mocks;

import org.endeavourhealth.patientlocation.dal.Resource_DAL;
import org.endeavourhealth.patientlocation.models.ServicePatient;
import org.hl7.fhir.instance.model.Encounter;
import org.hl7.fhir.instance.model.EpisodeOfCare;

import java.util.List;

public class Mock_Resource_DAL implements Resource_DAL {
    public Encounter encounter;
    public List<EpisodeOfCare> episodeOfCare;

    @Override
    public Encounter getLastestEncounterForServicePatient(ServicePatient servicePatient) {
        return encounter;
    }

    @Override
    public List<EpisodeOfCare> getOpenEpisodesForServicePatient(ServicePatient servicePatient) {
        return episodeOfCare;
    }
}
