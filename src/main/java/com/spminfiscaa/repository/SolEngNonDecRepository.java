package com.spminfiscaa.repository;

import com.spminfiscaa.domain.SolEngNonDec;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the SolEngNonDec entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SolEngNonDecRepository extends JpaRepository<SolEngNonDec, Long> {
}
