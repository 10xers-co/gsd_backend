package gsd.backend.meta.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import com.codahale.metrics.MetricRegistry;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;

@Produces (MediaType.APPLICATION_JSON)
@Path ("/metrics")
@Slf4j
public class MetricsResource {
    private final MetricRegistry metricRegistry;

    public MetricsResource(final MetricRegistry metricRegistry) {
        this.metricRegistry = metricRegistry;
    }

    @GET
    public Object fetchMetricByKeyAndType(@QueryParam ("key") final String key, @QueryParam ("metric_type") final String metricType) {
        log.debug("key =  {} and metricType = {}", key, metricType);

        if (Strings.isNullOrEmpty(metricType)) {
            return metricRegistry.getMetrics();
        }

        switch (metricType) {
            case "gauge":
                return Strings.isNullOrEmpty(key) ? metricRegistry.getGauges() : metricRegistry.getGauges().get(key);
            case "meter":
                return Strings.isNullOrEmpty(key) ? metricRegistry.getMeters() : metricRegistry.getMeters().get(key);
            case "counter":
                return Strings.isNullOrEmpty(key) ? metricRegistry.getCounters() : metricRegistry.getCounters().get(key);
            case "timer":
                return Strings.isNullOrEmpty(key) ? metricRegistry.getTimers() : metricRegistry.getTimers().get(key);
            case "histogram":
                return Strings.isNullOrEmpty(key) ? metricRegistry.getHistograms() : metricRegistry.getHistograms().get(key);
            default:
                return metricRegistry.getMetrics();
        }
    }
}