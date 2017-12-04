package org.endeavourhealth.patientlocation.helpers;

import cucumber.api.java8.En;
import org.endeavourhealth.patientlocation.mocks.Mock_SecurityContext;
import org.endeavourhealth.patientlocation.World;
import org.keycloak.KeycloakPrincipal;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.hasItems;

public class SecurityStepDef extends Security implements En {
    private Set<String> actualOrgs;
    private boolean actualAccess;
    private String actualDefaultOrg;

    public SecurityStepDef(World world) {
        // ---------- GIVEN ----------
        Given("^a security context containing (.*)$", (String input) -> {
            List<Map<String, Object>> orgGroups = new ArrayList<>();
            if (!input.isEmpty()) {
                String[] organisations = input.split(",");
                for (String organisation : organisations) {
                    Map<String, Object> orgGroup = new HashMap<>();
                    orgGroup.put("organisationId", organisation);
                    orgGroups.add(orgGroup);
                }
            }
            world.securityContext = new Mock_SecurityContext(orgGroups);
        });
        Given("^no security context$", () -> {
            world.securityContext = null;
        });
        Given("^a security context$", () -> {
            world.securityContext = new Mock_SecurityContext();
        });

        // ---------- AND ----------
        And("^a requested organisation list of (.*)$", (String input) -> {
            if ("null".equals(input))
                world.requestedOrgs = null;
            else {
                world.requestedOrgs = new ArrayList<>();

                if (!input.isEmpty()) {
                    String[] requested = input.split(",");
                    world.requestedOrgs.addAll(Arrays.asList(requested));
                }
            }
        });
        And("^no default organisation$", () -> {
            ((KeycloakPrincipal)world.securityContext.getUserPrincipal()).getKeycloakSecurityContext().getToken().setOtherClaims("organisationId", null);
        });
        And("^default organisation of (.*)$", (String input) -> {
            ((KeycloakPrincipal)world.securityContext.getUserPrincipal()).getKeycloakSecurityContext().getToken().setOtherClaims("organisationId", input);
        });

        // ---------- WHEN ----------
        When("^the allowed organisations are checked$", () -> {
            actualOrgs = Security.getUserAllowedOrganisationIdsFromSecurityContext(world.securityContext);
        });
        When("^the users allowed access is checked$", () -> {
            actualAccess = Security.userIsAllowedAccess(world.securityContext, world.requestedOrgs);
        });
        When("^the users default organisation is checked$", () -> {
            actualDefaultOrg = Security.getUsersDefaultOrganisation(world.securityContext);
        });

        // ---------- THEN ----------
        Then("^the user allowed organisations will be (.*)$", (String expected) -> {
            String[] expectedList = expected.split(",");

            assertEquals(expectedList.length, actualOrgs.size());
            assertThat(actualOrgs, hasItems(expectedList));
        });
        Then("^the user allowed access will be (.*)$", (String expectedStr) -> {
            boolean expected = Boolean.parseBoolean(expectedStr);
            assertEquals(expected, actualAccess);
        });
        Then("^the users default organisation will be (.*)$", (String expectedStr) -> {
            String expected = ("null".equals(expectedStr)) ? null : expectedStr;
            assertEquals(expected, actualDefaultOrg);
        });
    }
}
