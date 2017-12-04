package org.endeavourhealth.patientlocation.mocks;

import javax.ws.rs.core.SecurityContext;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Mock_SecurityContext implements SecurityContext{
    private Mock_KeycloakPrincipal principal;

    public Mock_SecurityContext() {
        this(new ArrayList<>());
    }

    public Mock_SecurityContext(List<Map<String, Object>> orgGroups) {
        Mock_KeycloakSecurityContext securityContext = new Mock_KeycloakSecurityContext(orgGroups);
        this.principal = new Mock_KeycloakPrincipal(securityContext);
    }

    @Override
    public Principal getUserPrincipal() {
        return this.principal;
    }

    @Override
    public boolean isUserInRole(String s) {
        return false;
    }

    @Override
    public boolean isSecure() {
        return false;
    }

    @Override
    public String getAuthenticationScheme() {
        return null;
    }
}
