package com.paterna.repozitoriji;

import com.paterna.podatci.NarudzbenicaBP;
import org.springframework.data.jpa.repository.JpaRepository;


public interface NarudzbeniceRepozitorij extends JpaRepository<NarudzbenicaBP, Long> {}