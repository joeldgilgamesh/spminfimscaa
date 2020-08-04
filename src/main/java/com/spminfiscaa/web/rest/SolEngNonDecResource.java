package com.spminfiscaa.web.rest;

import com.spminfiscaa.csvmanage.CsvSEND;
import com.spminfiscaa.message.ResponseMessage;
import com.spminfiscaa.service.SolEngNonDecService;
import com.spminfiscaa.web.rest.errors.BadRequestAlertException;
import com.spminfiscaa.service.dto.SolEngNonDecDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.spminfiscaa.domain.SolEngNonDec}.
 */
@RestController
@RequestMapping("/api")
public class SolEngNonDecResource {

    private final Logger log = LoggerFactory.getLogger(SolEngNonDecResource.class);

    private static final String ENTITY_NAME = "spminfimscaaSolEngNonDec";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SolEngNonDecService solEngNonDecService;

    public SolEngNonDecResource(SolEngNonDecService solEngNonDecService) {
        this.solEngNonDecService = solEngNonDecService;
    }

    /**
     * {@code POST  /sol-eng-non-decs} : Create a new solEngNonDec.
     *
     * @param solEngNonDecDTO the solEngNonDecDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new solEngNonDecDTO, or with status {@code 400 (Bad Request)} if the solEngNonDec has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sol-eng-non-decs")
    public ResponseEntity<SolEngNonDecDTO> createSolEngNonDec(@RequestBody SolEngNonDecDTO solEngNonDecDTO) throws URISyntaxException {
        log.debug("REST request to save SolEngNonDec : {}", solEngNonDecDTO);
        if (solEngNonDecDTO.getId() != null) {
            throw new BadRequestAlertException("A new solEngNonDec cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SolEngNonDecDTO result = solEngNonDecService.save(solEngNonDecDTO);
        return ResponseEntity.created(new URI("/api/sol-eng-non-decs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sol-eng-non-decs} : Updates an existing solEngNonDec.
     *
     * @param solEngNonDecDTO the solEngNonDecDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated solEngNonDecDTO,
     * or with status {@code 400 (Bad Request)} if the solEngNonDecDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the solEngNonDecDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sol-eng-non-decs")
    public ResponseEntity<SolEngNonDecDTO> updateSolEngNonDec(@RequestBody SolEngNonDecDTO solEngNonDecDTO) throws URISyntaxException {
        log.debug("REST request to update SolEngNonDec : {}", solEngNonDecDTO);
        if (solEngNonDecDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SolEngNonDecDTO result = solEngNonDecService.save(solEngNonDecDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, solEngNonDecDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /sol-eng-non-decs} : get all the solEngNonDecs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of solEngNonDecs in body.
     */
    @GetMapping("/sol-eng-non-decs")
    public List<SolEngNonDecDTO> getAllSolEngNonDecs() {
        log.debug("REST request to get all SolEngNonDecs");
        return solEngNonDecService.findAll();
    }

    /**
     * {@code GET  /sol-eng-non-decs/:id} : get the "id" solEngNonDec.
     *
     * @param id the id of the solEngNonDecDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the solEngNonDecDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sol-eng-non-decs/{id}")
    public ResponseEntity<SolEngNonDecDTO> getSolEngNonDec(@PathVariable Long id) {
        log.debug("REST request to get SolEngNonDec : {}", id);
        Optional<SolEngNonDecDTO> solEngNonDecDTO = solEngNonDecService.findOne(id);
        return ResponseUtil.wrapOrNotFound(solEngNonDecDTO);
    }

    /**
     * {@code DELETE  /sol-eng-non-decs/:id} : delete the "id" solEngNonDec.
     *
     * @param id the id of the solEngNonDecDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sol-eng-non-decs/{id}")
    public ResponseEntity<Void> deleteSolEngNonDec(@PathVariable Long id) {
        log.debug("REST request to delete SolEngNonDec : {}", id);

        solEngNonDecService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    //Methode execel
    //@PostMapping(value = "/upload", consumes="multipart/mixed" )
    @PostMapping("/SEND-upload")

    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam MultipartFile file) {
        String message = "";
        if (CsvSEND.hasExcelFormat(file)) {
            try {
                solEngNonDecService.save(file);
                message = "Uploaded the file successfully: " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
            } catch (Exception e) {
                message = "Could not upload the file: " +  file.getOriginalFilename() + "!";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
            }
        }
        message = "Please upload an excel file!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
    }

    @GetMapping("/SEND-download")
    public ResponseEntity<Resource> getFile() {
        String filename = "Solde_Engage_Non_Declarer.xlsx";
        InputStreamResource file = new InputStreamResource(solEngNonDecService.load());

        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
            .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
            .body(file);
    }

    @GetMapping("/SEND-total")
    public int getTotal() {
        log.debug("REST request to delete SolEngNonDec : {}" , solEngNonDecService.somme());
      return  solEngNonDecService.somme();
    }
}
