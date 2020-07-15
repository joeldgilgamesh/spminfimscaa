package com.spminfiscaa.service.impl;

import com.spminfiscaa.csvmanage.CsvSPDE;
import com.spminfiscaa.service.ServPreDetteExtService;
import com.spminfiscaa.domain.ServPreDetteExt;
import com.spminfiscaa.repository.ServPreDetteExtRepository;
import com.spminfiscaa.service.dto.ServPreDetteExtDTO;
import com.spminfiscaa.service.mapper.ServPreDetteExtMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link ServPreDetteExt}.
 */
@Service
@Transactional
public class ServPreDetteExtServiceImpl implements ServPreDetteExtService {

    private final Logger log = LoggerFactory.getLogger(ServPreDetteExtServiceImpl.class);

    private final ServPreDetteExtRepository servPreDetteExtRepository;

    private final ServPreDetteExtMapper servPreDetteExtMapper;

    public ServPreDetteExtServiceImpl(ServPreDetteExtRepository servPreDetteExtRepository, ServPreDetteExtMapper servPreDetteExtMapper) {
        this.servPreDetteExtRepository = servPreDetteExtRepository;
        this.servPreDetteExtMapper = servPreDetteExtMapper;
    }

    /**
     * Save a servPreDetteExt.
     *
     * @param servPreDetteExtDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ServPreDetteExtDTO save(ServPreDetteExtDTO servPreDetteExtDTO) {
        log.debug("Request to save ServPreDetteExt : {}", servPreDetteExtDTO);
        ServPreDetteExt servPreDetteExt = servPreDetteExtMapper.toEntity(servPreDetteExtDTO);
        servPreDetteExt = servPreDetteExtRepository.save(servPreDetteExt);
        return servPreDetteExtMapper.toDto(servPreDetteExt);
    }

    /**
     * Get all the servPreDetteExts.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ServPreDetteExtDTO> findAll() {
        log.debug("Request to get all ServPreDetteExts");
        return servPreDetteExtRepository.findAll().stream()
            .map(servPreDetteExtMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one servPreDetteExt by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ServPreDetteExtDTO> findOne(Long id) {
        log.debug("Request to get ServPreDetteExt : {}", id);
        return servPreDetteExtRepository.findById(id)
            .map(servPreDetteExtMapper::toDto);
    }

    /**
     * Delete the servPreDetteExt by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ServPreDetteExt : {}", id);

        servPreDetteExtRepository.deleteById(id);
    }
    //Methode Excel
    @Override
    public void save(MultipartFile file) {
        try {
            List<ServPreDetteExt> servPreDetteExts = CsvSPDE.readSend(file.getInputStream());
            servPreDetteExtRepository.saveAll(servPreDetteExts);
        } catch (IOException e) {
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }
    }

    @Override
    public ByteArrayInputStream load() {
        List<ServPreDetteExt> servPreDetteExts = servPreDetteExtRepository.findAll();
        ByteArrayInputStream in = CsvSPDE.writeSend(servPreDetteExts);
        return in;
    }
}
