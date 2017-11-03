package org.endeavourhealth.patientlocation.models;

import org.hl7.fhir.instance.model.Encounter;

import java.util.Date;

public class ActiveEncounter {
    private RegisteredPatient patient;
    private String type;
    private Date date;
    private String problem;

    public static ActiveEncounter createFrom(RegisteredPatient patient, Encounter encounter, String type) {
        ActiveEncounter activeEncounter = new ActiveEncounter();
        activeEncounter.patient = patient;
        activeEncounter.type = type;

        if (encounter.hasReason() && encounter.getReason().size() > 0)
            // TODO : Needs extending to check coding.code.term if text missing
            activeEncounter.problem = encounter.getReason().get(0).getText();

        if (encounter.hasPeriod())
            activeEncounter.date = encounter.getPeriod().getStart();

        return activeEncounter;
    }

    public RegisteredPatient getPatient() {
        return patient;
    }

    public ActiveEncounter setPatient(RegisteredPatient patient) {
        this.patient = patient;
        return this;
    }

    public String getType() {
        return type;
    }

    public ActiveEncounter setType(String type) {
        this.type = type;
        return this;
    }

    public Date getDate() {
        return date;
    }

    public ActiveEncounter setDate(Date date) {
        this.date = date;
        return this;
    }

    public String getProblem() {
        return problem;
    }

    public ActiveEncounter setProblem(String problem) {
        this.problem = problem;
        return this;
    }
}
