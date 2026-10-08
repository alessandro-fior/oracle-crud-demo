package com.example.oraclecrud.benchmark;

import com.example.oraclecrud.model.Cliente;
import com.example.oraclecrud.repository.ClienteJpaRepository;
import com.example.oraclecrud.service.ClienteService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DisplayName("JPQL vs Native projection queries")
public class ProjectionQueryTest {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ClienteJpaRepository jpaRepo;

    @Test
    @Transactional
    public void testProjectionQueries() {
        // Arrange
        clienteService.create(new Cliente(null, "Mario", "Rossi", "mario@example.com", "123"));
        clienteService.create(new Cliente(null, "Maria", "Verdi", "maria@example.com", "456"));
        clienteService.create(new Cliente(null, "Giovanni", "Bianchi", "giovanni@example.com", "789"));

        // Act - JPQL
        List<?> jpqlResults = jpaRepo.findByNomeContainingJPQL("Mar");
        // Act - Native
        List<?> nativeResults = jpaRepo.findByNomeContainingNative("Mar");

        // Assert
        assertThat(jpqlResults).hasSize(2); // Mario and Maria
        assertThat(nativeResults).hasSize(2);
        // Verify that names match expected
        assertThat(jpqlResults).extracting("nome").containsExactly("Mario", "Maria");
        assertThat(nativeResults).extracting("nome").containsExactly("Mario", "Maria");
    }
}
