package org.endeavourhealth.patientlocation.dal;

import org.endeavourhealth.patientlocation.models.ServicePatient;

import java.util.List;

public interface MPI_DAL {
    List<ServicePatient> getMyRegisteredPatientsAtOtherServices(String myServiceId, List<String> otherServiceIds);
}
