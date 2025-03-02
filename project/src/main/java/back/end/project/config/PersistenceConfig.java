package back.end.project.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PersistenceConfig {
    @Value("${persistence.strategy}")
    private String strategy;

    public PersistenceStrategy getStrategy() {
        return PersistenceStrategy.valueOf(strategy.toUpperCase());
    }
}
