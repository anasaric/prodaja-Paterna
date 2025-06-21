package com.paterna.delegati;

import com.paterna.podatci.VozilaBP;
import com.paterna.repozitoriji.VoziloRepozitorij;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DostavaArtikalaDelegat implements JavaDelegate {

    @Autowired
    private VoziloRepozitorij voziloRepository;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        try {
            int voziloId = (int) execution.getVariable("voziloId");
            String registracija = "";

            if (voziloId != 0) {
                Optional<VozilaBP> voziloOptional = voziloRepository.findById(voziloId);
                if (voziloOptional.isPresent()) {
                    registracija = voziloOptional.get().getRegistracija();
                    VozilaBP vozilo = voziloOptional.get();
                    vozilo.setDostupno(true);
                    voziloRepository.save(vozilo);
                    System.out.println("Vozilo s registracijom " + registracija + " je sada dostupno.");
                } else {
                    System.out.println("Vozilo s registracijom " + registracija + " nije pronađeno u bazi.");
                }
            } else {
                System.out.println("Nema spremljene registracije vozila u Camunda varijabli.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Greška prilikom dohvaćanja vozila: " + e.getMessage());
        }
    }
}