package com.paterna.podatci;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "otpremnice")
public class OtpremniceBP {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "datum_otpreme")
    private LocalDate datumOtpreme;

    @Column(name = "kupac_id")
    private int kupacId;

    @Column(name = "vozilo_id")
    private int voziloId;

    @Column(name = "narudzbenica_id")
    private int narudzbenicaId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDatumOtpreme() {
        return datumOtpreme;
    }

    public void setDatumOtpreme(LocalDate datumOtpreme) {
        this.datumOtpreme = datumOtpreme;
    }

    public int getKupacId() {
        return kupacId;
    }

    public void setKupacId(int kupacId) {
        this.kupacId = kupacId;
    }

    public int getVoziloId() {
        return voziloId;
    }

    public void setVoziloId(int voziloId) {
        this.voziloId = voziloId;
    }

    public int getNarudzbenicaId() {
        return narudzbenicaId;
    }

    public void setNarudzbenicaId(int narudzbenicaId) {
        this.narudzbenicaId = narudzbenicaId;
    }

}
