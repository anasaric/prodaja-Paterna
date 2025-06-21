package com.paterna.repozitoriji;

import com.paterna.podatci.DobavljaciBP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DobavljaciRepozitorij extends JpaRepository<DobavljaciBP, Integer> {
    DobavljaciBP findByNaziv(String naziv);
}
