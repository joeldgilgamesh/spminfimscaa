package com.spminfiscaa.service;

import com.spminfiscaa.service.dto.ServPreDetteIntNoStrucDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.spminfiscaa.domain.ServPreDetteIntNoStruc}.
 */
public interface ServPreDetteIntNoStrucService {

    /**
     * Save a servPreDetteIntNoStruc.
     *
     * @param servPreDetteIntNoStrucDTO the entity to save.
     * @return the persisted entity.
     */
    ServPreDetteIntNoStrucDTO save(ServPreDetteIntNoStrucDTO servPreDetteIntNoStrucDTO);

    /**
     * Get all the servPreDetteIntNoStrucs.
     *
     * @return the list of entities.
     */
    List<ServPreDetteIntNoStrucDTO> findAll();


    /**
     * Get the "id" servPreDetteIntNoStruc.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ServPreDetteIntNoStrucDTO> findOne(Long id);

    /**
     * Delete the "id" servPreDetteIntNoStruc.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    //Methode Excel
    void save(MultipartFile file);

    ByteArrayInputStream load();

    int somme();

    String showDate();

}
