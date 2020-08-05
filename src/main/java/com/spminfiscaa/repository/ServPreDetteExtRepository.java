package com.spminfiscaa.repository;

import antlr.collections.List;
import com.spminfiscaa.domain.ServPreDetteExt;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.Map;

/**
 * Spring Data  repository for the ServPreDetteExt entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServPreDetteExtRepository extends JpaRepository<ServPreDetteExt, Long> {
    @Query("SELECT sum(t.total) from ServPreDetteExt t")
    int sommeServPreDetteExt();

    @Query(value = "select count (ac) as count, function('date_format', max(ac.date), '%Y, " +
        "%m,%d') as date FROM ServPreDetteExt ac where ac.date BETWEEN : startDate and : " +
        "enDate group by function('date_format', max(ac.date), '%Y, %m, %d') ")
    Map<String, Object> showDateServPreDetteExt(@Param("startDate") Date startDate, @Param("enDate") Date enDate);

    @Query("select  max (dat.date) as date from ServPreDetteExt dat  ")
    LocalDate showLastAddServPreDetteExt();

}
