package com.example.oraclecrud.controller;

import com.example.oraclecrud.model.Cliente;
import com.example.oraclecrud.repository.ClienteJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class BenchmarkController {

    @Autowired
    private ClienteJpaRepository jpaRepo;

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @GetMapping("/bench/time")
    public Map<String, Long> timeOperations() {
        long start, end;
        Map<String, Long> result = new LinkedHashMap<>();

        // JP A – insert
        Cliente cJpa = new Cliente(null, "JpaTest", "X", "jpa@example.com", "1111111");
        start = System.nanoTime();
        Cliente persisted = jpaRepo.save(cJpa);
        end = System.nanoTime();
        result.put("JPA save", end - start);

        // JDBC – insert
        // JDBC – insert
        String sqlInsert = "INSERT INTO cliente (nome, cognome, email, telefono) VALUES (:nome, :cognome, :email, :telefono)";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("nome", "JdbcTest")
                .addValue("cognome", "Y")
                .addValue("email", "jdbc@example.com")
                .addValue("telefono", "2222222");
        start = System.nanoTime();
        jdbcTemplate.update(sqlInsert, params);
        end = System.nanoTime();
        result.put("JDBC save", end - start);
        
        // JPA – load
        start = System.nanoTime();
        jpaRepo.findById(persisted.getId());
        end = System.nanoTime();
        result.put("JPA load", end - start);
        
        // JDBC – load
        start = System.nanoTime();
        // Reuse repository's findById which is plain JDBC
        org.springframework.jdbc.core.namedparam.SqlParameterSource sqlParams = new MapSqlParameterSource("id", persisted.getId());
        java.util.List<Cliente> la = jdbcTemplate.query("SELECT id, nome, cognome, email, telefono FROM cliente WHERE id = :id", sqlParams, (rs,rowNum)-> {
            Cliente c = new Cliente();
            c.setId(rs.getLong("id"));
            c.setNome(rs.getString("nome"));
            c.setCognome(rs.getString("cognome"));
            c.setEmail(rs.getString("email"));
            c.setTelefono(rs.getString("telefono"));
            return c;
        });
        end = System.nanoTime();
        result.put("JDBC load", end - start);
        
        return result;
    }
}
