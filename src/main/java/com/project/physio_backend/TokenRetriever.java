package com.project.physio_backend;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class TokenRetriever {

    private final JdbcTemplate jdbcTemplate;

    public TokenRetriever(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public String getTokenFromDatabase(String username) {
        String query = "SELECT token FROM user_tokens WHERE username = ?";
        return jdbcTemplate.queryForObject(query, String.class, username);
    }
}
