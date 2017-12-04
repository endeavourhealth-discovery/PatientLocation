package org.endeavourhealth.patientlocation;

import javax.ws.rs.core.SecurityContext;
import java.util.List;

public class World {
    public SecurityContext securityContext;
    public List<String> requestedOrgs;
}
