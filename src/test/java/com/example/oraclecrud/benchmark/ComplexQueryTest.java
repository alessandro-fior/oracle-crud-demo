package com.example.oraclecrud.benchmark;

import com.example.oraclecrud.model.Articolo;
import com.example.oraclecrud.model.Ordine;
import com.example.oraclecrud.repository.OrdineRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DisplayName("Complex query with relations")
public class ComplexQueryTest {

    @Autowired
    private OrdineRepository ordineRepo;

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Test
    @Transactional
    public void testJpaFetchJoin() {
        // setup data
        Ordine ordine = new Ordine("Ordine di prova");
        ordine = ordineRepo.save(ordine);
        List<Articolo> articoli = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            Articolo a = new Articolo("Articolo" + i, i, ordine);
            articoli.add(a);
        }
        ordine.setArticoli(articoli);
        ordineRepo.save(ordine);
        Ordine loaded = ordineRepo.findWithArticoli(ordine.getId());
        assertThat(loaded.getArticoli()).hasSize(10);
    }

    @Test
    @Transactional
    public void testJdbcJoin() {
        Ordine ordine = ordineRepo.save(new Ordine("Ordine JDBC di prova"));
        ordine.setArticoli(new ArrayList<>());
        ordine.getArticoli().add(new Articolo("Articolo JDBC", 1, ordine));
        ordineRepo.save(ordine);

        String sql = "SELECT o.id AS o_id, o.descrizione, a.id AS a_id, a.nome, a.quantita " +
                "FROM ordine o JOIN articolo a ON a.ordine_id = o.id";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, new HashMap<>());
        Map<Long, Ordine> map = new HashMap<>();
        for (Map<String, Object> row : rows) {
            Long oId = ((Number) row.get("o_id")).longValue();
            Ordine ord = map.computeIfAbsent(oId, k -> new Ordine());
            ord.setId(oId);
            ord.setDescrizione((String) row.get("descrizione"));
            Articolo art = new Articolo();
            art.setId(((Number) row.get("a_id")).longValue());
            art.setNome((String) row.get("nome"));
            art.setQuantita(((Number) row.get("quantita")).intValue());
            if (ord.getArticoli() == null) {
                ord.setArticoli(new ArrayList<>());
            }
            ord.getArticoli().add(art);
        }
        assertThat(map).containsKey(ordine.getId());
    }
}
