package com.paterna.repozitoriji;


import com.paterna.podatci.PrimkaBP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrimkaRepozitorij extends JpaRepository<PrimkaBP, Integer> {}

