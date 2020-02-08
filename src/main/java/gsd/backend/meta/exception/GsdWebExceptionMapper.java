package gsd.backend.meta.exception;

import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GsdWebExceptionMapper implements ExceptionMapper<GsdWebException> {
    @Override
    @Produces (value = MediaType.APPLICATION_JSON)
    public Response toResponse(GsdWebException exception) {
        Map<String, Object> response = new HashMap<>();
        response.put("code", exception.getCode());
        response.put("message", exception.getMessage());
        response.put("status_code", exception.getStatusCode());
        response.put("header", exception.getHeader());
        log.error("GSD web exception - Code: {}, Message: {}, Header: {}, status code: {}", exception.getCode(), exception.getMessage(),
                exception.getHeader(), exception.getStatusCode());
        return Response.status(exception.getStatusCode()).entity(response).build();
    }
}
