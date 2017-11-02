package org.endeavourhealth.patientlocation.models;

import java.util.Date;

public class OpenEpisode {
    private ServicePatient servicePatient;
    private String group;
    private String status;
    private Date date;
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

    public String getStatus() {
        return status;
    }

    public OpenEpisode setStatus(String status) {
        this.status = status;
        return this;
    }

    public Date getDate() {
        return date;
    }

    public OpenEpisode setDate(Date date) {
        this.date = date;
        return this;
    }
}
