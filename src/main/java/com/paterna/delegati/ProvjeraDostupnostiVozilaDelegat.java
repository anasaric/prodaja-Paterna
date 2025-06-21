package com.paterna.delegati;

import com.paterna.podatci.VozilaBP;
import com.paterna.repozitoriji.VoziloRepozitorij;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ProvjeraDostupnostiVozilaDelegat implements JavaDelegate {

    @Autowired
    private VoziloRepozitorij voziloRepository;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        try {
            boolean dostava = false;
            Optional<VozilaBP> slobodnoVozilo = voziloRepository.findFirstByDostupnoTrue();
            if (slobodnoVozilo.isPresent()) {
                VozilaBP vozilo = slobodnoVozilo.get();
                vozilo.setDostupno(false);
                voziloRepository.save(vozilo);

                execution.setVariable("voziloId", vozilo.getId());
                execution.setVariable("voziloRegistracija", vozilo.getRegistracija());
                execution.setVariable("dostavaOmogucena", true);
            } else {
                execution.setVariable("dostavaOmogucena", false);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Greška u delegatu: " + e.getMessage());
        }
    }
}