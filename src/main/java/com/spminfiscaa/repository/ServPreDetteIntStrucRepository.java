package com.spminfiscaa.repository;

import com.spminfiscaa.domain.ServPreDetteIntStruc;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

/**
 * Spring Data  repository for the ServPreDetteIntStruc entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServPreDetteIntStrucRepository extends JpaRepository<ServPreDetteIntStruc, Long> {
    @Query("SELECT sum(t.total) from ServPreDetteIntStruc t")
    public int sommeServPreDetteIntStruc();

    @Query("select  max (dat.date) as date from ServPreDetteIntStruc dat  ")
    LocalDate showServPreDetteIntStruc();
}
