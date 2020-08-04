package com.spminfiscaa.web.rest;

import com.spminfiscaa.csvmanage.CsvSPDIS;
import com.spminfiscaa.message.ResponseMessage;
import com.spminfiscaa.service.ServPreDetteIntStrucService;
import com.spminfiscaa.web.rest.errors.BadRequestAlertException;
import com.spminfiscaa.service.dto.ServPreDetteIntStrucDTO;

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
 * REST controller for managing {@link com.spminfiscaa.domain.ServPreDetteIntStruc}.
 */
@RestController
@RequestMapping("/api")
public class ServPreDetteIntStrucResource {

    private final Logger log = LoggerFactory.getLogger(ServPreDetteIntStrucResource.class);

    private static final String ENTITY_NAME = "spminfimscaaServPreDetteIntStruc";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ServPreDetteIntStrucService servPreDetteIntStrucService;

    public ServPreDetteIntStrucResource(ServPreDetteIntStrucService servPreDetteIntStrucService) {
        this.servPreDetteIntStrucService = servPreDetteIntStrucService;
    }

    /**
     * {@code POST  /serv-pre-dette-int-strucs} : Create a new servPreDetteIntStruc.
     *
     * @param servPreDetteIntStrucDTO the servPreDetteIntStrucDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new servPreDetteIntStrucDTO, or with status {@code 400 (Bad Request)} if the servPreDetteIntStruc has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/serv-pre-dette-int-strucs")
    public ResponseEntity<ServPreDetteIntStrucDTO> createServPreDetteIntStruc(@RequestBody ServPreDetteIntStrucDTO servPreDetteIntStrucDTO) throws URISyntaxException {
        log.debug("REST request to save ServPreDetteIntStruc : {}", servPreDetteIntStrucDTO);
        if (servPreDetteIntStrucDTO.getId() != null) {
            throw new BadRequestAlertException("A new servPreDetteIntStruc cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ServPreDetteIntStrucDTO result = servPreDetteIntStrucService.save(servPreDetteIntStrucDTO);
        return ResponseEntity.created(new URI("/api/serv-pre-dette-int-strucs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /serv-pre-dette-int-strucs} : Updates an existing servPreDetteIntStruc.
     *
     * @param servPreDetteIntStrucDTO the servPreDetteIntStrucDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated servPreDetteIntStrucDTO,
     * or with status {@code 400 (Bad Request)} if the servPreDetteIntStrucDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the servPreDetteIntStrucDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/serv-pre-dette-int-strucs")
    public ResponseEntity<ServPreDetteIntStrucDTO> updateServPreDetteIntStruc(@RequestBody ServPreDetteIntStrucDTO servPreDetteIntStrucDTO) throws URISyntaxException {
        log.debug("REST request to update ServPreDetteIntStruc : {}", servPreDetteIntStrucDTO);
        if (servPreDetteIntStrucDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ServPreDetteIntStrucDTO result = servPreDetteIntStrucService.save(servPreDetteIntStrucDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, servPreDetteIntStrucDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /serv-pre-dette-int-strucs} : get all the servPreDetteIntStrucs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of servPreDetteIntStrucs in body.
     */
    @GetMapping("/serv-pre-dette-int-strucs")
    public List<ServPreDetteIntStrucDTO> getAllServPreDetteIntStrucs() {
        log.debug("REST request to get all ServPreDetteIntStrucs");
        return servPreDetteIntStrucService.findAll();
    }

    /**
     * {@code GET  /serv-pre-dette-int-strucs/:id} : get the "id" servPreDetteIntStruc.
     *
     * @param id the id of the servPreDetteIntStrucDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the servPreDetteIntStrucDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/serv-pre-dette-int-strucs/{id}")
    public ResponseEntity<ServPreDetteIntStrucDTO> getServPreDetteIntStruc(@PathVariable Long id) {
        log.debug("REST request to get ServPreDetteIntStruc : {}", id);
        Optional<ServPreDetteIntStrucDTO> servPreDetteIntStrucDTO = servPreDetteIntStrucService.findOne(id);
        return ResponseUtil.wrapOrNotFound(servPreDetteIntStrucDTO);
    }

    /**
     * {@code DELETE  /serv-pre-dette-int-strucs/:id} : delete the "id" servPreDetteIntStruc.
     *
     * @param id the id of the servPreDetteIntStrucDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/serv-pre-dette-int-strucs/{id}")
    public ResponseEntity<Void> deleteServPreDetteIntStruc(@PathVariable Long id) {
        log.debug("REST request to delete ServPreDetteIntStruc : {}", id);

        servPreDetteIntStrucService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
    //Methode execel
    @PostMapping("/SPDIS-upload")

    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam MultipartFile file) {
        String message = "";
        if (CsvSPDIS.hasExcelFormat(file)) {
            try {
                servPreDetteIntStrucService.save(file);
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

    @GetMapping("/SPDIS-download")
    public ResponseEntity<Resource> getFile() {
        String filename = "SPD-INT-Structuré.xlsx";
        InputStreamResource file = new InputStreamResource(servPreDetteIntStrucService.load());

        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
            .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
            .body(file);
    }
    @GetMapping("/SPDIS-total")
    public int getTotal() {
        log.debug("REST request to delete SolEngNonDec : {}" , servPreDetteIntStrucService.somme());
        return  servPreDetteIntStrucService.somme();
    }
}
