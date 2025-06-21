package com.paterna.kontroleri;
import com.paterna.repozitoriji.GrupaArtikalaRepozitorij;
import com.paterna.podatci.GrupeArtikalaBP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/grupe-artikala")
@CrossOrigin(origins = "*")
public class GrupeArtikalaKontroler {

    @Autowired
    private GrupaArtikalaRepozitorij repozitorij;

    @GetMapping
    public List<GrupeArtikalaBP> getSveGrupeArtikala() {
        List<GrupeArtikalaBP> grupe = repozitorij.findAll();
        grupe.forEach(g -> System.out.println(g.getNazivGrupe()));
        return repozitorij.findAll();
    }
}


