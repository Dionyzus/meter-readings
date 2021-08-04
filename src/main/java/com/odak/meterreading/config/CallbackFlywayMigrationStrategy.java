package com.odak.meterreading.config;

import org.flywaydb.core.Flyway;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.stereotype.Component;

@Component
public class CallbackFlywayMigrationStrategy implements FlywayMigrationStrategy {

    @Override
    public void migrate(Flyway flyway) {
    	//Just to make sure there are no problems with creating data.
    	flyway.clean();
        flyway.migrate();
    }
}