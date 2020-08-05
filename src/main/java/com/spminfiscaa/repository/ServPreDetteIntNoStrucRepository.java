package com.spminfiscaa.repository;

import com.spminfiscaa.domain.ServPreDetteIntNoStruc;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.Map;

/**
 * Spring Data  repository for the ServPreDetteIntNoStruc entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServPreDetteIntNoStrucRepository extends JpaRepository<ServPreDetteIntNoStruc, Long> {
    @Query("SELECT sum(t.total) from ServPreDetteIntNoStruc t")
    public int sommeServPreDetteIntNoStruc();

    @Query("select  max (dat.date) as date from ServPreDetteIntNoStruc dat  ")
    LocalDate showServPreDetteIntNoStruc();
}
