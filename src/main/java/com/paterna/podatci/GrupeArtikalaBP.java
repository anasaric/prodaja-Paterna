package com.paterna.podatci;

import javax.persistence.*;

import java.util.List;

@Entity
@Table(name = "grupe_artikala")
public class GrupeArtikalaBP {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "naziv_grupe")
    private String nazivGrupe;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNazivGrupe() {
        return nazivGrupe;
    }

    public void setNazivGrupe(String nazivGrupe) {
        this.nazivGrupe = nazivGrupe;
    }
}
