package com.paterna.delegati;

import com.paterna.podatci.ArtikliBP;
import com.paterna.repozitoriji.ArtikliRepozitorij;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class AzurirajZaliheDelegat implements JavaDelegate {

    private final ArtikliRepozitorij artikliRepozitorij;

    public AzurirajZaliheDelegat(ArtikliRepozitorij artikliRepozitorij) {
        this.artikliRepozitorij = artikliRepozitorij;
    }

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        List<Map<String, Object>> artikli = (List<Map<String, Object>>) execution.getVariable("artikli");

        for (Map<String, Object> artiklMap : artikli) {
            String naziv = (String) artiklMap.get("artikl");
            int narucenaKolicina = (int) artiklMap.get("kolicina");

            ArtikliBP artikl = artikliRepozitorij.findByNaziv(naziv)
                    .orElseThrow(() -> new RuntimeException("Artikl nije pronađen: " + naziv));

            int novaKolicina = artikl.getTrenutnaKolicina() - narucenaKolicina;
            artikl.setTrenutnaKolicina(novaKolicina);
            artikliRepozitorij.save(artikl);
        }
        System.out.println("Zalihe ažurirane.");
    }
}