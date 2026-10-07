package com.example.oraclecrud.model;

import jakarta.persistence.*;

@Entity
@Table(name = "articolo")
public class Articolo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private Integer quantita;

    @ManyToOne
    @JoinColumn(name = "ordine_id")
    private Ordine ordine;

    public Articolo() {}

    public Articolo(String nome, Integer quantita, Ordine ordine) {
        this.nome = nome;
        this.quantita = quantita;
        this.ordine = ordine;
    }

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}
    public String getNome() {return nome;}
    public void setNome(String nome) {this.nome = nome;}
    public Integer getQuantita() {return quantita;}
    public void setQuantita(Integer quantita) {this.quantita = quantita;}
    public Ordine getOrdine() {return ordine;}
    public void setOrdine(Ordine ordine) {this.ordine = ordine;}
}
