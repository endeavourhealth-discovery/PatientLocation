package org.endeavourhealth.patientlocation.dal;

import com.fasterxml.jackson.databind.JsonNode;
import org.endeavourhealth.common.config.ConfigManager;
import org.endeavourhealth.coreui.framework.ContextShutdownHook;
import org.endeavourhealth.coreui.framework.StartupConfig;
import org.endeavourhealth.patientlocation.models.RegisteredPatient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class MPI_DAL implements ContextShutdownHook {
    private static final Logger LOG = LoggerFactory.getLogger(MPI_DAL.class);
    private Connection _connection = null;
    public MPI_DAL() {
        StartupConfig.registerShutdownHook("MPI_DAL", this);
    }

    public List<RegisteredPatient> getRegisteredPatients() {
        Connection connection = getConnection();
        try {
            List<RegisteredPatient> registeredPatients = new ArrayList<>();

            String sql = "SELECT distinct s.service_id, s.system_id, s.patient_id, l.person_id " +
                "FROM patient_search s " +
                "JOIN patient_link l on l.patient_id = s.patient_id " +
                "WHERE registration_end is null ";

            try (PreparedStatement stement = connection.prepareStatement(sql)) {
                ResultSet rs = stement.executeQuery();

                while (rs.next()) {
                    registeredPatients.add(
                        new RegisteredPatient()
                            .setServiceId(rs.getString(1))
                            .setSystemId(rs.getString(2))
                            .setPatientId(rs.getString(3))
                            .setPersonId(rs.getString(4))
                    );
                }
            }
            return registeredPatients;

        } catch (Exception e) {
            LOG.error("Error getting own patients at other service(s)", e);
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
            JsonNode json = ConfigManager.getConfigurationAsJson("eds_db");
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
