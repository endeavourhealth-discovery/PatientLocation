package org.endeavourhealth.patientlocation.logic;

import cucumber.api.PendingException;
import cucumber.api.java8.En;
import org.aopalliance.reflect.Code;
import org.hl7.fhir.instance.model.CodeableConcept;
import org.hl7.fhir.instance.model.Coding;
import org.hl7.fhir.instance.model.Encounter;
import org.hl7.fhir.instance.model.Period;
import org.junit.Assert;

import java.util.Date;

public class EpisodeLogicStepDef extends EpisodeLogic implements En {
    private Encounter encounter;
    private boolean isActive;
    private String group;
    private String problem;

    public EpisodeLogicStepDef() {
        Given("^a null encounter$", () -> {
            encounter = null;
        });
        Given("^an encounter$", () -> {
            encounter = new Encounter();
        });

        And("^it has a period$", () -> {
            Period period = new Period();
            encounter.setPeriod(period);
        });
        And("^its end date is (.*)$", (String endDateStr) -> {
            Date endDate;
            if ("null".equals(endDateStr))
                endDate = null;
            else
                endDate = new Date(endDateStr);
            encounter.getPeriod().setEnd(endDate);
        });
        And("^its status is (.*)$", (String status) -> {
            encounter.setStatus(Encounter.EncounterState.valueOf(status));
        });
        And("^its class is (.*)$", (String clazz) -> {
            encounter.setClass_(Encounter.EncounterClass.valueOf(clazz));
        });
        And("^its reason is empty$", () -> {
            encounter.getReason().clear();
        });
        And("^it has a reason$", () -> {
            CodeableConcept concept = new CodeableConcept();
            encounter.getReason().add(concept);
        });
        And("^its reason has text (.*)$", (String text) -> {
            CodeableConcept concept = encounter.getReason().get(0);
            concept.setText(text);
        });
        And("^its reason has coding$", () -> {
            Coding coding = new Coding();
            CodeableConcept concept = encounter.getReason().get(0);
            concept.addCoding(coding);
        });
        And("^its reason coding has display (.*)", (String display) -> {
            encounter.getReason().get(0).getCoding().get(0).setDisplay(display);
        });
        And("^its reason coding has code (.*)$", (String code) -> {
            encounter.getReason().get(0).getCoding().get(0).setCode(code);
        });

        When("^the active status is checked$", () -> {
            isActive = isActiveEncounter(encounter);
        });
        When("^the group is checked$", () -> {
            group = getGroup(encounter);
        });
        When("^the problem is checked$", () -> {
            problem = getProblem(encounter);
        });

        Then("^isActive will be (.*)$", (String expectedStr) -> {
            Boolean expected = Boolean.parseBoolean(expectedStr);
            Assert.assertEquals(expected, isActive);
        });
        Then("^group will be (.*)$", (String expected) -> {
            Assert.assertEquals(expected, group);
        });
        Then("^problem will be (.*)", (String expected) -> {
            if ("null".equals(expected))
                expected = null;

            Assert.assertEquals(expected, problem);
        });
    }
}
