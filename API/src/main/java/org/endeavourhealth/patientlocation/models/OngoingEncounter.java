package org.endeavourhealth.patientlocation.models;

import java.util.Date;

public class OngoingEncounter {
    private String nhsNumber;
    private String organisation;
    private Long status;
    private Date date;

    public String getNhsNumber() {
        return nhsNumber;
    }

    public OngoingEncounter setNhsNumber(String nhsNumber) {
        this.nhsNumber = nhsNumber;
        return this;
    }

    public String getOrganisation() {
        return organisation;
    }

    public OngoingEncounter setOrganisation(String organisation) {
        this.organisation = organisation;
        return this;
    }

    public Long getStatus() {
        return status;
    }

    public OngoingEncounter setStatus(Long status) {
        this.status = status;
        return this;
    }

    public Date getDate() {
        return date;
    }

    public OngoingEncounter setDate(Date date) {
        this.date = date;
        return this;
    }
}
