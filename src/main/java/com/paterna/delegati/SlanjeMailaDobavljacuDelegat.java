package com.paterna.delegati;

import com.paterna.podatci.ArtikliBP;
import com.paterna.podatci.DobavljaciBP;
import com.paterna.repozitoriji.ArtikliRepozitorij;
import com.paterna.repozitoriji.DobavljaciRepozitorij;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;

@Component
public class SlanjeMailaDobavljacuDelegat implements JavaDelegate {

    @Autowired
    private ArtikliRepozitorij artikliRepozitorij;

    @Autowired
    private DobavljaciRepozitorij dobavljaciRepozitorij;

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        List<Map<String, Object>> artikli = (List<Map<String, Object>>) execution.getVariable("artikli");
        List<Map<String, Object>> naruceniArtikli = new ArrayList<>();
        LocalDate najdaljiDatum = (LocalDate) execution.getVariable("najdaljiDatum");
        LocalDate datumNarudzbe = LocalDate.now();

        for (Map<String, Object> artikl : artikli) {
            if (!imaDovoljnoZaliha(artikl)) {
                String naziv = (String) artikl.get("artikl");
                int narucenaKolicina = (int) artikl.get("kolicina");
                Optional<ArtikliBP> artiklOpt = artikliRepozitorij.findByNaziv(naziv.trim());
                if (artiklOpt.isPresent()) {
                    ArtikliBP artiklIzBaze = artiklOpt.get();
                    DobavljaciBP dobavljac = dobavljaciRepozitorij
                            .findById(artiklIzBaze.getDobavljacArtikla())
                            .orElse(null);

                    LocalDate datumIsporuke = datumNarudzbe.plusDays(dobavljac.getVrijemeDostave());

                    if (datumIsporuke.isAfter(najdaljiDatum)) {
                        najdaljiDatum = datumIsporuke;
                    }

                    int minimalnaKolicina = artiklIzBaze.getMinimalnaKolicina();
                    int zaNaruciti = narucenaKolicina + (minimalnaKolicina * 2);

                    if (dobavljac != null && dobavljac.getEmail() != null) {
                        execution.setVariable("trajanjeDostave", "PT" + dobavljac.getVrijemeDostave() + "S");

                           SimpleMailMessage message = new SimpleMailMessage();
                           message.setTo(dobavljac.getEmail());
                           message.setSubject("Upit za narudžbu: " + naziv + " !");
                           message.setText("Poštovani " + dobavljac.getNaziv() + ",\n\n" +
                                   "Molimo vas da isporučite " + zaNaruciti + " kom artikla '" + naziv + "'.\n\n" +
                                   "Datum narudžbe: " + datumNarudzbe + "\n" +
                                   "Očekivani datum isporuke: " + datumIsporuke + "\n" +
                                   "Vrijeme dostave prema našim uvjetima: " + dobavljac.getVrijemeDostave() + " dana\n\n" +
                                   "Srdačan pozdrav,\nPaterna d.o.o");
                           mailSender.send(message);

                        Map<String, Object> noviArtikl = new HashMap<>(artikl);
                        noviArtikl.put("narucenaKolicina", zaNaruciti);
                        naruceniArtikli.add(noviArtikl);
                    }
                }

            }
        }
        execution.setVariable("naruceniArtikli", naruceniArtikli);
        execution.setVariable("najdaljiDatum", najdaljiDatum);
        execution.setVariable("najdaljiDatumString", najdaljiDatum.toString());
    }

    private boolean imaDovoljnoZaliha(Map<String, Object> artikl) {
        String naziv = (String) artikl.get("artikl");
        int narucenaKolicina = (int) artikl.get("kolicina");

        Optional<ArtikliBP> artiklIzBaze = artikliRepozitorij.findByNaziv(naziv);

        if (artiklIzBaze.isEmpty()) {
            throw new RuntimeException("Artikl '" + naziv + "' nije pronađen u bazi.");
        }

        int trenutnoNaSkladistu = artiklIzBaze.get().getTrenutnaKolicina();
        return trenutnoNaSkladistu >= narucenaKolicina;
    }
}

