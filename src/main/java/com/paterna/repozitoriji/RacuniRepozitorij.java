package com.paterna.repozitoriji;

import com.paterna.podatci.RacuniBP;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RacuniRepozitorij extends JpaRepository<RacuniBP, Integer> {
}
