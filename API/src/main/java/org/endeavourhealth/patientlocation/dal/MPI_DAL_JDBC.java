package org.endeavourhealth.patientlocation.dal;

import com.fasterxml.jackson.databind.JsonNode;
import org.endeavourhealth.common.config.ConfigManager;
import org.endeavourhealth.coreui.framework.ContextShutdownHook;
import org.endeavourhealth.coreui.framework.StartupConfig;
import org.endeavourhealth.patientlocation.models.OpenEpisode;
import org.endeavourhealth.patientlocation.models.ServicePatient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.*;

public class MPI_DAL_JDBC implements MPI_DAL, ContextShutdownHook {
    private static final Logger LOG = LoggerFactory.getLogger(MPI_DAL_JDBC.class);
    private Connection _connection = null;
    public MPI_DAL_JDBC() {
        StartupConfig.registerShutdownHook("MPI_DAL_JDBC", this);
    }

    @Override
    public List<ServicePatient> getMyRegisteredPatientsAtOtherServices(String myServiceId, List<String> otherServiceIds) {
        Connection connection = getConnection();
        try {
            List<ServicePatient> servicePatients = new ArrayList<>();

            String sql = "SELECT distinct other.service_id, other.system_id, other.patient_id, other.nhs_number, other.surname, other.forenames " +
                "FROM patient_search own " +
                "JOIN patient_search other on other.nhs_number = own.nhs_number " +
                "where own.service_id = ? " +
                "and own.nhs_number is not null " +
                "and own.registration_end is null " +
                "and other.registration_end is null " +
                "and other.service_id in (" + String.join(",", Collections.nCopies(otherServiceIds.size(), "?")) +")";

            try (PreparedStatement stement = connection.prepareStatement(sql)) {
                int i = 1;
                stement.setString(i++, myServiceId);
                for(String serviceId : otherServiceIds)
                    stement.setString(i++, serviceId);

                ResultSet rs = stement.executeQuery();

                while (rs.next()) {
                    servicePatients.add(
                        new ServicePatient()
                            .setServiceId(rs.getString(1))
                            .setSystemId(rs.getString(2))
                            .setPatientId(rs.getString(3))
                            .setNhsNumber(rs.getString(4))
                            .setSurname(rs.getString(5))
                            .setForename(rs.getString(6))
                    );
                }
            }
            return servicePatients;

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
