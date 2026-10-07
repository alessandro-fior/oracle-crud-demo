package com.example.oraclecrud.benchmark;

import com.example.oraclecrud.model.Cliente;
import com.example.oraclecrud.repository.ClienteJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DisplayName("Cache eviction and read latency")
public class CacheEvictionTest {

    @Autowired
    private ClienteJpaRepository jpaRepo;

    @Autowired
    private EntityManager entityManager;

    @Test
    @Transactional
    public void testReadWithoutFirstLevelCache() {
        // assicurati che il record esista
        Cliente c = new Cliente(null, "Cache", "Evict", "cache@example.com", "123456");
        c = jpaRepo.save(c);

        // svuota la cache
        entityManager.clear();

        assertThat(jpaRepo.findById(c.getId())).isPresent();
    }
}
