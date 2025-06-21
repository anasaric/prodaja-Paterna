package com.paterna.podatci;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "racuni")
public class RacuniBP {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "datum_izdavanja")
    private LocalDate datumIzdavanja;

    @Column(name = "cijena_bez_pdv")
    private double cijenaBezPDV;

    @Column(name = "pdv")
    private double pdv;

    @Column(name = "cijena_ukupno")
    private double cijenaUkupno;

    @Column(name = "placanje")
    private String placanje;

    @Column(name = "status_racuna")
    private String statusRacuna;

    @Column(name = "narudzbenica_id")
    private int narudzbenicaId;

    @Column(name = "kupac_id")
    private int kupacId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDatumIzdavanja() {
        return datumIzdavanja;
    }

    public void setDatumIzdavanja(LocalDate datumIzdavanja) {
        this.datumIzdavanja = datumIzdavanja;
    }

    public double getCijenaBezPDV() {
        return cijenaBezPDV;
    }

    public void setCijenaBezPDV(double cijenaBezPDV) {
        this.cijenaBezPDV = cijenaBezPDV;
    }

    public double getPdv() {
        return pdv;
    }

    public void setPdv(double pdv) {
        this.pdv = pdv;
    }

    public double getCijenaUkupno() {
        return cijenaUkupno;
    }

    public void setCijenaUkupno(double cijenaUkupno) {
        this.cijenaUkupno = cijenaUkupno;
    }

    public String getPlacanje() {
        return placanje;
    }

    public void setPlacanje(String placanje) {
        this.placanje = placanje;
    }

    public String getStatusRacuna() {
        return statusRacuna;
    }

    public void setStatusRacuna(String statusRacuna) {
        this.statusRacuna = statusRacuna;
    }

    public int getNarudzbenicaId() {
        return narudzbenicaId;
    }

    public void setNarudzbenicaId(int narudzbenicaId) {
        this.narudzbenicaId = narudzbenicaId;
    }

    public int getKupacId() { return kupacId;  }

    public void setKupacId(int kupacId) {  this.kupacId = kupacId; }

}
