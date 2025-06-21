package com.paterna.delegati;

import com.paterna.podatci.KupciBP;
import com.paterna.podatci.RacuniBP;
import com.paterna.podatci.TipKupcaBP;
import com.paterna.repozitoriji.KupciRepozitorij;
import com.paterna.repozitoriji.RacuniRepozitorij;
import com.paterna.repozitoriji.TipKupcaRepozitorij;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;

@Component
public class InformacijeOKupcuDelegat implements JavaDelegate {

    @Autowired
    private KupciRepozitorij kupciRepozitorij;

    @Autowired
    private TipKupcaRepozitorij tipKupcaRepozitorij;

    @Autowired
    private RacunDelegat racun;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        int kupacId = (int) execution.getVariable("kupacId");
        int tip_kupca = (int) execution.getVariable("tip_kupca");
        Optional<KupciBP> kupacOpt = kupciRepozitorij.findById(kupacId);

        boolean stalniKupac = (tip_kupca == 1 || tip_kupca == 2 || tip_kupca == 3);
        execution.setVariable("stalniKupac", stalniKupac);

        String placeno = "Ne";
        Object ukupnaCijena = Double.parseDouble(execution.getVariable("ukupnaCijena").toString());

        if (kupacOpt.isPresent()) {
            String ime = (String) execution.getVariable("imeKupca");
            String prezime = (String) execution.getVariable("prezimeKupca");
            execution.setVariable("imePrezimeKupca", ime + " " + prezime);

            KupciBP kupac = kupacOpt.get();
            int tipId = kupac.getTipKupca();

            Optional<TipKupcaBP> tipOpt = tipKupcaRepozitorij.findById(tipId);
            double ukupnaCijenaNarudzbe = 0.0;
            double nakonPopusta = 0;
            int odgodaPlacanjaKupac=0;
            if (tipOpt.isPresent()) {
                TipKupcaBP tip = tipOpt.get();

                int popust = 0;
                if (ukupnaCijena != null) {
                    try {
                        ukupnaCijenaNarudzbe = Double.parseDouble(ukupnaCijena.toString().replace(",", "."));
                        popust = tip.getOstvareniPopust();
                        nakonPopusta = ukupnaCijenaNarudzbe * (1 - popust / 100.0);

                        execution.setVariable("ukupnoNakonPopusta", String.format("%.2f", nakonPopusta));
                    } catch (NumberFormatException e) {
                        execution.setVariable("ukupnoNakonPopusta", "N/A");
                    }
                } else {
                    execution.setVariable("ukupnoNakonPopusta", "N/A");
                    System.err.println("ukupnaCijena nije definirana");
                }
                odgodaPlacanjaKupac = tip.getOdgodaPlacanja();
                String popustString = String.valueOf(popust) + " %";

                execution.setVariable("opisKupca", tip.getOpis());
                execution.setVariable("ostvareniPopust", popustString);

                double pdv = 0.25;
                double cijenaBezPdv = nakonPopusta / (1 + pdv);
                double iznosPdv = nakonPopusta - cijenaBezPdv;

                String cijenaBezPdvString = String.format("%.2f", cijenaBezPdv);
                String iznosPdvString = String.format("%.2f", iznosPdv);

                execution.setVariable("cijenaBezPDV", cijenaBezPdvString);
                execution.setVariable("iznosPDV", iznosPdvString);
                String odgodaPlacanja = execution.getVariable("zeliOdgoduPlacanja").toString();
                String nacinPlacanja  = execution.getVariable("nacinPlacanja").toString();

                if(odgodaPlacanja.equals("Ne") && nacinPlacanja.equals("kartično")){
                    placeno = "Da";
                }

                if(odgodaPlacanja == "Ne" || placeno == "Da"){
                    odgodaPlacanjaKupac = 0;
                }

                LocalDate datumZaprimanja = (LocalDate) execution.getVariable("najdaljiDatum");

                LocalDate datumPlacanja =  datumZaprimanja.plusDays(tip.getOdgodaPlacanja());
                String odgodaPlacanjaKupacString = odgodaPlacanjaKupac + " dana";

                execution.setVariable("placeno", placeno);
                execution.setVariable("odgodaPlacanja", odgodaPlacanjaKupacString);
                execution.setVariable("odgodaPlacanjaGateway", odgodaPlacanja);
                execution.setVariable("datumPlacanja", datumPlacanja.toString());

                List<Map<String, Object>> artikli = (List<Map<String, Object>>) execution.getVariable("artikli");
                int narudzbenicaId = (int) execution.getVariable("narudzbenicaId");

                racun.unosRacuna(
                        kupacId,
                        narudzbenicaId,
                        cijenaBezPdv,
                        pdv,
                        (Double) ukupnaCijena,
                        nacinPlacanja,
                        placeno,
                        artikli
                );
            }
        }
    }
}