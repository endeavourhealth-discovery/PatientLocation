package org.endeavourhealth.patientlocation.metrics;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.servlets.MetricsServlet;


public class PatientLocationMetricListener extends MetricsServlet.ContextListener {
    public static final MetricRegistry patientLocationMetricRegistry = PatientLocationInstrumentedFilterContextListener.REGISTRY;

    @Override
    protected MetricRegistry getMetricRegistry() {
        return patientLocationMetricRegistry;
    }
}
