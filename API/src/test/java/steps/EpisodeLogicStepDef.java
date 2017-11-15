package steps;

import cucumber.api.PendingException;
import cucumber.api.java8.En;
import mocks.Mock_MpiDal;
import mocks.Mock_Resource_DAL;
import org.endeavourhealth.patientlocation.logic.EpisodeLogic;
import org.endeavourhealth.patientlocation.models.OpenEpisode;
import org.endeavourhealth.patientlocation.models.ServicePatient;
import org.hl7.fhir.instance.model.EpisodeOfCare;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class EpisodeLogicStepDef implements En {
    private List<OpenEpisode> openEpisodes;

    public EpisodeLogicStepDef(World world, Mock_MpiDal mock_mpiDal, Mock_Resource_DAL mock_resourceDal) {
        // ---------- GIVEN ----------

        // ---------- GIVEN ----------
        When("^open episodes are retrieved$", () -> {
            openEpisodes = new EpisodeLogic(mock_mpiDal, mock_resourceDal).getOpenEpisodes(world.securityContext, world.requestedOrgs);
        });
        // ---------- AND ----------
        And("^there are (.*) of my patients at other organisations$", (String input) -> {
            if ("none".equals(input))
                mock_mpiDal.registeredPatientsAtOtherServices = null;
            else {
                mock_mpiDal.registeredPatientsAtOtherServices = new ArrayList<>();
                mock_mpiDal.registeredPatientsAtOtherServices.add(new ServicePatient());
            }
        });
        And("^(.*) episodes of care exist$", (String input) -> {
            if ("no".equals(input))
                mock_resourceDal.episodeOfCare = null;
            else {
                mock_resourceDal.episodeOfCare = new ArrayList<>();
                mock_resourceDal.episodeOfCare.add(new EpisodeOfCare());
            }

        });

        // ---------- THEN ----------
        Then("^open episodes list will be null$", () -> assertNull(openEpisodes));
        Then("^open episodes list will be empty$", () -> {
            assertNotNull(openEpisodes);
            assertEquals(0, openEpisodes.size());
        });
        Then("^open episodes list will be populated$", () -> {
            assertNotNull(openEpisodes);
            assertNotEquals(0, openEpisodes.size());
        });
    }
}
