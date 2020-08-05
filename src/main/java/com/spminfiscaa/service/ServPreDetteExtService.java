package com.spminfiscaa.service;

import com.spminfiscaa.service.dto.ServPreDetteExtDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.spminfiscaa.domain.ServPreDetteExt}.
 */
public interface ServPreDetteExtService {

    /**
     * Save a servPreDetteExt.
     *
     * @param servPreDetteExtDTO the entity to save.
     * @return the persisted entity.
     */
    ServPreDetteExtDTO save(ServPreDetteExtDTO servPreDetteExtDTO);

    /**
     * Get all the servPreDetteExts.
     *
     * @return the list of entities.
     */
    List<ServPreDetteExtDTO> findAll();


    /**
     * Get the "id" servPreDetteExt.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ServPreDetteExtDTO> findOne(Long id);

    /**
     * Delete the "id" servPreDetteExt.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    //Methode Excel
    void save(MultipartFile file);

    ByteArrayInputStream load();

    int somme();

    Map<String, Object> showTri(Date dateStart, Date dateEnd);

    String showDate();

}
