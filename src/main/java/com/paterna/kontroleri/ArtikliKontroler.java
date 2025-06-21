package com.paterna.kontroleri;
import com.paterna.podatci.ArtikliBP;
import com.paterna.repozitoriji.ArtikliRepozitorij;
import javax.annotation.PostConstruct;

import com.paterna.repozitoriji.GrupaArtikalaRepozitorij;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/artikli")
public class ArtikliKontroler {

    @Autowired
    private ArtikliRepozitorij repozitorij;

    @GetMapping("/grupa/{grupaId}")
    public List<ArtikliBP> getArtikliByGrupa(@PathVariable("grupaId") int grupaId) {
        return repozitorij.findByGrupaId(grupaId);
    }
}



