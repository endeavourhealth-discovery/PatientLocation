CREATE DATABASE IF NOT EXISTS PatientLocation;

CREATE TABLE IF NOT EXISTS PatientLocation.PatientLocation (
  service_id VARCHAR(36) NOT NULL,
  system_id VARCHAR(36) NOT NULL,
  patient_id VARCHAR(36) NOT NULL,
  person_id VARCHAR(36) NOT NULL,
  nhs_number VARCHAR(10),
  inpatient_admission DATETIME,
  inpatient_problem VARCHAR(255),
  inpatient_discharge DATETIME,
  outpatient_admission DATETIME,
  outpatient_problem VARCHAR(255),
  outpatient_discharge DATETIME,
  emergency_admission DATETIME,
  emergency_problem VARCHAR(255),
  emergency_discharge DATETIME
);
