package org.endeavourhealth.patientlocation.logic;

import org.endeavourhealth.patientlocation.dal.DataAccessLayer;
import org.endeavourhealth.patientlocation.dal.DataAccessLayer_Jdbc;
import org.endeavourhealth.patientlocation.helpers.Security;
import org.endeavourhealth.patientlocation.models.OpenEpisode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.SecurityContext;
import java.util.List;

public class EpisodeLogic {
    static DataAccessLayer dal;

    public EpisodeLogic() {
        if (dal == null)
            dal = new DataAccessLayer_Jdbc();
    }

    private static final Logger LOG = LoggerFactory.getLogger(EpisodeLogic.class);

    public List<OpenEpisode> getOpenEpisodes(SecurityContext context, List<String> serviceIds) {
        if (!Security.userIsAllowedAccess(context, serviceIds))
            return null;

        return dal.getOpenEpisodes(serviceIds);
    }
}
