package com.paterna.delegati;

import com.paterna.podatci.ArtikliBP;
import com.paterna.podatci.DobavljaciBP;
import com.paterna.podatci.PrimkaBP;
import com.paterna.repozitoriji.ArtikliRepozitorij;
import com.paterna.repozitoriji.DobavljaciRepozitorij;
import com.paterna.repozitoriji.PrimkaRepozitorij;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class ZaprimitiArtikleDelegat implements JavaDelegate {

    @Autowired
    private ArtikliRepozitorij artikliRepozitorij;

    @Autowired
    private DobavljaciRepozitorij dobavljaciRepozitorij;

    @Autowired
    private PrimkaRepozitorij primkaRepozitorij;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        List<Map<String, Object>> artikli = (List<Map<String, Object>>) execution.getVariable("naruceniArtikli");
        LocalDate datum = LocalDate.now();

        int i = 1;
        for (Map<String, Object> artiklMap : artikli) {

            String naziv = (String) artiklMap.get("artikl");
            String narucenaKolicina = String.valueOf((int) artiklMap.get("narucenaKolicina"));

            Optional<ArtikliBP> artiklOpt = artikliRepozitorij.findByNaziv(naziv);
            if (artiklOpt.isPresent()) {
                ArtikliBP artikl = artiklOpt.get();
                String nabavnaCijena = String.valueOf((double) artikl.getNabavnaCijena());
                String prodajnaCijena = String.valueOf((double) artikl.getCijena());
                String mjernaJedinica = String.valueOf(artikl.getMjernaJedinica());

                Optional<DobavljaciBP> dobavljacOpt = dobavljaciRepozitorij.findById(artikl.getDobavljacArtikla());

                String dobavljac = dobavljacOpt.map(DobavljaciBP::getNaziv).orElse("Nepoznat");

                execution.setVariable("primka" + i, datum.toString());
                execution.setVariable("nazivArtikla" + i, naziv);
                execution.setVariable("dobavljacArtikla" + i, dobavljac);
                execution.setVariable("kolicina" + i, narucenaKolicina);
                execution.setVariable("jedinica" + i, mjernaJedinica);
                execution.setVariable("nabavnaCijena" + i, nabavnaCijena);
                execution.setVariable("prodajnaCijena" + i, prodajnaCijena);

                int novaKolicina = (int) artiklMap.get("narucenaKolicina");
                artikl.setTrenutnaKolicina(artikl.getTrenutnaKolicina() + novaKolicina);
                artikliRepozitorij.save(artikl);

                PrimkaBP primka = new PrimkaBP();
                primka.setDobavljacId(dobavljacOpt.get().getId());
                primka.setDatumZaprimanja(LocalDate.now());
                primka.setArtiklId(artikl.getId());
                primkaRepozitorij.save(primka);

            }
            i++;
        }
        System.out.println("Izvjestaj uspjesno generiran!");
    }
}

