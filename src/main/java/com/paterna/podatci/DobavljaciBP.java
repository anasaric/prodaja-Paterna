package com.paterna.podatci;
import javax.persistence.*;

@Entity
@Table(name = "dobavljaci")
public class DobavljaciBP {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "naziv")
    private String naziv;

    @Column(name = "adresa")
    private String adresa;

    @Column(name = "vrijeme_dostave")
    private int vrijemeDostave;

    @Column(name = "opis")
    private String opis;

    @Column(name = "email")
    private String email;

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

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public int getVrijemeDostave() {
        return vrijemeDostave;
    }

    public void setVrijemeDostave(int vrijemeDostave) {
        this.vrijemeDostave = vrijemeDostave;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
