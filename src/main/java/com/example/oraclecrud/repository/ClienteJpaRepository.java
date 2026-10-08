package com.example.oraclecrud.repository;

import com.example.oraclecrud.model.Cliente;
import com.example.oraclecrud.dto.ClienteProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteJpaRepository extends JpaRepository<Cliente, Long> {
    // JPQL projection to retrieve only name and email for clients whose name contains the given fragment
    @Query("SELECT c FROM Cliente c WHERE c.nome LIKE CONCAT('%', ?1, '%')")
    java.util.List<com.example.oraclecrud.dto.ClienteProjection> findByNomeContainingJPQL(String fragment);

    // Native SQL projection equivalent
    @Query(value = "SELECT nome, email FROM cliente WHERE nome LIKE '%' || ?1 || '%'", nativeQuery = true)
    java.util.List<com.example.oraclecrud.dto.ClienteProjection> findByNomeContainingNative(String fragment);
}
