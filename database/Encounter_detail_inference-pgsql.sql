drop table if exists concept_map;

create table concept_map
(
	event_type bigint not null,
    concept_type bigint not null,
    source_term varchar(1000) not null,
    concept_id bigint,
    primary key (event_type, concept_type, source_term)
);

insert into concept_map
(event_type, concept_type, concept_id, source_term)
values
-- ENCOUNTERS (1008)
	-- Patient encounter status (8038)   Based on term <fhir_class>|<fhir_type>
	-- NOTE: MUST RUN BEFORE/REQUIRED BY ADMINISTRATIVE ACTION
		-- .Outpatient (8018)
			(1008, 8038, 8018, 'outpatient|outpatient'),
			(1008, 8038, 8018, 'outpatient|inpatient'),
			(1008, 8038, 8018, 'outpatient|outpatient referral'),
			(1008, 8038, 8018, 'outpatient|radiology'),
			(1008, 8038, 8018, 'outpatient|clinical measurement'),
			(1008, 8038, 8018, 'outpatient|emergency department'),
		-- .Inpatient (8023)
			(1008, 8038, 8023, 'inpatient|emergency department'),
			(1008, 8038, 8023, 'inpatient|inpatient'),
			(1008, 8038, 8023, 'inpatient|maternity'),
			(1008, 8038, 8023, 'inpatient|newborn'),
			(1008, 8038, 8023, 'emergency|inpatient'),
			(1008, 8038, 8023, 'other|inpatient'),
		-- .Day case (8024)
			(1008, 8038, 8024, 'inpatient|day case'),
			(1008, 8038, 8024, 'inpatient|regular day admission'),
			(1008, 8038, 8024, 'recurring|regular day admission'),
			(1008, 8038, 8024, 'recurring|outpatient'),
		-- .Emergency (8048)
			(1008, 8038, 8048, 'emergency|emergency department'),
		-- .Waiting List (8049)
			(1008, 8038, 8049, 'waitinglist|day case waiting list'),
			(1008, 8038, 8049, 'waitinglist|clinical measurement wait list'),
			(1008, 8038, 8049, 'waitinglist|inpatient waiting list'),
			(1008, 8038, 8049, 'waitinglist|outpatient referral'),
			(1008, 8038, 8049, 'waitinglist|radiology referral wait list'),
			(1008, 8038, 8049, 'outpatient|clinical measurement wait list'),
			(1008, 8038, 8049, 'outpatient|radiology referral wait list'),
			(1008, 8038, 8049, 'inpatient|inpatient waiting list'),
			(1008, 8038, 8049, 'inpatient|day case waiting list'),
		-- .Other/Unknown/Home
			-- (1008, 8038, ????, 'outpatient|dna/cancel op')

	-- Administrative action (8002) based on term <fhir_adt_message_code>|<patient status>
		-- .Admission (8019)
			(1008, 8002, 8019, 'ADT^A01|%'),		-- Admit/visit notification
		-- .Discharge (8020)
			(1008, 8002, 8020, 'ADT^A03|8023'),		-- Discharge/end visit (INPATIENT)
		-- .Discharge cancellation (8028)
			(1008, 8002, 8028, 'ADT^A13|%'),		-- Cancel discharge/end visit
			(1008, 8002, 8028, 'ADT^A12|%'),		-- Cancel transfer
		-- .Administration entry (8013)
			(1008, 8002, 8013, 'ADT^A35|%'),		-- Merge patient information - account number only
			(1008, 8002, 8013, 'ADT^A08|%'),		-- Update patient information
			(1008, 8002, 8013, 'ADT^A04|%'),		-- Register a patient
		-- .Transfer of patient (8022)
			(1008, 8002, 8022, 'ADT^A07|%'),		-- Change an inpatient to an outpatient
			(1008, 8002, 8022, 'ADT^A06|%'),		-- Change an outpatient to an inpatient
			(1008, 8002, 8022, 'ADT^A02|%'),		-- Transfer a patient
			(1008, 8002, 8022, 'ADT^A17|%'),		-- Swap patients
		-- .Attendance end (8021)
			(1008, 8002, 8020, 'ADT^A03|8018'),		-- Discharge/end visit (OUTPATIENT)
			(1008, 8002, 8020, 'ADT^A03|8048'),		-- Discharge/end visit (EMERGENCY)
			(1008, 8002, 8020, 'ADT^A03|8024'),		-- Discharge/end visit (DAY CASE)
			(1008, 8002, 8020, 'ADT^A03|8049'),		-- Discharge/end visit (WAITING LIST)
		-- .Pre-admission (8046)
			(1008, 8002, 8046, 'ADT^A05|%'),		-- Pre-admit a patient
		-- .Delete previous (8051)
			(1008, 8002, 8051, 'ADT^A23|%'),		-- Delete a patient record
            
	-- Completion status (8041)
		-- .Completed (8039)
			(1008, 8041, 8039, 'finished'),
			(1008, 8041, 8039, 'cancelled'),
		-- .Ongoing (8040)
			(1008, 8041, 8040, 'in-progress'),
		-- .Planned (8050)
			(1008, 8041, 8050, 'planned'),

	-- Healthcare service type (8000)
		-- .General Practice (8003)
			(1008, 8000, 8003, 'GP Surgery'),
			(1008, 8000, 8003, 'Surgery Consultation'),
			(1008, 8000, 8003, 'G.P.Surgery'),
			(1008, 8000, 8003, 'G.P. Surgery'),
			(1008, 8000, 8003, 'G P Surgery'),
			(1008, 8000, 8003, 'G.P Surgery'),
			(1008, 8000, 8003, 'Gp Surgery - Antenatal Clinic'),
			(1008, 8000, 8003, 'Gp Surgery - Sample Testing'),
			(1008, 8000, 8003, 'Saturday G.P. Surgery'),
			(1008, 8000, 8003, 'SURGERY'),
			(1008, 8000, 8003, 'College Surgery'),
			(1008, 8000, 8003, 'Surgery consultation'),
			(1008, 8000, 8003, 'Gp Surgery - Nurse Triage'),
			(1008, 8000, 8003, 'Emergency Appointment, Surgery'),
			(1008, 8000, 8003, 'G.P.Surgery (Duty Dr)'),
			(1008, 8000, 8003, 'Emergency G.P. Surgery'),
			(1008, 8000, 8003, 'G.P. Surgery - Child Health Clinic'),
			(1008, 8000, 8003, 'Branch Surgery (N.B.H)'),
			(1008, 8000, 8003, 'Walk-In Consultation'),
			(1008, 8000, 8003, 'Nhs Direct Nhs Trust, GP Out of Hours Centre'),
			(1008, 8000, 8003, 'Gp Out Of Hours'),
			(1008, 8000, 8003, 'Surgery: Emergency Appointment'),
			(1008, 8000, 8003, 'Co-op Surgery Consultation'),
			(1008, 8000, 8003, 'Extended Hours Gp Surgery'),
			(1008, 8000, 8003, 'G.P.Surgery - Non Appointment'),
			(1008, 8000, 8003, 'GP Out of Hours Centre'),
			(1008, 8000, 8003, 'General practice surgery'),
			(1008, 8000, 8003, 'Out of hours consultation at surgery'),
			(1008, 8000, 8003, 'Surgery Attendance'),

			(1008, 8000, 8003, 'Barndoc'),
			(1008, 8000, 8003, 'Barndoc Telephone Consultation'),
			(1008, 8000, 8003, 'East Park Medical Practice'),
			(1008, 8000, 8003, 'Caversham Group Pratcice'),
			(1008, 8000, 8003, 'The Lawson Practice'),
			(1008, 8000, 8003, 'Archway Medical Centre Surgery Pod'),
			(1008, 8000, 8003, 'Aberfeldy Practice Surgery Pod'),
			(1008, 8000, 8003, 'Hetherington Group Practice Surgery Pod'),
			(1008, 8000, 8003, 'Merchant Street Surgery'),
			(1008, 8000, 8003, 'Parliament Hill Surgery'),
			(1008, 8000, 8003, 'Globe Town Surgery'),
			(1008, 8000, 8003, 'Falcon Road Medical Centre'),
			(1008, 8000, 8003, 'Much Wenlock Surgery'),
			(1008, 8000, 8003, 'Dukes Avenue Practice'),
			(1008, 8000, 8003, 'Bloomsbury Surgery'),
			(1008, 8000, 8003, 'Belsize Surgery Pod'),
			(1008, 8000, 8003, 'St Peters Surgery'),
			(1008, 8000, 8003, 'Cressage Surgery'),
			(1008, 8000, 8003, 'Lonsdale Medical Centre'),
			(1008, 8000, 8003, 'Newham Gp Co-Op'),
			(1008, 8000, 8003, 'Chrisp Street Health Centre'),
			(1008, 8000, 8003, 'Stroud Green Medical Clinic'),
			(1008, 8000, 8003, 'Hanover Medical Centre, Sheffield'),
			(1008, 8000, 8003, 'Sea Road Surgery'),
			(1008, 8000, 8003, 'Pebsham Surgery'),
			(1008, 8000, 8003, 'Whitechapel Walk In Centre'),
			(1008, 8000, 8003, 'St Andrews Health Centre'),
			(1008, 8000, 8003, 'The Forest Road Medical Centre, Surgery'),
			(1008, 8000, 8003, 'Lawson Practice'),
			(1008, 8000, 8003, 'Fairhill Medical Practice'),
			(1008, 8000, 8003, 'ALBION STREET GROUP PRACTICE'),
			(1008, 8000, 8003, 'The Barkantine Practice'),
			(1008, 8000, 8003, 'Roman Way Medical Centre'),
			(1008, 8000, 8003, 'Rotherfield Surgery'),
			(1008, 8000, 8003, 'Russell Street Surgery'),
			(1008, 8000, 8003, 'CITY WELLBEING PRACTICE'),
			(1008, 8000, 8003, 'Parkwood Surgery'),
			(1008, 8000, 8003, 'Harvey House Surgery'),
			(1008, 8000, 8003, 'Much Hadham Surgery'),
			(1008, 8000, 8003, 'JUBILEE STREET PRACTICE'),
			(1008, 8000, 8003, 'Chandos Medical Centre'),
			(1008, 8000, 8003, 'Woodhouse Health Centre'),
			(1008, 8000, 8003, 'The Bloomsbury Surgery'),
			(1008, 8000, 8003, 'Tooting South Medical Centre'),
			(1008, 8000, 8003, 'St Pauls Way Medical Centre'),
			(1008, 8000, 8003, 'Pinner Road Surgery'),
			(1008, 8000, 8003, 'THE BARKANTINE PRACTICE'),
			(1008, 8000, 8003, 'Elsdale Street Surgery'),
			(1008, 8000, 8003, 'Whitechapel Health Centre'),
			(1008, 8000, 8003, 'Harrow Road Surgery'),
			(1008, 8000, 8003, 'HGP AT PAVILION MEDICAL CENTRE'),
			(1008, 8000, 8003, 'Hunsdon Branch Surgery'),
			(1008, 8000, 8003, 'BRIXTON HILL GROUP PRACTICE'),
			(1008, 8000, 8003, 'Abbey Road Pod'),
			(1008, 8000, 8003, 'ALBION HEALTH CENTRE'),
			(1008, 8000, 8003, 'Phoenix Surgery'),
			(1008, 8000, 8003, 'Harford Health Centre'),
			(1008, 8000, 8003, 'West Hampstead Medical Practice'),
			(1008, 8000, 8003, 'Little Hadham Branch Surgery'),
			(1008, 8000, 8003, 'STROUTS PLACE MEDICAL CENTRE'),
			(1008, 8000, 8003, 'Talbot Medical Centre'),
			(1008, 8000, 8003, 'Goodinge Group Practice'),
			(1008, 8000, 8003, 'Lower Clapton Medical Centre'),
			(1008, 8000, 8003, 'Allerton Medical Centre'),
		-- .Acute care (8005)
			(1008, 8000, 8005, 'Inpatient NHS'),
			(1008, 8000, 8005, 'Hospital inpatient'),
			(1008, 8000, 8005, 'Hospital'),
			(1008, 8000, 8005, 'Accident & Emergency'),
			(1008, 8000, 8005, 'Hospital Outpatient Consultation'),
			(1008, 8000, 8005, 'Private Hospital Inpatient'),
			(1008, 8000, 8005, 'Seen in hospital out-pat.'),
			(1008, 8000, 8005, 'Hospital Inpatient'),
			(1008, 8000, 8005, 'Hospital Care:'),
			(1008, 8000, 8005, 'Inpatient Trust'),
			(1008, 8000, 8005, 'Outpatient Trust'),
			(1008, 8000, 8005, 'Hospital Department'),
			(1008, 8000, 8005, 'Casualty Department'),
			(1008, 8000, 8005, 'Hospital outpatient report'),
			(1008, 8000, 8005, 'Casualty'),
			(1008, 8000, 8005, 'A&e'),
			(1008, 8000, 8005, 'Discharged from inpatient care'),
			(1008, 8000, 8005, 'Hospital Outpatient'),
			(1008, 8000, 8005, 'Hospital outpatient'),
			
			(1008, 8000, 8005, 'Middlesex Hospital'),
			(1008, 8000, 8005, 'L.G.I.'),
			(1008, 8000, 8005, 'L G I - A & E'),
			(1008, 8000, 8005, 'The Royal London Hospital'),
			(1008, 8000, 8005, 'City Hospital'),
			(1008, 8000, 8005, 'Royal London Hospital - Whitechapel'),
			(1008, 8000, 8005, 'Guy''s & St Thomas'''),
			(1008, 8000, 8005, 'Sir Robert Peel/minor Injuries Unit'),
			(1008, 8000, 8005, 'St Mary''s Hospital'),
		-- .Community health (8006)
			(1008, 8000, 8005, 'Health Authority'),
			(1008, 8000, 8005, 'Community Health Clinic'),
			(1008, 8000, 8005, 'Community health clinic'),
			(1008, 8000, 8005, 'PCT'),

			(1008, 8000, 8005, 'Tower Hamlets Healthcare Trust'),
		-- .Social care (8007)
			(1008, 8000, 8006, 'Home Visit'),
			(1008, 8000, 8006, 'Home visit note'),
			(1008, 8000, 8007, 'Nursing home visit note'),
			(1008, 8000, 8007, 'Residential Home Visit'),
			(1008, 8000, 8007, 'Residential home visit note'),
			(1008, 8000, 8007, 'Children''s home visit note'),

	-- Mode of interaction (8001)
		-- .Face to face (8008)
			(1008, 8001, 8008, 'GP Surgery'),
			(1008, 8001, 8008, 'Surgery Consultation'),
			(1008, 8001, 8008, 'G.P.Surgery'),
			(1008, 8001, 8008, 'Home visit node'),
			(1008, 8001, 8008, 'Triage'),
			(1008, 8001, 8008, 'Walk-in Centre'),
		-- .Video (8010)
			(1008, 8001, 8010, 'Consultation via telemedicine web camera'),
		-- .Telephone (8025)
			(1008, 8001, 8025, 'Telephone call from a patient'),
			(1008, 8001, 8025, 'Telephone call to a patient'),

            
	-- Consultation purpose (8029)
		-- .Advice provided (8030)
			(1008, 8029, 8030, 'Telephone call to a patient'),
			(1008, 8029, 8030, 'Telephone call from a patient'),
		-- .Triage (8031)
			(1008, 8029, 8031, 'Triage'),
		-- .Consultation (8032)
			(1008, 8029, 8032, 'GP Surgery'),
			(1008, 8029, 8032, 'G.P.Surgery'),
            
            
	-- Site of care type (8011)
		-- .General Practice Surgery (8034)
			(1008, 8011, 8034, 'PR'),
		-- .Hospital (8035)
			(1008, 8011, 8035, 'SI'),
		-- .Day hospital (8036)
			(1008, 8011, 8036, '??')

	-- Disposition (8033)
		-- .No follow up required (8042)
			-- (1008, 8033, 8042, '???????'),

;


-- PATIENT STATUS
-- NOTE: MUST RUN BEFORE/REQUIRED BY ADMINISTRATIVE ACTION
update encounter_detail ed
set patient_status_concept_id = m.concept_id
from encounter_raw e, concept_map m
where e.id = ed.id
and m.event_type = 1008 
and m.concept_type = 8038 
and lower(concat(e.fhir_class, '|', e.fhir_type)) = m.source_term;
/*
select e.fhir_class, e.fhir_type, count(*)
from encounter_raw e
join encounter_detail ed on ed.id = e.id
where ed.patient_status_concept_id is null
group by e.fhir_class, e.fhir_type
*/

-- ADMINISTRATIVE ACTION
update encounter_detail ed
set administrative_action_concept_id = m.concept_id
from encounter_raw e, concept_map m
where e.id = ed.id
and m.event_type = 1008 
and m.concept_type = 8002 
and concat(e.fhir_adt_message_code,'|',coalesce(ed.patient_status_concept_id,0)) like m.source_term;

/*
select distinct e.fhir_adt_message_code, ed.patient_status_concept_id, count(*)
from encounter_raw e
join encounter_detail ed on ed.id = e.id
where ed.administrative_action_concept_id is null
group by e.fhir_adt_message_code, ed.patient_status_concept_id
*/

-- COMPLETION STATUS
update encounter_detail ed
set completion_status_concept_id = m.concept_id
from encounter_raw e, concept_map m
where e.id = ed.id
and m.event_type = 1008 
and m.concept_type = 8041 
and m.source_term = e.fhir_status;
/*
select e.fhir_status, count(*)
from encounter_raw e
join encounter_detail ed on ed.id = e.id
where ed.completion_status_concept_id is null
group by e.fhir_status
order by count(*) desc;
*/

-- HEALTHCARE SERVICE TYPES
update encounter_detail ed
set healthcare_service_type_concept_id = m.concept_id
from encounter_raw e, concept_map m
where e.id = ed.id
and m.event_type = 1008 
and m.concept_type = 8000 
and m.source_term = e.fhir_original_term;
/*
select e.fhir_original_term, count(*)
from encounter_raw e
join encounter_detail ed on ed.id = e.id
where ed.healthcare_service_type_concept_id is null
group by e.fhir_original_term
order by count(*) desc;
*/

-- INTERACTION MODES
update encounter_detail ed
join encounter_raw e on e.id = ed.id
join concept_map m on m.event_type = 1008 and m.concept_type = 8001 and m.source_term = e.fhir_original_term
set ed.interaction_mode_concept_id = m.concept_id;
/*
select distinct e.fhir_original_term
from encounter_raw e
join encounter_detail ed on ed.id = e.id
where ed.interaction_mode_concept_id is null;
*/


-- CONSULTATION PURPOSE
update encounter_detail ed
join encounter_raw e on e.id = ed.id
join concept_map m on m.event_type = 1008 and m.concept_type = 8029 and m.source_term = e.fhir_original_term
set ed.purpose_concept_id = m.concept_id;
/*
select distinct e.fhir_original_term
from encounter_raw e
join encounter_detail ed on ed.id = e.id
where ed.purpose_concept_id is null;
*/

-- SITE OF CARE TYPE
update encounter_detail ed
join location l on l.id = ed.location_id
join concept_map m on m.event_type = 1008 and m.concept_type = 8011 and l.type_code = m.source_term
set ed.site_of_care_type_concept_id = m.concept_id
where ed.location_id is not null;
/*
update encounter_detail ed
join organization o on o.id = ed.service_provider_organization_id
join concept_map m on m.event_type = 1008 and m.concept_type = 8011 and o.type_code = m.source_term
set ed.site_of_care_type_concept_id = m.concept_id
where ed.location_id is null;

select distinct ed.location_id, ed.service_provider_organization_id
from encounter_detail ed
where ed.site_of_care_type_concept_id is null;
*/


-- ENCOUNTER DISPOSITION
/*
update encounter_detail
set disposition_concept_id = 8042
where ????? 
*/

-- WHERE ARE MY PATIENTS
SELECT distinct p.nhs_number, e1.person_id, e1.organization_id, o.name,  e1.clinical_effective_date, e1.patient_status_concept_id
    FROM  encounter_detail AS e1
    JOIN
      ( SELECT  person_id, organization_id, MAX(clinical_effective_date) AS clinical_effective_date
            FROM  encounter_detail
            WHERE administrative_action_concept_id != 8051 -- Delete previous
            GROUP BY  person_id, organization_id
      ) AS e2 USING (person_id, organization_id, clinical_effective_date)
	JOIN patient p on p.person_id = e1.person_id and p.organization_id = e1.organization_id
	JOIN organization o on o.id = e1.service_provider_organization_id
	WHERE completion_status_concept_id = 8040 -- Ongoing;
	ORDER BY e1.patient_status_concept_id, e1.clinical_effective_date desc
