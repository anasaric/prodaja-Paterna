package com.paterna.podatci;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "narudzbenica")
public class NarudzbenicaBP {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "kupci_id")
    private int kupacId;

    @Temporal(TemporalType.DATE)
    @Column(name = "datum_narudzbe")
    private Date datumNarudzbe;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getKupacId() {
        return kupacId;
    }

    public void setKupacId(int kupacId) {
        this.kupacId = kupacId;
    }

    public Date getDatumNarudzbe() {
        return datumNarudzbe;
    }

    public void setDatumNarudzbe(Date datumNarudzbe) {
        this.datumNarudzbe = datumNarudzbe;
    }

}