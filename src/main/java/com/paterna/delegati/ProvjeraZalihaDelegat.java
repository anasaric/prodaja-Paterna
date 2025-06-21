package com.paterna.delegati;

import com.fasterxml.jackson.core.type.TypeReference;
import com.paterna.podatci.ArtikliBP;
import com.paterna.repozitoriji.ArtikliRepozitorij;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.spin.Spin;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class ProvjeraZalihaDelegat implements JavaDelegate {

    private final ArtikliRepozitorij repozitorij;

    public ProvjeraZalihaDelegat(ArtikliRepozitorij repozitorij) {
        this.repozitorij = repozitorij;
    }

    @Override
    public void execute(DelegateExecution execution) throws Exception {

        List<Map<String, Object>> artikli = (List<Map<String, Object>>) execution.getVariable("artikli");

        if (artikli == null) {
            throw new IllegalArgumentException("Varijabla 'artikli' nije postavljena u procesu!");
        }

        boolean sveDostupno = true;

        for (Map<String, Object> artikl : artikli) {
            String naziv = (String) artikl.get("artikl");
            int narucenaKolicina = (int) artikl.get("kolicina");

            Optional<ArtikliBP> artiklIzBaze = repozitorij.findByNaziv(naziv);

            if (artiklIzBaze.isEmpty()) {
                throw new RuntimeException("Artikl '" + naziv + "' nije pronađen u bazi.");
            }

            int trenutnoNaSkladistu = artiklIzBaze.get().getTrenutnaKolicina();

            if (trenutnoNaSkladistu < narucenaKolicina) {
                sveDostupno = false;
                break;
            }
        }

        execution.setVariable("dovoljnoZalihe", sveDostupno);
        LocalDate najdaljiDatum = LocalDate.now();
        execution.setVariable("najdaljiDatum", najdaljiDatum);
        execution.setVariable("najdaljiDatumString", najdaljiDatum.toString());
    }
}
