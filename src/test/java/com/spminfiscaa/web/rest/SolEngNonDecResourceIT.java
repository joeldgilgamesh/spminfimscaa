package com.spminfiscaa.web.rest;

import com.spminfiscaa.SpminfimscaaApp;
import com.spminfiscaa.domain.SolEngNonDec;
import com.spminfiscaa.repository.SolEngNonDecRepository;
import com.spminfiscaa.service.SolEngNonDecService;
import com.spminfiscaa.service.dto.SolEngNonDecDTO;
import com.spminfiscaa.service.mapper.SolEngNonDecMapper;

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
 * Integration tests for the {@link SolEngNonDecResource} REST controller.
 */
@SpringBootTest(classes = SpminfimscaaApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class SolEngNonDecResourceIT {

    private static final String DEFAULT_BAILLEUR = "AAAAAAAAAA";
    private static final String UPDATED_BAILLEUR = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE_COOPERATION = "AAAAAAAAAA";
    private static final String UPDATED_TYPE_COOPERATION = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE_FOND = "AAAAAAAAAA";
    private static final String UPDATED_TYPE_FOND = "BBBBBBBBBB";

    private static final Integer DEFAULT_COMMISSION = 1;
    private static final Integer UPDATED_COMMISSION = 2;

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_SOLDE = 1L;
    private static final Long UPDATED_SOLDE = 2L;

    @Autowired
    private SolEngNonDecRepository solEngNonDecRepository;

    @Autowired
    private SolEngNonDecMapper solEngNonDecMapper;

    @Autowired
    private SolEngNonDecService solEngNonDecService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSolEngNonDecMockMvc;

    private SolEngNonDec solEngNonDec;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SolEngNonDec createEntity(EntityManager em) {
        SolEngNonDec solEngNonDec = new SolEngNonDec()
            .bailleur(DEFAULT_BAILLEUR)
            .type_cooperation(DEFAULT_TYPE_COOPERATION)
            .type_fond(DEFAULT_TYPE_FOND)
            .commission(DEFAULT_COMMISSION)
            .date(DEFAULT_DATE)
            .solde(DEFAULT_SOLDE);
        return solEngNonDec;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SolEngNonDec createUpdatedEntity(EntityManager em) {
        SolEngNonDec solEngNonDec = new SolEngNonDec()
            .bailleur(UPDATED_BAILLEUR)
            .type_cooperation(UPDATED_TYPE_COOPERATION)
            .type_fond(UPDATED_TYPE_FOND)
            .commission(UPDATED_COMMISSION)
            .date(UPDATED_DATE)
            .solde(UPDATED_SOLDE);
        return solEngNonDec;
    }

    @BeforeEach
    public void initTest() {
        solEngNonDec = createEntity(em);
    }

    @Test
    @Transactional
    public void createSolEngNonDec() throws Exception {
        int databaseSizeBeforeCreate = solEngNonDecRepository.findAll().size();
        // Create the SolEngNonDec
        SolEngNonDecDTO solEngNonDecDTO = solEngNonDecMapper.toDto(solEngNonDec);
        restSolEngNonDecMockMvc.perform(post("/api/sol-eng-non-decs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(solEngNonDecDTO)))
            .andExpect(status().isCreated());

        // Validate the SolEngNonDec in the database
        List<SolEngNonDec> solEngNonDecList = solEngNonDecRepository.findAll();
        assertThat(solEngNonDecList).hasSize(databaseSizeBeforeCreate + 1);
        SolEngNonDec testSolEngNonDec = solEngNonDecList.get(solEngNonDecList.size() - 1);
        assertThat(testSolEngNonDec.getBailleur()).isEqualTo(DEFAULT_BAILLEUR);
        assertThat(testSolEngNonDec.getType_cooperation()).isEqualTo(DEFAULT_TYPE_COOPERATION);
        assertThat(testSolEngNonDec.getType_fond()).isEqualTo(DEFAULT_TYPE_FOND);
        assertThat(testSolEngNonDec.getCommission()).isEqualTo(DEFAULT_COMMISSION);
        assertThat(testSolEngNonDec.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testSolEngNonDec.getSolde()).isEqualTo(DEFAULT_SOLDE);
    }

    @Test
    @Transactional
    public void createSolEngNonDecWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = solEngNonDecRepository.findAll().size();

        // Create the SolEngNonDec with an existing ID
        solEngNonDec.setId(1L);
        SolEngNonDecDTO solEngNonDecDTO = solEngNonDecMapper.toDto(solEngNonDec);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSolEngNonDecMockMvc.perform(post("/api/sol-eng-non-decs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(solEngNonDecDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SolEngNonDec in the database
        List<SolEngNonDec> solEngNonDecList = solEngNonDecRepository.findAll();
        assertThat(solEngNonDecList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSolEngNonDecs() throws Exception {
        // Initialize the database
        solEngNonDecRepository.saveAndFlush(solEngNonDec);

        // Get all the solEngNonDecList
        restSolEngNonDecMockMvc.perform(get("/api/sol-eng-non-decs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(solEngNonDec.getId().intValue())))
            .andExpect(jsonPath("$.[*].bailleur").value(hasItem(DEFAULT_BAILLEUR)))
            .andExpect(jsonPath("$.[*].type_cooperation").value(hasItem(DEFAULT_TYPE_COOPERATION)))
            .andExpect(jsonPath("$.[*].type_fond").value(hasItem(DEFAULT_TYPE_FOND)))
            .andExpect(jsonPath("$.[*].commission").value(hasItem(DEFAULT_COMMISSION)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].solde").value(hasItem(DEFAULT_SOLDE.intValue())));
    }
    
    @Test
    @Transactional
    public void getSolEngNonDec() throws Exception {
        // Initialize the database
        solEngNonDecRepository.saveAndFlush(solEngNonDec);

        // Get the solEngNonDec
        restSolEngNonDecMockMvc.perform(get("/api/sol-eng-non-decs/{id}", solEngNonDec.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(solEngNonDec.getId().intValue()))
            .andExpect(jsonPath("$.bailleur").value(DEFAULT_BAILLEUR))
            .andExpect(jsonPath("$.type_cooperation").value(DEFAULT_TYPE_COOPERATION))
            .andExpect(jsonPath("$.type_fond").value(DEFAULT_TYPE_FOND))
            .andExpect(jsonPath("$.commission").value(DEFAULT_COMMISSION))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.solde").value(DEFAULT_SOLDE.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingSolEngNonDec() throws Exception {
        // Get the solEngNonDec
        restSolEngNonDecMockMvc.perform(get("/api/sol-eng-non-decs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSolEngNonDec() throws Exception {
        // Initialize the database
        solEngNonDecRepository.saveAndFlush(solEngNonDec);

        int databaseSizeBeforeUpdate = solEngNonDecRepository.findAll().size();

        // Update the solEngNonDec
        SolEngNonDec updatedSolEngNonDec = solEngNonDecRepository.findById(solEngNonDec.getId()).get();
        // Disconnect from session so that the updates on updatedSolEngNonDec are not directly saved in db
        em.detach(updatedSolEngNonDec);
        updatedSolEngNonDec
            .bailleur(UPDATED_BAILLEUR)
            .type_cooperation(UPDATED_TYPE_COOPERATION)
            .type_fond(UPDATED_TYPE_FOND)
            .commission(UPDATED_COMMISSION)
            .date(UPDATED_DATE)
            .solde(UPDATED_SOLDE);
        SolEngNonDecDTO solEngNonDecDTO = solEngNonDecMapper.toDto(updatedSolEngNonDec);

        restSolEngNonDecMockMvc.perform(put("/api/sol-eng-non-decs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(solEngNonDecDTO)))
            .andExpect(status().isOk());

        // Validate the SolEngNonDec in the database
        List<SolEngNonDec> solEngNonDecList = solEngNonDecRepository.findAll();
        assertThat(solEngNonDecList).hasSize(databaseSizeBeforeUpdate);
        SolEngNonDec testSolEngNonDec = solEngNonDecList.get(solEngNonDecList.size() - 1);
        assertThat(testSolEngNonDec.getBailleur()).isEqualTo(UPDATED_BAILLEUR);
        assertThat(testSolEngNonDec.getType_cooperation()).isEqualTo(UPDATED_TYPE_COOPERATION);
        assertThat(testSolEngNonDec.getType_fond()).isEqualTo(UPDATED_TYPE_FOND);
        assertThat(testSolEngNonDec.getCommission()).isEqualTo(UPDATED_COMMISSION);
        assertThat(testSolEngNonDec.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testSolEngNonDec.getSolde()).isEqualTo(UPDATED_SOLDE);
    }

    @Test
    @Transactional
    public void updateNonExistingSolEngNonDec() throws Exception {
        int databaseSizeBeforeUpdate = solEngNonDecRepository.findAll().size();

        // Create the SolEngNonDec
        SolEngNonDecDTO solEngNonDecDTO = solEngNonDecMapper.toDto(solEngNonDec);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSolEngNonDecMockMvc.perform(put("/api/sol-eng-non-decs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(solEngNonDecDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SolEngNonDec in the database
        List<SolEngNonDec> solEngNonDecList = solEngNonDecRepository.findAll();
        assertThat(solEngNonDecList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSolEngNonDec() throws Exception {
        // Initialize the database
        solEngNonDecRepository.saveAndFlush(solEngNonDec);

        int databaseSizeBeforeDelete = solEngNonDecRepository.findAll().size();

        // Delete the solEngNonDec
        restSolEngNonDecMockMvc.perform(delete("/api/sol-eng-non-decs/{id}", solEngNonDec.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SolEngNonDec> solEngNonDecList = solEngNonDecRepository.findAll();
        assertThat(solEngNonDecList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
