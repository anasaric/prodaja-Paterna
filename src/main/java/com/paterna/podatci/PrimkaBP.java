package com.paterna.podatci;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "primke")
public class PrimkaBP {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "dobavljac_id")
    private int dobavljacId;

    @Column(name = "datum_zaprimanja")
    private LocalDate datumZaprimanja;

    @Column(name = "artikl_id")
    private int artiklId;

    public int getId() {
        return id;
    }

    public int getDobavljacId() {
        return dobavljacId;
    }

    public void setDobavljacId(int dobavljacId) {
        this.dobavljacId = dobavljacId;
    }

    public LocalDate getDatumZaprimanja() {
        return datumZaprimanja;
    }

    public void setDatumZaprimanja(LocalDate datumZaprimanja) {
        this.datumZaprimanja = datumZaprimanja;
    }

    public int getArtiklId() {
        return artiklId;
    }

    public void setArtiklId(int artiklId) {
        this.artiklId = artiklId;
    }
}
