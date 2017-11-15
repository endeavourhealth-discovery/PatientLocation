package org.endeavourhealth.patientlocation;

import org.endeavourhealth.common.config.ConfigManager;
import org.endeavourhealth.common.config.ConfigManagerException;

public class Application {
    public static void main(String[] argv) {
        System.out.println("Initializing...");
        try {
            ConfigManager.Initialize("patient-location");
            new Logic().execute();
        } catch (ConfigManagerException e) {
            System.out.println("Error loading configuration");
        }
    }
}
