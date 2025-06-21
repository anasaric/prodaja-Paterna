package com.paterna.repozitoriji;

import com.paterna.podatci.KupciBP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KupciRepozitorij extends JpaRepository<KupciBP, Integer> {
    Optional<KupciBP> findByImeAndPrezimeAndKontakt(String ime, String prezime, String kontakt);
}