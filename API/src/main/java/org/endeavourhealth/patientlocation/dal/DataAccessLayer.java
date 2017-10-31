package org.endeavourhealth.patientlocation.dal;

import org.endeavourhealth.patientlocation.models.OpenEpisode;

import java.util.List;

public interface DataAccessLayer {
    List<OpenEpisode> getOpenEpisodes(List<String> serviceIds);
}
