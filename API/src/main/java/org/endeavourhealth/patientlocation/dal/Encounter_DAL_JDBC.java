package org.endeavourhealth.patientlocation.dal;

import com.fasterxml.jackson.databind.JsonNode;
import org.endeavourhealth.common.config.ConfigManager;
import org.endeavourhealth.coreui.framework.ContextShutdownHook;
import org.endeavourhealth.coreui.framework.StartupConfig;
import org.endeavourhealth.patientlocation.models.OngoingEncounter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class Encounter_DAL_JDBC implements Encounter_DAL, ContextShutdownHook {
    private static final Logger LOG = LoggerFactory.getLogger(Encounter_DAL_JDBC.class);
    private Connection _connection = null;
    public Encounter_DAL_JDBC() {
        StartupConfig.registerShutdownHook("Encounter_DAL_JDBC", this);
    }

    @Override
    public List<OngoingEncounter> getOngoingEncountersForMyPatientsAtOtherOrganisations(String myServiceId, List<String> otherServiceIds) {
        Connection connection = getConnection();
        try {
            List<OngoingEncounter> ongoingEncounters = new ArrayList<>();

            String sql = "SELECT distinct 12345 as nhs_number, person_id, e1.organization_id, o.name,  e1.clinical_effective_date, e1.patient_status_concept_id " +
                "    FROM  encounter_detail AS e1 " +
                "    JOIN " +
                "      ( SELECT  person_id, organization_id, MAX(clinical_effective_date) AS clinical_effective_date " +
                "            FROM  encounter_detail " +
                "            WHERE administrative_action_concept_id != 8051 " +
                "            GROUP BY  person_id, organization_id " +
                "      ) AS e2 USING (person_id, organization_id, clinical_effective_date) " +
                "JOIN organization o on o.id = e1.service_provider_organization_id "; // +
//                "JOIN patient p on p.person_id = e1.person_id and p.organization_id = ?\n" +
//                "WHERE completion_status_concept_id = 8040 -- Ongoing;\n" +
//                "AND e1.organization_id IN (" + String.join(",", Collections.nCopies(otherServiceIds.size(), "?")) +")\n";

            try (PreparedStatement stement = connection.prepareStatement(sql)) {
                int i = 1;
//                stement.setString(i++, myServiceId);
//                for(String serviceId : otherServiceIds)
//                    stement.setString(i++, serviceId);

                ResultSet rs = stement.executeQuery();

                while (rs.next()) {
                    ongoingEncounters.add(
                        new OngoingEncounter()
                        .setNhsNumber(rs.getString("nhs_number"))
                        .setOrganisation(rs.getString("name"))
                        .setDate(rs.getDate("clinical_effective_date"))
                        .setStatus(rs.getLong("patient_status_concept_id"))
                    );
                }
            }
            return ongoingEncounters;

        } catch (Exception e) {
            LOG.error("Error getting own patients ongoing encounters at other service(s)", e);
        }
        return null;
    }

    @Override
    public void contextShutdown() {
        try{
            if (_connection != null && !_connection.isClosed())
                _connection.close();
        } catch (Exception e) {
            LOG.error("Error disconnecting", e);
        }
    }

    private Connection getConnection() {
        try {
            return (_connection != null  && !_connection.isClosed()) ? _connection : (_connection = createConnection());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Connection createConnection() {
        try {
            JsonNode json = ConfigManager.getConfigurationAsJson("resource_database");
            String url = json.get("url").asText();
            String user = json.get("username").asText();
            String pass = json.get("password").asText();
            String driver = json.get("class") == null ? null : json.get("class").asText();

            if (driver != null && !driver.isEmpty())
                Class.forName(driver);

            Properties props = new Properties();

            props.setProperty("user", user);
            props.setProperty("password", pass);

            return DriverManager.getConnection(url, props);
        } catch (Exception e) {
            LOG.error("Error getting connection", e);
        }
        return null;
    }

}
