package com.example.oraclecrud.repository;

import com.example.oraclecrud.model.Cliente;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ClienteRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final JdbcTemplate plainJdbcTemplate;

    public ClienteRepository(NamedParameterJdbcTemplate jdbcTemplate, JdbcTemplate plainJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.plainJdbcTemplate = plainJdbcTemplate;
    }

    private final RowMapper<Cliente> rowMapper = (rs, rowNum) -> {
        Cliente c = new Cliente();
        c.setId(rs.getLong("id"));
        c.setNome(rs.getString("nome"));
        c.setCognome(rs.getString("cognome"));
        c.setEmail(rs.getString("email"));
        c.setTelefono(rs.getString("telefono"));
        return c;
    };

    public Cliente save(Cliente c) {
        String sql = "INSERT INTO cliente (nome, cognome, email, telefono) VALUES (:nome, :cognome, :email, :telefono)";
        Long id = plainJdbcTemplate.execute((org.springframework.jdbc.core.ConnectionCallback<Long>) connection -> {
            try (java.sql.PreparedStatement statement = connection.prepareStatement(sql, new String[] {"ID"})) {
                statement.setString(1, c.getNome());
                statement.setString(2, c.getCognome());
                statement.setString(3, c.getEmail());
                statement.setString(4, c.getTelefono());
                statement.executeUpdate();
                try (java.sql.ResultSet keys = statement.getGeneratedKeys()) {
                    if (!keys.next()) {
                        throw new IllegalStateException("Oracle did not return the generated CLIENTE id");
                    }
                    return keys.getLong(1);
                }
            }
        });
        c.setId(id);
        return c;
    }

    public List<Cliente> findAll() {
        String sql = "SELECT id, nome, cognome, email, telefono FROM cliente";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Cliente findById(Long id) {
        String sql = "SELECT id, nome, cognome, email, telefono FROM cliente WHERE id = :id";
        SqlParameterSource params = new MapSqlParameterSource("id", id);
        List<Cliente> list = jdbcTemplate.query(sql, params, rowMapper);
        return list.isEmpty() ? null : list.get(0);
    }

    public int update(Cliente c) {
        String sql = "UPDATE cliente SET nome = :nome, cognome = :cognome, email = :email, telefono = :telefono WHERE id = :id";
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", c.getId())
                .addValue("nome", c.getNome())
                .addValue("cognome", c.getCognome())
                .addValue("email", c.getEmail())
                .addValue("telefono", c.getTelefono());
        return jdbcTemplate.update(sql, params);
    }

    public int delete(Long id) {
        String sql = "DELETE FROM cliente WHERE id = :id";
        SqlParameterSource params = new MapSqlParameterSource("id", id);
        return jdbcTemplate.update(sql, params);
    }
}
