package mocks;

import org.keycloak.representations.AccessToken;

import java.util.List;
import java.util.Map;

public class Mock_KeycloakSecurityContext extends org.keycloak.KeycloakSecurityContext {
    private AccessToken accessToken;

    public Mock_KeycloakSecurityContext(List<Map<String, Object>> orgGroups) {
        accessToken = new AccessToken();
        accessToken.setOtherClaims("orgGroups", orgGroups);
    }

    @Override
    public AccessToken getToken() {
        return accessToken;
    }
}
