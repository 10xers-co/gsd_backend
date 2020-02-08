package gsd.backend.meta.healthcheck;

import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.sql.DataSource;
import com.codahale.metrics.health.HealthCheck;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
public class DataSourceHealthCheck extends HealthCheck {
    private final DataSource dataSource;

    @Override
    protected Result check() {
        try (final Connection connection = dataSource.getConnection(); final PreparedStatement preparedStatement = connection
                .prepareStatement("SELECT 1 FROM DUAL")) {
            preparedStatement.execute();
            return Result.healthy();
        } catch (final Exception ex) {
            return Result.unhealthy(ex.getMessage());
        }

    }
}
