package com.paterna.repozitoriji;

import com.paterna.podatci.VozilaBP;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VoziloRepozitorij extends JpaRepository<VozilaBP, Integer> {
    Optional<VozilaBP> findFirstByDostupnoTrue();
}