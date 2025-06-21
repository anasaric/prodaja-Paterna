package com.paterna.podatci;

import javax.persistence.*;

@Entity
@Table(name = "stavke_narudzbenice")
public class StavkeNarudzbeniceBP {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "narudzbenica_id")
    private int narudzbenicaId;

    @Column(name = "artikl_id")
    private int artiklId;

    @Column(name = "kolicina")
    private int kolicina;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNarudzbenicaId() {
        return narudzbenicaId;
    }

    public void setNarudzbenicaId(int narudzbenicaId) {
        this.narudzbenicaId = narudzbenicaId;
    }

    public int getArtiklId() {
        return artiklId;
    }

    public void setArtiklId(int artiklId) {
        this.artiklId = artiklId;
    }

    public int getKolicina() {
        return kolicina;
    }

    public void setKolicina(int kolicina) {
        this.kolicina = kolicina;
    }

}

