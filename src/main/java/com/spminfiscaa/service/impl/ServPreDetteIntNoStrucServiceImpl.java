package com.spminfiscaa.service.impl;

import com.spminfiscaa.csvmanage.CsvSPDINS;
import com.spminfiscaa.service.ServPreDetteIntNoStrucService;
import com.spminfiscaa.domain.ServPreDetteIntNoStruc;
import com.spminfiscaa.repository.ServPreDetteIntNoStrucRepository;
import com.spminfiscaa.service.dto.ServPreDetteIntNoStrucDTO;
import com.spminfiscaa.service.mapper.ServPreDetteIntNoStrucMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link ServPreDetteIntNoStruc}.
 */
@Service
@Transactional
public class ServPreDetteIntNoStrucServiceImpl implements ServPreDetteIntNoStrucService {

    private final Logger log = LoggerFactory.getLogger(ServPreDetteIntNoStrucServiceImpl.class);

    private final ServPreDetteIntNoStrucRepository servPreDetteIntNoStrucRepository;

    private final ServPreDetteIntNoStrucMapper servPreDetteIntNoStrucMapper;

    public ServPreDetteIntNoStrucServiceImpl(ServPreDetteIntNoStrucRepository servPreDetteIntNoStrucRepository, ServPreDetteIntNoStrucMapper servPreDetteIntNoStrucMapper) {
        this.servPreDetteIntNoStrucRepository = servPreDetteIntNoStrucRepository;
        this.servPreDetteIntNoStrucMapper = servPreDetteIntNoStrucMapper;
    }

    /**
     * Save a servPreDetteIntNoStruc.
     *
     * @param servPreDetteIntNoStrucDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ServPreDetteIntNoStrucDTO save(ServPreDetteIntNoStrucDTO servPreDetteIntNoStrucDTO) {
        log.debug("Request to save ServPreDetteIntNoStruc : {}", servPreDetteIntNoStrucDTO);
        ServPreDetteIntNoStruc servPreDetteIntNoStruc = servPreDetteIntNoStrucMapper.toEntity(servPreDetteIntNoStrucDTO);
        servPreDetteIntNoStruc = servPreDetteIntNoStrucRepository.save(servPreDetteIntNoStruc);
        return servPreDetteIntNoStrucMapper.toDto(servPreDetteIntNoStruc);
    }

    /**
     * Get all the servPreDetteIntNoStrucs.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ServPreDetteIntNoStrucDTO> findAll() {
        log.debug("Request to get all ServPreDetteIntNoStrucs");
        return servPreDetteIntNoStrucRepository.findAll().stream()
            .map(servPreDetteIntNoStrucMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one servPreDetteIntNoStruc by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ServPreDetteIntNoStrucDTO> findOne(Long id) {
        log.debug("Request to get ServPreDetteIntNoStruc : {}", id);
        return servPreDetteIntNoStrucRepository.findById(id)
            .map(servPreDetteIntNoStrucMapper::toDto);
    }

    /**
     * Delete the servPreDetteIntNoStruc by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ServPreDetteIntNoStruc : {}", id);

        servPreDetteIntNoStrucRepository.deleteById(id);
    }

    @Override
    public void save(MultipartFile file) {
        try {
            List<ServPreDetteIntNoStruc> servPreDetteIntNoStrucs = CsvSPDINS.readSend(file.getInputStream());
            servPreDetteIntNoStrucRepository.saveAll(servPreDetteIntNoStrucs);
        } catch (IOException e) {
            throw new RuntimeException("fail to store xlsx data: " + e.getMessage());
        }
    }

    @Override
    public ByteArrayInputStream load() {

        List<ServPreDetteIntNoStruc> servPreDetteIntNoStrucs = servPreDetteIntNoStrucRepository.findAll();
        ByteArrayInputStream in = CsvSPDINS.writeSend(servPreDetteIntNoStrucs);
        return in;
    }

    @Override
    public int somme() {
        return servPreDetteIntNoStrucRepository.sommeServPreDetteIntNoStruc();
    }

}
