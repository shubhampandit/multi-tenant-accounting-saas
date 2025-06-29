package com.shubham.saas.tenant.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

@Service
public class TenantUserService {
    private final JdbcTemplate jdbcTemplate;
    private final DataSource dataSource;
    private final PasswordEncoder passwordEncoder;


    public TenantUserService(JdbcTemplate jdbcTemplate, DataSource dataSource, PasswordEncoder passwordEncoder) {
        this.jdbcTemplate = jdbcTemplate;
        this.dataSource = dataSource;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void createAdminUser(String schemaName, String email, String rawPassword){
        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM ${schema}.users".replace("${schema}", schemaName), Integer.class);

        if (count != null && count == 0){
            String encodedPassword = passwordEncoder.encode(rawPassword);

            String insertQuery = String.format("INSERT INTO %s.users (email, password, role) VALUES ('%s', '%s', '%s')",
                    schemaName, email, encodedPassword, "ADMIN");

            jdbcTemplate.execute(insertQuery);
        }
    }
}
