package gsd.backend.meta.filters;

import java.io.IOException;
import java.util.UUID;
import javax.annotation.Priority;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import org.slf4j.MDC;
import com.google.common.base.Strings;

@Priority (100)
public class RequestTracingFilter implements ContainerRequestFilter, ContainerResponseFilter {
    public static final String TRANSACTION_ID = "X-Transaction-Id";

    public static final String REQUEST_ID = "X-Request-Id";

    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        //Set Headers for Response.
        responseContext.getHeaders().add(TRANSACTION_ID, MDC.get(TRANSACTION_ID));
        responseContext.getHeaders().add(REQUEST_ID, MDC.get(REQUEST_ID));
        // Remove MDC Keys.
        MDC.remove(TRANSACTION_ID);
        MDC.remove(REQUEST_ID);
    }

    public void filter(ContainerRequestContext requestContext) throws IOException {

        String transactionId = requestContext.getHeaderString(TRANSACTION_ID);
        String requestId = requestContext.getHeaderString(REQUEST_ID);

        if (Strings.isNullOrEmpty(transactionId)) {
            transactionId = "Txn-" + UUID.randomUUID().toString();
        }

        if (Strings.isNullOrEmpty(requestId)) {
            requestId = "Req-" + UUID.randomUUID().toString();
        }

        MDC.put(TRANSACTION_ID, transactionId);
        MDC.put(REQUEST_ID, requestId);
    }

}
