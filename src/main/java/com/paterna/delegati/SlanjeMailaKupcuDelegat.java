package com.paterna.delegati;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class SlanjeMailaKupcuDelegat implements JavaDelegate {

    private final JavaMailSender mailSender;

    public SlanjeMailaKupcuDelegat(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String ime = (String) execution.getVariable("imeKupca");
        String prezime = (String) execution.getVariable("prezimeKupca");
        String email = (String) execution.getVariable("mail");
        String dostava = (String) execution.getVariable("zeliDostavu");
        Boolean dostupnostVozila = (Boolean) execution.getVariable("dostavaOmogucena");
        String registracijaVozila = (String) execution.getVariable("voziloRegistracija");

        String body = "";
        if(dostava.equals("Nije potrebna dostava")){
            body = String.format("Poštovani/a %s %s,\n\n" +
                            "Vaši artikli su spremni za preuzimanje.\n" +
                            "Molimo vas da ih pokupite u roku 5 radnih dana.\n" +
                            "Hvala na kupnji!\n\n" +
                            "Paterna d.o.o.",
                    ime, prezime);
        } else {
            if (dostupnostVozila) {
                body = String.format("Poštovani/a %s %s,\n\n" +
                                "Vaši artikli su spremni za dostavu.\n" +
                                "Biti će dostavljeni kroz 3-5 radnih dana.\n" +
                                "Registracija vozila: %s.\n" +
                                "Hvala na kupnji!\n\n" +
                                "Paterna d.o.o.",
                        ime, prezime, registracijaVozila);
            } else {
                body = String.format("Poštovani/a %s %s,\n\n" +
                                "Nismo u mogućnosti dostaviti naručene proizvode zbog manjka dostupnih vozila.\n" +
                                "Molimo vas da pokupite artikle u roku 10 dana.\n" +
                                "Ako niste u mogućnosti doći po artikle organizirat ćemo vam prijevoz što prije.\n" +
                                "Hvala na kupnji!\n\n" +
                                "Paterna d.o.o.",
                        ime, prezime);
            }
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Obavijest: Artikli spremni za preuzimanje");
        message.setText(body);

        mailSender.send(message);
    }
}