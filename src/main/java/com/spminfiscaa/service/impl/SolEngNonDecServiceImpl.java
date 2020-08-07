package com.spminfiscaa.service.impl;

import com.spminfiscaa.csvmanage.CsvSEND;
import com.spminfiscaa.service.SolEngNonDecService;
import com.spminfiscaa.domain.SolEngNonDec;
import com.spminfiscaa.repository.SolEngNonDecRepository;
import com.spminfiscaa.service.dto.SolEngNonDecDTO;
import com.spminfiscaa.service.mapper.SolEngNonDecMapper;
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
 * Service Implementation for managing {@link SolEngNonDec}.
 */
@Service
@Transactional
public class SolEngNonDecServiceImpl implements SolEngNonDecService {

    private final Logger log = LoggerFactory.getLogger(SolEngNonDecServiceImpl.class);

    private final SolEngNonDecRepository solEngNonDecRepository;

    private SolEngNonDecDTO solEngNonDecDTO;

    private final SolEngNonDecMapper solEngNonDecMapper;

    public SolEngNonDecServiceImpl(SolEngNonDecRepository solEngNonDecRepository, SolEngNonDecMapper solEngNonDecMapper) {
        this.solEngNonDecRepository = solEngNonDecRepository;
        this.solEngNonDecMapper = solEngNonDecMapper;
    }

    /**
     * Save a solEngNonDec.
     *
     * @param solEngNonDecDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public SolEngNonDecDTO save(SolEngNonDecDTO solEngNonDecDTO) {
        log.debug("Request to save SolEngNonDec : {}", solEngNonDecDTO);
        SolEngNonDec solEngNonDec = solEngNonDecMapper.toEntity(solEngNonDecDTO);
        solEngNonDec = solEngNonDecRepository.save(solEngNonDec);
        return solEngNonDecMapper.toDto(solEngNonDec);
    }

    /**
     * Get all the solEngNonDecs.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<SolEngNonDecDTO> findAll() {
        log.debug("Request to get all SolEngNonDecs");
        return solEngNonDecRepository.findAll().stream()
            .map(solEngNonDecMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one solEngNonDec by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SolEngNonDecDTO> findOne(Long id) {
        log.debug("Request to get SolEngNonDec : {}", id);
        return solEngNonDecRepository.findById(id)
            .map(solEngNonDecMapper::toDto);
    }

    /**
     * Delete the solEngNonDec by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SolEngNonDec : {}", id);

        solEngNonDecRepository.deleteById(id);
    }

    //Methode Excel
    @Override
    public void save(MultipartFile file) {
        try {
            List<SolEngNonDec> solEngNonDecs = CsvSEND.readSend(file.getInputStream());
            solEngNonDecRepository.saveAll(solEngNonDecs);
        } catch (IOException e) {
            throw new RuntimeException("fail to store xlsx data: " + e.getMessage());
        }
    }

    @Override
    public ByteArrayInputStream load() {

            List<SolEngNonDec> solEngNonDecs = solEngNonDecRepository.findAll();
            System.out.println(solEngNonDecs.size());
            ByteArrayInputStream in = CsvSEND.writeSend(solEngNonDecs);
            return in;
    }

    @Override
    public Long somme() {
        return solEngNonDecRepository.sommeSolEngNonDec();
    }

    @Override
    public String showDate() {
        return solEngNonDecRepository.showSolEngNonDec().toString();
    }
}
