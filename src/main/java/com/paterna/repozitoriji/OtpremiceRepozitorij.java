package com.paterna.repozitoriji;

import com.paterna.podatci.OtpremniceBP;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OtpremiceRepozitorij extends JpaRepository<OtpremniceBP, Integer> {
}
