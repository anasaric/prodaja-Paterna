package com.paterna.podatci;
import javax.persistence.*;

@Entity
@Table(name = "tip_kupca")
public class TipKupcaBP {

    @Id
    private int id;

    @Column(name = "naziv")
    private String naziv;

    @Column(name = "opis_kupca")
    private String opis;

    @Column(name = "ostvareni_popust")
    private int ostvareniPopust;

    @Column(name = "odgoda_placanja")
    private int odgodaPlacanja;

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

    public int getOstvareniPopust() {
        return ostvareniPopust;
    }

    public void setOstvareniPopust(int ostvareniPopust) {
        this.ostvareniPopust = ostvareniPopust;
    }

    public int getOdgodaPlacanja() {
        return odgodaPlacanja;
    }

    public void setOdgodaPlacanja(int odgodaPlacanja) {
        this.odgodaPlacanja = odgodaPlacanja;
    }
}
