package org.endeavourhealth.patientlocation.helpers;

import cucumber.api.java8.En;
import org.endeavourhealth.patientlocation.mocks.Mock_SecurityContext;

import javax.ws.rs.core.SecurityContext;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.hasItems;

public class SecurityStepDef implements En {
    private SecurityContext securityContext;
    private Set<String> actual;

    public SecurityStepDef() {
        Given("^A security context containing (.*)$", (String input) -> {
            String[] organisations = input.split(",");
            List<Map<String, Object>> orgGroups = new ArrayList<>();
            for (String organisation : organisations) {
                Map<String, Object> orgGroup = new HashMap<>();
                orgGroup.put("organisationId", organisation);
                orgGroups.add(orgGroup);
            }
            securityContext = new Mock_SecurityContext(orgGroups);
        });

        When("^the allowed organisations are checked$", () -> {
            actual = new Security().getUserAllowedOrganisationIdsFromSecurityContext(securityContext);
        });

        Then("^The user allowed organisations will be (.*)$", (String expected) -> {
            String[] expectedList = expected.split(",");

            assertEquals(expectedList.length, actual.size());
            assertThat(actual, hasItems(expectedList));
        });
    }
}
