package com.spminfiscaa.service.impl;

import com.spminfiscaa.csvmanage.CsvSPDE;
import com.spminfiscaa.csvmanage.CsvSPDIS;
import com.spminfiscaa.service.ServPreDetteIntStrucService;
import com.spminfiscaa.domain.ServPreDetteIntStruc;
import com.spminfiscaa.repository.ServPreDetteIntStrucRepository;
import com.spminfiscaa.service.dto.ServPreDetteIntStrucDTO;
import com.spminfiscaa.service.mapper.ServPreDetteIntStrucMapper;
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
 * Service Implementation for managing {@link ServPreDetteIntStruc}.
 */
@Service
@Transactional
public class ServPreDetteIntStrucServiceImpl implements ServPreDetteIntStrucService {

    private final Logger log = LoggerFactory.getLogger(ServPreDetteIntStrucServiceImpl.class);

    private final ServPreDetteIntStrucRepository servPreDetteIntStrucRepository;

    private final ServPreDetteIntStrucMapper servPreDetteIntStrucMapper;

    public ServPreDetteIntStrucServiceImpl(ServPreDetteIntStrucRepository servPreDetteIntStrucRepository, ServPreDetteIntStrucMapper servPreDetteIntStrucMapper) {
        this.servPreDetteIntStrucRepository = servPreDetteIntStrucRepository;
        this.servPreDetteIntStrucMapper = servPreDetteIntStrucMapper;
    }

    /**
     * Save a servPreDetteIntStruc.
     *
     * @param servPreDetteIntStrucDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ServPreDetteIntStrucDTO save(ServPreDetteIntStrucDTO servPreDetteIntStrucDTO) {
        log.debug("Request to save ServPreDetteIntStruc : {}", servPreDetteIntStrucDTO);
        ServPreDetteIntStruc servPreDetteIntStruc = servPreDetteIntStrucMapper.toEntity(servPreDetteIntStrucDTO);
        servPreDetteIntStruc = servPreDetteIntStrucRepository.save(servPreDetteIntStruc);
        return servPreDetteIntStrucMapper.toDto(servPreDetteIntStruc);
    }

    /**
     * Get all the servPreDetteIntStrucs.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ServPreDetteIntStrucDTO> findAll() {
        log.debug("Request to get all ServPreDetteIntStrucs");
        return servPreDetteIntStrucRepository.findAll().stream()
            .map(servPreDetteIntStrucMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one servPreDetteIntStruc by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ServPreDetteIntStrucDTO> findOne(Long id) {
        log.debug("Request to get ServPreDetteIntStruc : {}", id);
        return servPreDetteIntStrucRepository.findById(id)
            .map(servPreDetteIntStrucMapper::toDto);
    }

    /**
     * Delete the servPreDetteIntStruc by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ServPreDetteIntStruc : {}", id);

        servPreDetteIntStrucRepository.deleteById(id);
    }

    //Methode Excel
    @Override
    public void save(MultipartFile file) {
            try {
                List<ServPreDetteIntStruc> servPreDetteIntStrucs = CsvSPDIS.readSend(file.getInputStream());
                servPreDetteIntStrucRepository.saveAll(servPreDetteIntStrucs);
            } catch (IOException e) {
                throw new RuntimeException("fail to store xlsx data: " + e.getMessage());
            }
    }

    @Override
    public ByteArrayInputStream load() {
        List<ServPreDetteIntStruc> solEngNonDecs = servPreDetteIntStrucRepository.findAll();
        ByteArrayInputStream in = CsvSPDIS.writeSend(solEngNonDecs);
        return in;
    }

    @Override
    public int somme() {
        return servPreDetteIntStrucRepository.sommeServPreDetteIntStruc();
    }

    @Override
    public String showDate() {
        return servPreDetteIntStrucRepository.showServPreDetteIntStruc().toString();
    }
}
