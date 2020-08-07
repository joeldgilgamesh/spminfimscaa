package com.spminfiscaa.web.rest;

import com.spminfiscaa.SpminfimscaaApp;
import com.spminfiscaa.domain.ServPreDetteExt;
import com.spminfiscaa.repository.ServPreDetteExtRepository;
import com.spminfiscaa.service.ServPreDetteExtService;
import com.spminfiscaa.service.dto.ServPreDetteExtDTO;
import com.spminfiscaa.service.mapper.ServPreDetteExtMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ServPreDetteExtResource} REST controller.
 */
@SpringBootTest(classes = SpminfimscaaApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ServPreDetteExtResourceIT {

    private static final String DEFAULT_BAILLEUR = "AAAAAAAAAA";
    private static final String UPDATED_BAILLEUR = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE_COOPERATION = "AAAAAAAAAA";
    private static final String UPDATED_TYPE_COOPERATION = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE_FOND = "AAAAAAAAAA";
    private static final String UPDATED_TYPE_FOND = "BBBBBBBBBB";

    private static final Integer DEFAULT_MONTANT_A_REMBOURSER = 1;
    private static final Integer UPDATED_MONTANT_A_REMBOURSER = 2;

    private static final Integer DEFAULT_INTERET = 1;
    private static final Integer UPDATED_INTERET = 2;

    private static final LocalDate DEFAULT_ECHEANCE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ECHEANCE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_TOTAL = 1L;
    private static final Long UPDATED_TOTAL = 2L;

    @Autowired
    private ServPreDetteExtRepository servPreDetteExtRepository;

    @Autowired
    private ServPreDetteExtMapper servPreDetteExtMapper;

    @Autowired
    private ServPreDetteExtService servPreDetteExtService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restServPreDetteExtMockMvc;

    private ServPreDetteExt servPreDetteExt;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServPreDetteExt createEntity(EntityManager em) {
        ServPreDetteExt servPreDetteExt = new ServPreDetteExt()
            .bailleur(DEFAULT_BAILLEUR)
            .type_cooperation(DEFAULT_TYPE_COOPERATION)
            .type_fond(DEFAULT_TYPE_FOND)
            .montant_a_rembourser(DEFAULT_MONTANT_A_REMBOURSER)
            .interet(DEFAULT_INTERET)
            .echeance(DEFAULT_ECHEANCE)
            .date(DEFAULT_DATE)
            .total(DEFAULT_TOTAL);
        return servPreDetteExt;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServPreDetteExt createUpdatedEntity(EntityManager em) {
        ServPreDetteExt servPreDetteExt = new ServPreDetteExt()
            .bailleur(UPDATED_BAILLEUR)
            .type_cooperation(UPDATED_TYPE_COOPERATION)
            .type_fond(UPDATED_TYPE_FOND)
            .montant_a_rembourser(UPDATED_MONTANT_A_REMBOURSER)
            .interet(UPDATED_INTERET)
            .echeance(UPDATED_ECHEANCE)
            .date(UPDATED_DATE)
            .total(UPDATED_TOTAL);
        return servPreDetteExt;
    }

    @BeforeEach
    public void initTest() {
        servPreDetteExt = createEntity(em);
    }

    @Test
    @Transactional
    public void createServPreDetteExt() throws Exception {
        int databaseSizeBeforeCreate = servPreDetteExtRepository.findAll().size();
        // Create the ServPreDetteExt
        ServPreDetteExtDTO servPreDetteExtDTO = servPreDetteExtMapper.toDto(servPreDetteExt);
        restServPreDetteExtMockMvc.perform(post("/api/serv-pre-dette-exts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(servPreDetteExtDTO)))
            .andExpect(status().isCreated());

        // Validate the ServPreDetteExt in the database
        List<ServPreDetteExt> servPreDetteExtList = servPreDetteExtRepository.findAll();
        assertThat(servPreDetteExtList).hasSize(databaseSizeBeforeCreate + 1);
        ServPreDetteExt testServPreDetteExt = servPreDetteExtList.get(servPreDetteExtList.size() - 1);
        assertThat(testServPreDetteExt.getBailleur()).isEqualTo(DEFAULT_BAILLEUR);
        assertThat(testServPreDetteExt.getType_cooperation()).isEqualTo(DEFAULT_TYPE_COOPERATION);
        assertThat(testServPreDetteExt.getType_fond()).isEqualTo(DEFAULT_TYPE_FOND);
        assertThat(testServPreDetteExt.getMontant_a_rembourser()).isEqualTo(DEFAULT_MONTANT_A_REMBOURSER);
        assertThat(testServPreDetteExt.getInteret()).isEqualTo(DEFAULT_INTERET);
        assertThat(testServPreDetteExt.getEcheance()).isEqualTo(DEFAULT_ECHEANCE);
        assertThat(testServPreDetteExt.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testServPreDetteExt.getTotal()).isEqualTo(DEFAULT_TOTAL);
    }

    @Test
    @Transactional
    public void createServPreDetteExtWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = servPreDetteExtRepository.findAll().size();

        // Create the ServPreDetteExt with an existing ID
        servPreDetteExt.setId(1L);
        ServPreDetteExtDTO servPreDetteExtDTO = servPreDetteExtMapper.toDto(servPreDetteExt);

        // An entity with an existing ID cannot be created, so this API call must fail
        restServPreDetteExtMockMvc.perform(post("/api/serv-pre-dette-exts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(servPreDetteExtDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ServPreDetteExt in the database
        List<ServPreDetteExt> servPreDetteExtList = servPreDetteExtRepository.findAll();
        assertThat(servPreDetteExtList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllServPreDetteExts() throws Exception {
        // Initialize the database
        servPreDetteExtRepository.saveAndFlush(servPreDetteExt);

        // Get all the servPreDetteExtList
        restServPreDetteExtMockMvc.perform(get("/api/serv-pre-dette-exts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(servPreDetteExt.getId().intValue())))
            .andExpect(jsonPath("$.[*].bailleur").value(hasItem(DEFAULT_BAILLEUR)))
            .andExpect(jsonPath("$.[*].type_cooperation").value(hasItem(DEFAULT_TYPE_COOPERATION)))
            .andExpect(jsonPath("$.[*].type_fond").value(hasItem(DEFAULT_TYPE_FOND)))
            .andExpect(jsonPath("$.[*].montant_a_rembourser").value(hasItem(DEFAULT_MONTANT_A_REMBOURSER)))
            .andExpect(jsonPath("$.[*].interet").value(hasItem(DEFAULT_INTERET)))
            .andExpect(jsonPath("$.[*].echeance").value(hasItem(DEFAULT_ECHEANCE.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].total").value(hasItem(DEFAULT_TOTAL.intValue())));
    }
    
    @Test
    @Transactional
    public void getServPreDetteExt() throws Exception {
        // Initialize the database
        servPreDetteExtRepository.saveAndFlush(servPreDetteExt);

        // Get the servPreDetteExt
        restServPreDetteExtMockMvc.perform(get("/api/serv-pre-dette-exts/{id}", servPreDetteExt.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(servPreDetteExt.getId().intValue()))
            .andExpect(jsonPath("$.bailleur").value(DEFAULT_BAILLEUR))
            .andExpect(jsonPath("$.type_cooperation").value(DEFAULT_TYPE_COOPERATION))
            .andExpect(jsonPath("$.type_fond").value(DEFAULT_TYPE_FOND))
            .andExpect(jsonPath("$.montant_a_rembourser").value(DEFAULT_MONTANT_A_REMBOURSER))
            .andExpect(jsonPath("$.interet").value(DEFAULT_INTERET))
            .andExpect(jsonPath("$.echeance").value(DEFAULT_ECHEANCE.toString()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.total").value(DEFAULT_TOTAL.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingServPreDetteExt() throws Exception {
        // Get the servPreDetteExt
        restServPreDetteExtMockMvc.perform(get("/api/serv-pre-dette-exts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateServPreDetteExt() throws Exception {
        // Initialize the database
        servPreDetteExtRepository.saveAndFlush(servPreDetteExt);

        int databaseSizeBeforeUpdate = servPreDetteExtRepository.findAll().size();

        // Update the servPreDetteExt
        ServPreDetteExt updatedServPreDetteExt = servPreDetteExtRepository.findById(servPreDetteExt.getId()).get();
        // Disconnect from session so that the updates on updatedServPreDetteExt are not directly saved in db
        em.detach(updatedServPreDetteExt);
        updatedServPreDetteExt
            .bailleur(UPDATED_BAILLEUR)
            .type_cooperation(UPDATED_TYPE_COOPERATION)
            .type_fond(UPDATED_TYPE_FOND)
            .montant_a_rembourser(UPDATED_MONTANT_A_REMBOURSER)
            .interet(UPDATED_INTERET)
            .echeance(UPDATED_ECHEANCE)
            .date(UPDATED_DATE)
            .total(UPDATED_TOTAL);
        ServPreDetteExtDTO servPreDetteExtDTO = servPreDetteExtMapper.toDto(updatedServPreDetteExt);

        restServPreDetteExtMockMvc.perform(put("/api/serv-pre-dette-exts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(servPreDetteExtDTO)))
            .andExpect(status().isOk());

        // Validate the ServPreDetteExt in the database
        List<ServPreDetteExt> servPreDetteExtList = servPreDetteExtRepository.findAll();
        assertThat(servPreDetteExtList).hasSize(databaseSizeBeforeUpdate);
        ServPreDetteExt testServPreDetteExt = servPreDetteExtList.get(servPreDetteExtList.size() - 1);
        assertThat(testServPreDetteExt.getBailleur()).isEqualTo(UPDATED_BAILLEUR);
        assertThat(testServPreDetteExt.getType_cooperation()).isEqualTo(UPDATED_TYPE_COOPERATION);
        assertThat(testServPreDetteExt.getType_fond()).isEqualTo(UPDATED_TYPE_FOND);
        assertThat(testServPreDetteExt.getMontant_a_rembourser()).isEqualTo(UPDATED_MONTANT_A_REMBOURSER);
        assertThat(testServPreDetteExt.getInteret()).isEqualTo(UPDATED_INTERET);
        assertThat(testServPreDetteExt.getEcheance()).isEqualTo(UPDATED_ECHEANCE);
        assertThat(testServPreDetteExt.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testServPreDetteExt.getTotal()).isEqualTo(UPDATED_TOTAL);
    }

    @Test
    @Transactional
    public void updateNonExistingServPreDetteExt() throws Exception {
        int databaseSizeBeforeUpdate = servPreDetteExtRepository.findAll().size();

        // Create the ServPreDetteExt
        ServPreDetteExtDTO servPreDetteExtDTO = servPreDetteExtMapper.toDto(servPreDetteExt);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServPreDetteExtMockMvc.perform(put("/api/serv-pre-dette-exts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(servPreDetteExtDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ServPreDetteExt in the database
        List<ServPreDetteExt> servPreDetteExtList = servPreDetteExtRepository.findAll();
        assertThat(servPreDetteExtList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteServPreDetteExt() throws Exception {
        // Initialize the database
        servPreDetteExtRepository.saveAndFlush(servPreDetteExt);

        int databaseSizeBeforeDelete = servPreDetteExtRepository.findAll().size();

        // Delete the servPreDetteExt
        restServPreDetteExtMockMvc.perform(delete("/api/serv-pre-dette-exts/{id}", servPreDetteExt.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ServPreDetteExt> servPreDetteExtList = servPreDetteExtRepository.findAll();
        assertThat(servPreDetteExtList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
