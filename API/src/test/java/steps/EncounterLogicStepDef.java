package steps;

import cucumber.api.java8.En;
import mocks.Mock_Encounter_DAL;
import org.endeavourhealth.patientlocation.logic.EncounterLogic;
import org.endeavourhealth.patientlocation.models.OngoingEncounter;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class EncounterLogicStepDef implements En {
    private List<OngoingEncounter> ongoingEncounters;

    public EncounterLogicStepDef(World world, Mock_Encounter_DAL mock_resourceDal) {
        // ---------- GIVEN ----------

        // ---------- GIVEN ----------
        When("^ongoing encounters are retrieved$", () -> {
            ongoingEncounters = new EncounterLogic(mock_resourceDal).getOpenEncounters(world.securityContext, world.requestedOrgs);
        });
        And("^(.*) ongoing encounters exist$", (String input) -> {
            if ("no".equals(input))
                mock_resourceDal.ongoingEncounters = null;
            else {
                mock_resourceDal.ongoingEncounters = new ArrayList<>();
                mock_resourceDal.ongoingEncounters.add(new OngoingEncounter());
            }

        });

        // ---------- THEN ----------
        Then("^ongoing encounters list will be null$", () -> assertNull(ongoingEncounters));
        Then("^ongoing encounters list will be populated$", () -> {
            assertNotNull(ongoingEncounters);
            assertNotEquals(0, ongoingEncounters.size());
        });
    }
}
