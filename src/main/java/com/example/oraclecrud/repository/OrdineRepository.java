package com.example.oraclecrud.repository;

import com.example.oraclecrud.model.Ordine;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdineRepository extends CrudRepository<Ordine, Long> {
    @Query("SELECT o FROM Ordine o JOIN FETCH o.articoli WHERE o.id = ?1")
    Ordine findWithArticoli(Long id);
}
