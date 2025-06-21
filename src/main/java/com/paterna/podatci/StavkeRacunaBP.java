package com.paterna.podatci;
import javax.persistence.*;

@Entity
@Table(name = "stavke_racuna")
public class StavkeRacunaBP {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "racun_id")
    private int racunId;

    @Column(name = "artikl_id")
    private int artiklId;

    private int kolicina;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRacunId() {
        return racunId;
    }

    public void setRacunId(int racunId) {
        this.racunId = racunId;
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
