package com.paterna.delegati;

import com.paterna.podatci.OtpremniceBP;
import com.paterna.repozitoriji.OtpremiceRepozitorij;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.spin.json.SpinJsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;


@Component
public class SpremiOtpremnicuUBazuDelegat implements JavaDelegate {

    @Autowired
    private OtpremiceRepozitorij otpremnicaRepozitorij;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        Object datum = execution.getVariable("najdaljiDatum");
        LocalDate najdaljiDatum;
        if (datum instanceof String) {
            najdaljiDatum = LocalDate.parse((String) datum);
        } else {
            najdaljiDatum = (LocalDate) datum;
        }

        int randomBroj = ThreadLocalRandom.current().nextInt(3, 6);
        LocalDate datumOtpreme = najdaljiDatum.plusDays(randomBroj);
        String datumOtpremeString = datumOtpreme.toString();
        execution.setVariable("datumOtpreme", datumOtpremeString);

        int kupacId = (int) execution.getVariable("kupacId");
        int voziloId = (int) execution.getVariable("voziloId");
        int narudzbenicaId = (int) execution.getVariable("narudzbenicaId");

        OtpremniceBP o = new OtpremniceBP();
        o.setDatumOtpreme(datumOtpreme);
        o.setKupacId(kupacId);
        o.setVoziloId(voziloId);
        o.setNarudzbenicaId(narudzbenicaId);
        otpremnicaRepozitorij.save(o);
    }
}
