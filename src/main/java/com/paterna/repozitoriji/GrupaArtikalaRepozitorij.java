package com.paterna.repozitoriji;

import com.paterna.podatci.GrupeArtikalaBP;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GrupaArtikalaRepozitorij extends JpaRepository<GrupeArtikalaBP, Integer> {
}
