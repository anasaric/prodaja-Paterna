package com.paterna.repozitoriji;
import com.paterna.podatci.ArtikliBP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArtikliRepozitorij extends JpaRepository<ArtikliBP, Integer> {
    List<ArtikliBP> findByGrupaId(int grupaId);
    Optional<ArtikliBP> findByNaziv(String naziv);
}



