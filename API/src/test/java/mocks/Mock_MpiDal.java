package mocks;

import org.endeavourhealth.patientlocation.dal.MPI_DAL;
import org.endeavourhealth.patientlocation.models.ServicePatient;

import java.util.List;

public class Mock_MpiDal implements MPI_DAL {
    public List<ServicePatient> registeredPatientsAtOtherServices;

    @Override
    public List<ServicePatient> getMyRegisteredPatientsAtOtherServices(String myServiceId, List<String> otherServiceIds) {
        return registeredPatientsAtOtherServices;
    }
}
