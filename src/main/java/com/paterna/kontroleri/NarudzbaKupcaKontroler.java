package com.paterna.kontroleri;

import com.paterna.podatci.*;
import com.paterna.repozitoriji.ArtikliRepozitorij;
import com.paterna.repozitoriji.KupciRepozitorij;
import com.paterna.repozitoriji.NarudzbeniceRepozitorij;
import com.paterna.repozitoriji.StavkeNarudzbeniceRepozitorij;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.spin.Spin;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api/narudzbe")
public class NarudzbaKupcaKontroler {

    private final RuntimeService runtimeService;
    private final KupciRepozitorij kupciRepozitorij;
    private final NarudzbeniceRepozitorij narudzbenicaRepozitorij;
    private final StavkeNarudzbeniceRepozitorij stavkeNarudzbeniceRepozitorij;
    private final ArtikliRepozitorij artikliRepozitorij;

    public NarudzbaKupcaKontroler(RuntimeService runtimeService, KupciRepozitorij kupciRepozitorij, NarudzbeniceRepozitorij narudzbenicaRepozitorij,
                                  StavkeNarudzbeniceRepozitorij stavkeNarudzbeniceRepozitorij, ArtikliRepozitorij artikliRepozitorij) {
        this.runtimeService = runtimeService;
        this.kupciRepozitorij = kupciRepozitorij;
        this.narudzbenicaRepozitorij = narudzbenicaRepozitorij;
        this.stavkeNarudzbeniceRepozitorij = stavkeNarudzbeniceRepozitorij;
        this.artikliRepozitorij = artikliRepozitorij;
    }

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody NarudzbaPodatciKupca narudzba) {
        Map<String, Object> variables = new HashMap<>();
        KupciBP kupac;

        Optional<KupciBP> kupacProvjera = kupciRepozitorij.findByImeAndPrezimeAndKontakt(
                narudzba.getIme(),
                narudzba.getPrezime(),
                narudzba.getKontakt()
        );

        if (kupacProvjera.isPresent()) {
            kupac = kupacProvjera.get();
            kupac.setMail(narudzba.getMail());
        } else {
            KupciBP novi = new KupciBP();
            novi.setIme(narudzba.getIme());
            novi.setPrezime(narudzba.getPrezime());
            novi.setAdresa(narudzba.getAdresa());
            novi.setKontakt(narudzba.getKontakt());
            novi.setMail(narudzba.getMail());
            novi.setTipKupca(4);
            kupac = kupciRepozitorij.save(novi);
        }

        variables.put("kupacId", kupac.getId());
        variables.put("imeKupca", narudzba.getIme());
        variables.put("prezimeKupca", narudzba.getPrezime());
        variables.put("kontaktBroj", narudzba.getKontakt());
        variables.put("adresaKupca", narudzba.getAdresa());
        kupac.setMail(narudzba.getMail());
        variables.put("mail", narudzba.getMail());
        variables.put("tip_kupca", kupac.getTipKupca());

        variables.put("nacinPlacanja", narudzba.getNacinPlacanja());

        if(narudzba.isZeliDostavu()){
            variables.put("zeliDostavu", "Potrebna je dostava");
        } else variables.put("zeliDostavu", "Nije potrebna dostava");

        if(narudzba.isZeliOdgoduPlacanja()){
            variables.put("zeliOdgoduPlacanja", "Da");
        } else variables.put("zeliOdgoduPlacanja", "Ne");

        NarudzbenicaBP narudzbenica = new NarudzbenicaBP();
        narudzbenica.setKupacId(kupac.getId());
        narudzbenica.setDatumNarudzbe(new Date());
        narudzbenica = narudzbenicaRepozitorij.save(narudzbenica);


        String tekstArtikla = "";
        List<Map<String, Object>> artikliKupca = new ArrayList<>();
        int i = 1;
        double ukupnaCijena = 0;
        for (NarudzbaArtikli a : narudzba.getArtikli()) {
            Map<String, Object> artikl = new HashMap<>();

            artikl.put("artikl", a.getArtikl());
            artikl.put("kolicina", a.getKolicina());
            artikliKupca.add(artikl);

            tekstArtikla = a.getArtikl() + "   -   " + a.getKolicina() + " kom";
            variables.put("artikl_" + i, tekstArtikla);

            Optional<ArtikliBP> pronadiArtikl = artikliRepozitorij.findByNaziv(a.getArtikl());
            if (pronadiArtikl.isPresent()) {
                ArtikliBP uneseniArtikl = pronadiArtikl.get();
                int artiklId = uneseniArtikl.getId();

                ukupnaCijena = ukupnaCijena + a.getKolicina() * uneseniArtikl.getCijena();
                String tekstArtiklaRacun = tekstArtikla + "    :      " + uneseniArtikl.getCijena() + " €  komad";
                variables.put("artikl_racun_" + i, tekstArtiklaRacun);

                StavkeNarudzbeniceBP artiklNarudzbenice = new StavkeNarudzbeniceBP();
                artiklNarudzbenice.setNarudzbenicaId(narudzbenica.getId());
                artiklNarudzbenice.setArtiklId(artiklId);
                artiklNarudzbenice.setKolicina(a.getKolicina());

                stavkeNarudzbeniceRepozitorij.save(artiklNarudzbenice);
            } else {
                return ResponseEntity.badRequest().body("Artikl nije pronađen: " + a.getArtikl());
            }
            i++;
        }

        String ukupnaCijenaString = String.format("%.2f", ukupnaCijena);
        variables.put("narudzbenicaId", narudzbenica.getId());
        variables.put("artikli", artikliKupca);
        variables.put("brojArtikala", narudzba.getArtikli().size());
        variables.put("ukupnaCijena", ukupnaCijenaString);

        runtimeService.startProcessInstanceByKey("proces-prodaje-Paterna", variables);

        return ResponseEntity.ok("Narudžba je ispravno kreirana!");
    }
}