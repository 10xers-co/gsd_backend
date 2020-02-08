package gsd.backend.meta.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GsdWebException extends GsdException {
    private String code;

    private String header;

    private Integer statusCode;

    private String message;

    @Builder
    public GsdWebException(final String message, final String code, final String header, final Integer statusCode) {
        super(message);
        this.message = message;
        this.code = code;
        this.header = header;
        this.statusCode = statusCode;
    }

}
