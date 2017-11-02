package org.endeavourhealth.patientlocation.models;

public class ServicePatient {
    private String serviceId;
    private String systemId;
    private String patientId;
    private String nhsNumber;
    private String surname;
    private String forename;

    public String getServiceId() {
        return serviceId;
    }

    public ServicePatient setServiceId(String serviceId) {
        this.serviceId = serviceId;
        return this;
    }

    public String getSystemId() {
        return systemId;
    }

    public ServicePatient setSystemId(String systemId) {
        this.systemId = systemId;
        return this;
    }

    public String getPatientId() {
        return patientId;
    }

    public ServicePatient setPatientId(String patientId) {
        this.patientId = patientId;
        return this;
    }

    public String getNhsNumber() {
        return nhsNumber;
    }

    public ServicePatient setNhsNumber(String nhsNumber) {
        this.nhsNumber = nhsNumber;
        return this;
    }

    public String getSurname() {
        return surname;
    }

    public ServicePatient setSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public String getForename() {
        return forename;
    }

    public ServicePatient setForename(String forename) {
        this.forename = forename;
        return this;
    }
}
