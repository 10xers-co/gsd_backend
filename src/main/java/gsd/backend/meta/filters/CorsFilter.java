package gsd.backend.meta.filters;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import org.apache.commons.lang3.StringUtils;
import com.google.common.base.Strings;
import com.google.common.collect.Sets;

public class CorsFilter implements ContainerResponseFilter {
    private final Set<String> accessControlAllowOriginSet;

    public CorsFilter(final String accessControlAllowOrigin) {
        if (Strings.isNullOrEmpty(accessControlAllowOrigin)) {
            this.accessControlAllowOriginSet = Sets.newHashSet("*");
        } else {
            this.accessControlAllowOriginSet = Stream.of(accessControlAllowOrigin.split(",")).filter(i -> !StringUtils.isBlank(i)).map(String::trim)
                                                     .map(String::toLowerCase).collect(Collectors.toSet());
        }
    }

    public CorsFilter() {
        this("");
    }

    public void filter(final ContainerRequestContext requestContext, final ContainerResponseContext responseContext) {
        String origin = requestContext.getHeaderString("Origin");
        if (!Strings.isNullOrEmpty(origin)) {
            origin = origin.toLowerCase();
            if (accessControlAllowOriginSet.contains(origin) || accessControlAllowOriginSet.contains("*")) {
                responseContext.getHeaders().add("Access-Control-Allow-Origin", origin);
                responseContext.getHeaders().add("Vary", "Origin");
            }
        } else {
            responseContext.getHeaders().add("Access-Control-Allow-Origin", accessControlAllowOriginSet.stream().collect(Collectors.joining(",")));
        }
        responseContext.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, HEAD, OPTIONS, PATCH");
        responseContext.getHeaders().add("Access-Control-Allow-Credentials", true);
        final String requestHeaders = requestContext.getHeaderString("Access-Control-Request-Headers");
        if (!Strings.isNullOrEmpty(requestHeaders)) {
            responseContext.getHeaders().add("Access-Control-Allow-Headers", requestHeaders);
        }
    }

}
