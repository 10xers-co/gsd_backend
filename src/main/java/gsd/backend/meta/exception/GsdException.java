package gsd.backend.meta.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GsdException extends RuntimeException {
    public GsdException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public GsdException(String message, Throwable cause) {
        super(message, cause);
    }

    public GsdException(String message) {
        super(message);
    }

    public GsdException(Throwable cause) {
        super(cause);
    }
}
