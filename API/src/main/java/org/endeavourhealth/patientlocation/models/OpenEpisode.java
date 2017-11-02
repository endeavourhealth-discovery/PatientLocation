package org.endeavourhealth.patientlocation.models;

public class OpenEpisode {
    private ServicePatient servicePatient;
    private String group;
    private String problem;

    public ServicePatient getServicePatient() {
        return servicePatient;
    }

    public OpenEpisode setServicePatient(ServicePatient servicePatient) {
        this.servicePatient = servicePatient;
        return this;
    }

    public String getGroup() {
        return group;
    }

    public OpenEpisode setGroup(String group) {
        this.group = group;
        return this;
    }

    public String getProblem() {
        return problem;
    }

    public OpenEpisode setProblem(String problem) {
        this.problem = problem;
        return this;
    }
}
