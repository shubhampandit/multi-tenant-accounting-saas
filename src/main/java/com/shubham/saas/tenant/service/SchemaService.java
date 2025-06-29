package com.shubham.saas.tenant.service;

import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
public class SchemaService {
    private final JdbcTemplate jdbcTemplate;
    private final DataSource dataSource;

    public SchemaService(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.dataSource = dataSource;
    }

    public void createSchema(String schemaName){
        jdbcTemplate.execute("CREATE SCHEMA IF NOT EXISTS " + schemaName);
    }

    public void migrateSchema(String schemaName){

    }

    public void initTenantSchema(String schemaName){
        createSchema(schemaName);

        try {
            runSqlScriptForSchema(schemaName, "sql/tenant_schema_init.sql");
        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize schema for tenant: " + schemaName, e);
        }
    }

    private void runSqlScriptForSchema(String schemaName, String resourcePath) throws IOException {
        var resource = new ClassPathResource(resourcePath);
        String sql = new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);

        // Replace ${schema} with actual schema name
        sql = sql.replace("${schema}", schemaName);

        // Split by semicolon (basic parser – works for simple scripts)
        String[] statements = sql.split(";");

        for (String stmt : statements) {
            String trimmed = stmt.trim();
            if (!trimmed.isEmpty()) {
                jdbcTemplate.execute(trimmed);
            }
        }
    }
}
