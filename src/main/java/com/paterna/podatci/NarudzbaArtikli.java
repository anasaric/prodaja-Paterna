package com.paterna.podatci;

import java.io.Serializable;

public class NarudzbaArtikli implements Serializable {
    private static final long serialVersionUID = 1L;
    private String grupa;
    private String artikl;
    private int kolicina;

    public String getArtikl() {
        return artikl;
    }

    public void setArtikl(String artikl) {
        this.artikl = artikl;
    }

    public int getKolicina() {
        return kolicina;
    }

    public void setKolicina(int kolicina) {
        this.kolicina = kolicina;
    }

    public String getGrupa() {
        return grupa;
    }

    public void setGrupa(String grupa) {
        this.grupa = grupa;
    }
}


