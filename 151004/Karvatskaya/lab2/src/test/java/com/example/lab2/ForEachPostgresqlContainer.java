package com.example.lab2;

import org.testcontainers.containers.PostgreSQLContainer;

public class ForEachPostgresqlContainer extends PostgreSQLContainer<ForEachPostgresqlContainer> {
    private static final String IMAGE_VERSION = "postgres:16.2";
    private static ForEachPostgresqlContainer container;

    private ForEachPostgresqlContainer() {
        super(IMAGE_VERSION);
    }

    public static ForEachPostgresqlContainer getInstance() {
        if (container == null) {
            container = new ForEachPostgresqlContainer();
        }
        return container;
    }

    @Override
    public void start() {
        super.start();
        System.setProperty("DB_URL", container.getJdbcUrl());
        System.setProperty("DB_USERNAME", container.getUsername());
        System.setProperty("DB_PASSWORD", container.getPassword());
    }

    @Override
    public void stop() {
        //do nothing, JVM handles shut down
    }
}