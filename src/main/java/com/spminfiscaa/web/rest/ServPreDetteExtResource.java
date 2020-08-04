package com.spminfiscaa.web.rest;

import com.spminfiscaa.csvmanage.CsvSPDE;
import com.spminfiscaa.message.ResponseMessage;
import com.spminfiscaa.service.ServPreDetteExtService;
import com.spminfiscaa.web.rest.errors.BadRequestAlertException;
import com.spminfiscaa.service.dto.ServPreDetteExtDTO;

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
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * REST controller for managing {@link com.spminfiscaa.domain.ServPreDetteExt}.
 */
@RestController
@RequestMapping("/api")
public class ServPreDetteExtResource {

    private final Logger log = LoggerFactory.getLogger(ServPreDetteExtResource.class);

    private static final String ENTITY_NAME = "spminfimscaaServPreDetteExt";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ServPreDetteExtService servPreDetteExtService;

    public ServPreDetteExtResource(ServPreDetteExtService servPreDetteExtService) {
        this.servPreDetteExtService = servPreDetteExtService;
    }

    /**
     * {@code POST  /serv-pre-dette-exts} : Create a new servPreDetteExt.
     *
     * @param servPreDetteExtDTO the servPreDetteExtDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new servPreDetteExtDTO, or with status {@code 400 (Bad Request)} if the servPreDetteExt has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/serv-pre-dette-exts")
    public ResponseEntity<ServPreDetteExtDTO> createServPreDetteExt(@RequestBody ServPreDetteExtDTO servPreDetteExtDTO) throws URISyntaxException {
        log.debug("REST request to save ServPreDetteExt : {}", servPreDetteExtDTO);
        if (servPreDetteExtDTO.getId() != null) {
            throw new BadRequestAlertException("A new servPreDetteExt cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ServPreDetteExtDTO result = servPreDetteExtService.save(servPreDetteExtDTO);
        return ResponseEntity.created(new URI("/api/serv-pre-dette-exts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /serv-pre-dette-exts} : Updates an existing servPreDetteExt.
     *
     * @param servPreDetteExtDTO the servPreDetteExtDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated servPreDetteExtDTO,
     * or with status {@code 400 (Bad Request)} if the servPreDetteExtDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the servPreDetteExtDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/serv-pre-dette-exts")
    public ResponseEntity<ServPreDetteExtDTO> updateServPreDetteExt(@RequestBody ServPreDetteExtDTO servPreDetteExtDTO) throws URISyntaxException {
        log.debug("REST request to update ServPreDetteExt : {}", servPreDetteExtDTO);
        if (servPreDetteExtDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ServPreDetteExtDTO result = servPreDetteExtService.save(servPreDetteExtDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, servPreDetteExtDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /serv-pre-dette-exts} : get all the servPreDetteExts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of servPreDetteExts in body.
     */
    @GetMapping("/serv-pre-dette-exts")
    public List<ServPreDetteExtDTO> getAllServPreDetteExts() {
        log.debug("REST request to get all ServPreDetteExts");
        return servPreDetteExtService.findAll();
    }

    /**
     * {@code GET  /serv-pre-dette-exts/:id} : get the "id" servPreDetteExt.
     *
     * @param id the id of the servPreDetteExtDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the servPreDetteExtDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/serv-pre-dette-exts/{id}")
    public ResponseEntity<ServPreDetteExtDTO> getServPreDetteExt(@PathVariable Long id) {
        log.debug("REST request to get ServPreDetteExt : {}", id);
        Optional<ServPreDetteExtDTO> servPreDetteExtDTO = servPreDetteExtService.findOne(id);
        return ResponseUtil.wrapOrNotFound(servPreDetteExtDTO);
    }

    /**
     * {@code DELETE  /serv-pre-dette-exts/:id} : delete the "id" servPreDetteExt.
     *
     * @param id the id of the servPreDetteExtDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/serv-pre-dette-exts/{id}")
    public ResponseEntity<Void> deleteServPreDetteExt(@PathVariable Long id) {
        log.debug("REST request to delete ServPreDetteExt : {}", id);

        servPreDetteExtService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
    //Methode execel
    @PostMapping("/SPDE-upload")

    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam MultipartFile file) {
        String message = "";
        if (CsvSPDE.hasExcelFormat(file)) {
            try {
                servPreDetteExtService.save(file);
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

    //En Maintenace
    @GetMapping("/SPDE-download")
    public ResponseEntity<Resource> getFile() {
        String filename = "SoldePrevisionnelDetteExterieur.xlsx";
        InputStreamResource file = new InputStreamResource(servPreDetteExtService.load());

        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
            .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
            .body(file);
    }
    @GetMapping("/SPDE-total")
    public int getTotal() {
        log.debug("REST request to delete SolEngNonDec : {}" , servPreDetteExtService.somme());
        return  servPreDetteExtService.somme();
    }

    @GetMapping("SPDE-pediod")
    public Map<String,Object> getInterval(@RequestParam Date dateStart, @RequestParam Date dateEnd){
        log.debug("REST request to delete SolEngNonDec : {}" , servPreDetteExtService.showTri(dateStart, dateEnd));
        return servPreDetteExtService.showTri(dateStart, dateEnd);
    }
}
