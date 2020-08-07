package com.spminfiscaa.service;

import com.spminfiscaa.domain.SolEngNonDec;
import com.spminfiscaa.service.dto.SolEngNonDecDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.spminfiscaa.domain.SolEngNonDec}.
 */
public interface SolEngNonDecService {

    /**
     * Save a solEngNonDec.
     *
     * @param solEngNonDecDTO the entity to save.
     * @return the persisted entity.
     */
    SolEngNonDecDTO save(SolEngNonDecDTO solEngNonDecDTO);

    /**
     * Get all the solEngNonDecs.
     *
     * @return the list of entities.
     */
    List<SolEngNonDecDTO> findAll();


    /**
     * Get the "id" solEngNonDec.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SolEngNonDecDTO> findOne(Long id);

    /**
     * Delete the "id" solEngNonDec.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    //Methode Excel
    void save(MultipartFile file);

    ByteArrayInputStream load();

    //somme solde Engagee Non Declarer
    public Long somme();

    String showDate();


}
