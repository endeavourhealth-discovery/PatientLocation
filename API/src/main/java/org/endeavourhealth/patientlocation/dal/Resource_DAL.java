package org.endeavourhealth.patientlocation.dal;

import org.endeavourhealth.patientlocation.models.ServicePatient;
import org.hl7.fhir.instance.model.Encounter;

public interface Resource_DAL {
    Encounter getLastestEncounterForServicePatient(ServicePatient servicePatient);
}
