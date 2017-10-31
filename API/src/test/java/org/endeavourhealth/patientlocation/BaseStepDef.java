package org.endeavourhealth.patientlocation;

public class BaseStepDef {
    protected String parseString(String string) {
        if (string == null || string.isEmpty())
            return null;

        if ("\"\"".equals(string))
            return "";

        if (string.startsWith("\"") && string.endsWith("\""))
            return string.substring(1, string.length() - 1);

        throw new IllegalArgumentException("Not a valid string value");
    }
}
