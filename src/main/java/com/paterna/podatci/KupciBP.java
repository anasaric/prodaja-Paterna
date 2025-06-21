package com.paterna.podatci;

import javax.persistence.*;

@Entity
@Table(name = "kupci")
public class KupciBP {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    private String ime;
    private String prezime;
    private String adresa;
    private String kontakt;
    private String mail;

    @Column(name = "tip_kupca")
    private Integer tipKupca;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getKontakt() {
        return kontakt;
    }

    public void setKontakt(String kontakt) {
        this.kontakt = kontakt;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Integer getTipKupca() {
        return tipKupca;
    }

    public void setTipKupca(Integer tipKupca) {
        this.tipKupca = tipKupca;
    }
}
