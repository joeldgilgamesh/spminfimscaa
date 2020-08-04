package com.spminfiscaa.repository;

import com.spminfiscaa.domain.ServPreDetteIntStruc;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ServPreDetteIntStruc entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServPreDetteIntStrucRepository extends JpaRepository<ServPreDetteIntStruc, Long> {
    @Query("SELECT sum(t.total) from ServPreDetteIntStruc t")
    public int sommeServPreDetteIntStruc();
}
