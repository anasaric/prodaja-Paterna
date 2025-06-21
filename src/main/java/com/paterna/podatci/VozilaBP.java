package com.paterna.podatci;

import javax.persistence.*;

@Entity
@Table(name = "vozila")
public class VozilaBP {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String registracija;

    private Boolean dostupno;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRegistracija() {
        return registracija;
    }

    public void setRegistracija(String registracija) {
        this.registracija = registracija;
    }

    public Boolean getDostupno() {
        return dostupno;
    }

    public void setDostupno(Boolean dostupno) {
        this.dostupno = dostupno;
    }
}
