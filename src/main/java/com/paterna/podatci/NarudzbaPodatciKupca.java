package com.paterna.podatci;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class NarudzbaPodatciKupca {
    private String ime;
    private String prezime;
    private String kontakt;
    private String adresa;
    private String mail;
    @JsonProperty("tip_kupca")
    private int tipKupca;
    private List<NarudzbaArtikli> artikli;
    private String nacinPlacanja;
    private boolean zeliDostavu;
    private boolean zeliOdgoduPlacanja;


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

    public String getKontakt() {
        return kontakt;
    }

    public void setKontakt(String kontakt) {
        this.kontakt = kontakt;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public List<NarudzbaArtikli> getArtikli() {
        return artikli;
    }

    public void setArtikli(List<NarudzbaArtikli> artikli) {
        this.artikli = artikli;
    }

    public int getTipKupca() {
        return tipKupca;
    }

    public void setTipKupca(int tipKupca) {
        this.tipKupca = tipKupca;
    }

    public String getNacinPlacanja() {
        return nacinPlacanja;
    }

    public void setNacinPlacanja(String nacinPlacanja) {
        this.nacinPlacanja = nacinPlacanja;
    }

    public boolean isZeliDostavu() {
        return zeliDostavu;
    }

    public void setZeliDostavu(boolean zeliDostavu) {
        this.zeliDostavu = zeliDostavu;
    }

    public boolean isZeliOdgoduPlacanja() {
        return zeliOdgoduPlacanja;
    }

    public void setZeliOdgoduPlacanja(boolean zeliOdgoduPlacanja) {
        this.zeliOdgoduPlacanja = zeliOdgoduPlacanja;
    }
}

