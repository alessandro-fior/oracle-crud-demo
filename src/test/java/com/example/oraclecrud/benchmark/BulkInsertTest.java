package com.example.oraclecrud.benchmark;

import com.example.oraclecrud.model.Cliente;
import com.example.oraclecrud.repository.ClienteJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class BulkInsertTest {

    @Autowired
    private ClienteJpaRepository jpaRepo;

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Test
    @Transactional
    void salvaUnClienteConJpa() {
        String email = "test-jpa-" + UUID.randomUUID() + "@example.com";
        Cliente saved = jpaRepo.save(new Cliente(null, "Test", "Jpa",
                email, "0001"));

        assertThat(saved.getId()).isNotNull();
        assertThat(jpaRepo.findById(saved.getId())).isPresent();
    }

    @Test
    @Transactional
    void salvaUnClienteConJdbc() {
        int rows = jdbcTemplate.update(
                "INSERT INTO cliente (nome, cognome, email, telefono) " +
                        "VALUES (:nome, :cognome, :email, :telefono)",
                new MapSqlParameterSource()
                        .addValue("nome", "Test")
                        .addValue("cognome", "Jdbc")
                        .addValue("email", "test-jdbc@example.com")
                        .addValue("telefono", "0002"));

        assertThat(rows).isEqualTo(1);
    }
}
