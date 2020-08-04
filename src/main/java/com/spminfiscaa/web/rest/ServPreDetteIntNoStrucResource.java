package com.spminfiscaa.web.rest;

import com.spminfiscaa.csvmanage.CsvSPDINS;
import com.spminfiscaa.message.ResponseMessage;
import com.spminfiscaa.service.ServPreDetteIntNoStrucService;
import com.spminfiscaa.web.rest.errors.BadRequestAlertException;
import com.spminfiscaa.service.dto.ServPreDetteIntNoStrucDTO;

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
 * REST controller for managing {@link com.spminfiscaa.domain.ServPreDetteIntNoStruc}.
 */
@RestController
@RequestMapping("/api")
public class ServPreDetteIntNoStrucResource {

    private final Logger log = LoggerFactory.getLogger(ServPreDetteIntNoStrucResource.class);

    private static final String ENTITY_NAME = "spminfimscaaServPreDetteIntNoStruc";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ServPreDetteIntNoStrucService servPreDetteIntNoStrucService;

    public ServPreDetteIntNoStrucResource(ServPreDetteIntNoStrucService servPreDetteIntNoStrucService) {
        this.servPreDetteIntNoStrucService = servPreDetteIntNoStrucService;
    }

    /**
     * {@code POST  /serv-pre-dette-int-no-strucs} : Create a new servPreDetteIntNoStruc.
     *
     * @param servPreDetteIntNoStrucDTO the servPreDetteIntNoStrucDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new servPreDetteIntNoStrucDTO, or with status {@code 400 (Bad Request)} if the servPreDetteIntNoStruc has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/serv-pre-dette-int-no-strucs")
    public ResponseEntity<ServPreDetteIntNoStrucDTO> createServPreDetteIntNoStruc(@RequestBody ServPreDetteIntNoStrucDTO servPreDetteIntNoStrucDTO) throws URISyntaxException {
        log.debug("REST request to save ServPreDetteIntNoStruc : {}", servPreDetteIntNoStrucDTO);
        if (servPreDetteIntNoStrucDTO.getId() != null) {
            throw new BadRequestAlertException("A new servPreDetteIntNoStruc cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ServPreDetteIntNoStrucDTO result = servPreDetteIntNoStrucService.save(servPreDetteIntNoStrucDTO);
        return ResponseEntity.created(new URI("/api/serv-pre-dette-int-no-strucs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /serv-pre-dette-int-no-strucs} : Updates an existing servPreDetteIntNoStruc.
     *
     * @param servPreDetteIntNoStrucDTO the servPreDetteIntNoStrucDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated servPreDetteIntNoStrucDTO,
     * or with status {@code 400 (Bad Request)} if the servPreDetteIntNoStrucDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the servPreDetteIntNoStrucDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/serv-pre-dette-int-no-strucs")
    public ResponseEntity<ServPreDetteIntNoStrucDTO> updateServPreDetteIntNoStruc(@RequestBody ServPreDetteIntNoStrucDTO servPreDetteIntNoStrucDTO) throws URISyntaxException {
        log.debug("REST request to update ServPreDetteIntNoStruc : {}", servPreDetteIntNoStrucDTO);
        if (servPreDetteIntNoStrucDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ServPreDetteIntNoStrucDTO result = servPreDetteIntNoStrucService.save(servPreDetteIntNoStrucDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, servPreDetteIntNoStrucDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /serv-pre-dette-int-no-strucs} : get all the servPreDetteIntNoStrucs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of servPreDetteIntNoStrucs in body.
     */
    @GetMapping("/serv-pre-dette-int-no-strucs")
    public List<ServPreDetteIntNoStrucDTO> getAllServPreDetteIntNoStrucs() {
        log.debug("REST request to get all ServPreDetteIntNoStrucs");
        return servPreDetteIntNoStrucService.findAll();
    }

    /**
     * {@code GET  /serv-pre-dette-int-no-strucs/:id} : get the "id" servPreDetteIntNoStruc.
     *
     * @param id the id of the servPreDetteIntNoStrucDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the servPreDetteIntNoStrucDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/serv-pre-dette-int-no-strucs/{id}")
    public ResponseEntity<ServPreDetteIntNoStrucDTO> getServPreDetteIntNoStruc(@PathVariable Long id) {
        log.debug("REST request to get ServPreDetteIntNoStruc : {}", id);
        Optional<ServPreDetteIntNoStrucDTO> servPreDetteIntNoStrucDTO = servPreDetteIntNoStrucService.findOne(id);
        return ResponseUtil.wrapOrNotFound(servPreDetteIntNoStrucDTO);
    }

    /**
     * {@code DELETE  /serv-pre-dette-int-no-strucs/:id} : delete the "id" servPreDetteIntNoStruc.
     *
     * @param id the id of the servPreDetteIntNoStrucDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/serv-pre-dette-int-no-strucs/{id}")
    public ResponseEntity<Void> deleteServPreDetteIntNoStruc(@PathVariable Long id) {
        log.debug("REST request to delete ServPreDetteIntNoStruc : {}", id);

        servPreDetteIntNoStrucService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
    //Methode execel
    @PostMapping("/SPDINS-upload")

    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam MultipartFile file) {
        String message = "";
        if (CsvSPDINS.hasExcelFormat(file)) {
            try {
                servPreDetteIntNoStrucService.save(file);
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

    @GetMapping("/SPDINS-download")
    public ResponseEntity<Resource> getFile() {
        String filename = "SPD-INT-Non-Structuré.xlsx";
        InputStreamResource file = new InputStreamResource(servPreDetteIntNoStrucService.load());

        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
            .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
            .body(file);
    }
    @GetMapping("/SPDINS-total")
    public int getTotal() {
        log.debug("REST request to delete SolEngNonDec : {}" , servPreDetteIntNoStrucService.somme());
        return  servPreDetteIntNoStrucService.somme();
    }
}
