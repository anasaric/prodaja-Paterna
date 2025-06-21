package com.paterna.podatci;

import javax.persistence.*;

@Entity
@Table(name = "artikli")
public class ArtikliBP {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String naziv;

    private String opis;

    private Double cijena;

    @Column(name = "mjerna_jedinica")
    private String mjernaJedinica;

    @Column(name = "grupa_id")
    private int grupaId;

    @Column(name = "trenutna_kolicina")
    private int trenutnaKolicina;

    @Column(name = "dobavljac_artikla")
    private int dobavljacArtikla;

    @Column(name = "minimalna_kolicina")
    private int minimalnaKolicina;

    @Column(name = "nabavna_cijena")
    private double nabavnaCijena;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public int getGrupaId() {
        return grupaId;
    }

    public void setGrupaId(int grupaId) {
        this.grupaId = grupaId;
    }

    public Double getCijena() {
        return cijena;
    }

    public void setCijena(Double cijena) {
        this.cijena = cijena;
    }

    public int getTrenutnaKolicina() {
        return trenutnaKolicina;
    }

    public void setTrenutnaKolicina(int trenutnaKolicina) {
        this.trenutnaKolicina = trenutnaKolicina;
    }

    public int getDobavljacArtikla() {
        return dobavljacArtikla;
    }

    public void setDobavljacArtikla(int dobavljacArtikla) {
        this.dobavljacArtikla = dobavljacArtikla;
    }

    public int getMinimalnaKolicina() {
        return minimalnaKolicina;
    }

    public void setMinimalnaKolicina(int minimalnaKolicina) {
        this.minimalnaKolicina = minimalnaKolicina;
    }

    public double getNabavnaCijena() {
        return nabavnaCijena;
    }

    public void setNabavnaCijena(double nabavnaCijena) {
        this.nabavnaCijena = nabavnaCijena;
    }

    public String getMjernaJedinica() {
        return mjernaJedinica;
    }

    public void setMjernaJedinica(String mjernaJedinica) {
        this.mjernaJedinica = mjernaJedinica;
    }
}

