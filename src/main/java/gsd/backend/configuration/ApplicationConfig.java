package gsd.backend.configuration;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ApplicationConfig {
    private String name;

    private String appToken;

    private boolean active;

    private List<String> resourcesAllowed = new ArrayList();
}
