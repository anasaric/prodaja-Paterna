package com.paterna.delegati;

import com.paterna.podatci.ArtikliBP;
import com.paterna.podatci.RacuniBP;
import com.paterna.podatci.StavkeRacunaBP;
import com.paterna.repozitoriji.ArtikliRepozitorij;
import com.paterna.repozitoriji.RacuniRepozitorij;
import com.paterna.repozitoriji.StavkeRacunaRepozitorij;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class RacunDelegat {

    @Autowired
    private RacuniRepozitorij racuniRepozitorij;

    @Autowired
    private StavkeRacunaRepozitorij stavkeRacunaRepozitorij;

    @Autowired
    private ArtikliRepozitorij artikliRepozitorij;

    public void unosRacuna(int kupacId, int narudzbenicaId, double cijenaBezPdv,
                                   double pdv, double ukupnaCijena, String nacinPlacanja,
                                   String statusRacuna, List<Map<String, Object>> artikli) {

        RacuniBP racun = new RacuniBP();
        racun.setDatumIzdavanja(LocalDate.now());
        racun.setCijenaBezPDV(cijenaBezPdv);
        racun.setPdv(pdv);
        racun.setCijenaUkupno(ukupnaCijena);
        racun.setPlacanje(nacinPlacanja);
        racun.setStatusRacuna(statusRacuna);
        racun.setNarudzbenicaId(narudzbenicaId);
        racun.setKupacId(kupacId);

        racuniRepozitorij.save(racun);

        for (Map<String, Object> artikl : artikli) {
            String artiklNaziv = artikl.get("artikl").toString();
            int kolicina = (int) artikl.get("kolicina");

            int artiklId = 0;
            Optional<ArtikliBP> pronadiArtikl = artikliRepozitorij.findByNaziv(artiklNaziv);
            if (pronadiArtikl.isPresent()) {
                ArtikliBP uneseniArtikl = pronadiArtikl.get();
                artiklId = uneseniArtikl.getId();
            }

            StavkeRacunaBP stavka = new StavkeRacunaBP();
            stavka.setRacunId(racun.getId());
            stavka.setArtiklId(artiklId);
            stavka.setKolicina(kolicina);

            stavkeRacunaRepozitorij.save(stavka);
        }
    }
}
