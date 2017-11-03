package org.endeavourhealth.patientlocation.models;

public class RegisteredPatient {
    private String serviceId;
    private String systemId;
    private String personId;
    private String patientId;

    public String getServiceId() {
        return serviceId;
    }

    public RegisteredPatient setServiceId(String serviceId) {
        this.serviceId = serviceId;
        return this;
    }

    public String getSystemId() {
        return systemId;
    }

    public RegisteredPatient setSystemId(String systemId) {
        this.systemId = systemId;
        return this;
    }

    public String getPersonId() {
        return personId;
    }

    public RegisteredPatient setPersonId(String personId) {
        this.personId = personId;
        return this;
    }

    public String getPatientId() {
        return patientId;
    }

    public RegisteredPatient setPatientId(String patientId) {
        this.patientId = patientId;
        return this;
    }
}
