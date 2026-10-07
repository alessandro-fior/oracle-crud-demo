package com.example.oraclecrud.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "ordine")
public class Ordine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descrizione;

    @OneToMany(mappedBy = "ordine", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Articolo> articoli;

    public Ordine() {}

    public Ordine(String descrizione) {
        this.descrizione = descrizione;
    }

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}
    public String getDescrizione() {return descrizione;}
    public void setDescrizione(String d) {this.descrizione = d;}
    public List<Articolo> getArticoli() {return articoli;}
    public void setArticoli(List<Articolo> articoli) {this.articoli = articoli;}
}
