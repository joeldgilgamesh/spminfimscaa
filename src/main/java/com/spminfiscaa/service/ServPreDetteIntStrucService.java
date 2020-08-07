package com.spminfiscaa.service;

import com.spminfiscaa.service.dto.ServPreDetteIntStrucDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.spminfiscaa.domain.ServPreDetteIntStruc}.
 */
public interface ServPreDetteIntStrucService {

    /**
     * Save a servPreDetteIntStruc.
     *
     * @param servPreDetteIntStrucDTO the entity to save.
     * @return the persisted entity.
     */
    ServPreDetteIntStrucDTO save(ServPreDetteIntStrucDTO servPreDetteIntStrucDTO);

    /**
     * Get all the servPreDetteIntStrucs.
     *
     * @return the list of entities.
     */
    List<ServPreDetteIntStrucDTO> findAll();


    /**
     * Get the "id" servPreDetteIntStruc.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ServPreDetteIntStrucDTO> findOne(Long id);

    /**
     * Delete the "id" servPreDetteIntStruc.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    //Methode Excel
    void save(MultipartFile file);

    ByteArrayInputStream load();

    Long somme();

    String showDate();

}
