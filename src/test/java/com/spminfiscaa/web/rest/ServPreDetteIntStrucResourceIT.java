package com.spminfiscaa.web.rest;

import com.spminfiscaa.SpminfimscaaApp;
import com.spminfiscaa.domain.ServPreDetteIntStruc;
import com.spminfiscaa.repository.ServPreDetteIntStrucRepository;
import com.spminfiscaa.service.ServPreDetteIntStrucService;
import com.spminfiscaa.service.dto.ServPreDetteIntStrucDTO;
import com.spminfiscaa.service.mapper.ServPreDetteIntStrucMapper;

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
 * Integration tests for the {@link ServPreDetteIntStrucResource} REST controller.
 */
@SpringBootTest(classes = SpminfimscaaApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ServPreDetteIntStrucResourceIT {

    private static final String DEFAULT_ORGANISME = "AAAAAAAAAA";
    private static final String UPDATED_ORGANISME = "BBBBBBBBBB";

    private static final String DEFAULT_CATEGORIE = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORIE = "BBBBBBBBBB";

    private static final String DEFAULT_GROUPE = "AAAAAAAAAA";
    private static final String UPDATED_GROUPE = "BBBBBBBBBB";

    private static final Integer DEFAULT_PRINCIPALE = 1;
    private static final Integer UPDATED_PRINCIPALE = 2;

    private static final Integer DEFAULT_INTERET = 1;
    private static final Integer UPDATED_INTERET = 2;

    private static final LocalDate DEFAULT_ECHEANCE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ECHEANCE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_TOTAL = 1L;
    private static final Long UPDATED_TOTAL = 2L;

    @Autowired
    private ServPreDetteIntStrucRepository servPreDetteIntStrucRepository;

    @Autowired
    private ServPreDetteIntStrucMapper servPreDetteIntStrucMapper;

    @Autowired
    private ServPreDetteIntStrucService servPreDetteIntStrucService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restServPreDetteIntStrucMockMvc;

    private ServPreDetteIntStruc servPreDetteIntStruc;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServPreDetteIntStruc createEntity(EntityManager em) {
        ServPreDetteIntStruc servPreDetteIntStruc = new ServPreDetteIntStruc()
            .organisme(DEFAULT_ORGANISME)
            .categorie(DEFAULT_CATEGORIE)
            .groupe(DEFAULT_GROUPE)
            .principale(DEFAULT_PRINCIPALE)
            .interet(DEFAULT_INTERET)
            .echeance(DEFAULT_ECHEANCE)
            .date(DEFAULT_DATE)
            .total(DEFAULT_TOTAL);
        return servPreDetteIntStruc;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServPreDetteIntStruc createUpdatedEntity(EntityManager em) {
        ServPreDetteIntStruc servPreDetteIntStruc = new ServPreDetteIntStruc()
            .organisme(UPDATED_ORGANISME)
            .categorie(UPDATED_CATEGORIE)
            .groupe(UPDATED_GROUPE)
            .principale(UPDATED_PRINCIPALE)
            .interet(UPDATED_INTERET)
            .echeance(UPDATED_ECHEANCE)
            .date(UPDATED_DATE)
            .total(UPDATED_TOTAL);
        return servPreDetteIntStruc;
    }

    @BeforeEach
    public void initTest() {
        servPreDetteIntStruc = createEntity(em);
    }

    @Test
    @Transactional
    public void createServPreDetteIntStruc() throws Exception {
        int databaseSizeBeforeCreate = servPreDetteIntStrucRepository.findAll().size();
        // Create the ServPreDetteIntStruc
        ServPreDetteIntStrucDTO servPreDetteIntStrucDTO = servPreDetteIntStrucMapper.toDto(servPreDetteIntStruc);
        restServPreDetteIntStrucMockMvc.perform(post("/api/serv-pre-dette-int-strucs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(servPreDetteIntStrucDTO)))
            .andExpect(status().isCreated());

        // Validate the ServPreDetteIntStruc in the database
        List<ServPreDetteIntStruc> servPreDetteIntStrucList = servPreDetteIntStrucRepository.findAll();
        assertThat(servPreDetteIntStrucList).hasSize(databaseSizeBeforeCreate + 1);
        ServPreDetteIntStruc testServPreDetteIntStruc = servPreDetteIntStrucList.get(servPreDetteIntStrucList.size() - 1);
        assertThat(testServPreDetteIntStruc.getOrganisme()).isEqualTo(DEFAULT_ORGANISME);
        assertThat(testServPreDetteIntStruc.getCategorie()).isEqualTo(DEFAULT_CATEGORIE);
        assertThat(testServPreDetteIntStruc.getGroupe()).isEqualTo(DEFAULT_GROUPE);
        assertThat(testServPreDetteIntStruc.getPrincipale()).isEqualTo(DEFAULT_PRINCIPALE);
        assertThat(testServPreDetteIntStruc.getInteret()).isEqualTo(DEFAULT_INTERET);
        assertThat(testServPreDetteIntStruc.getEcheance()).isEqualTo(DEFAULT_ECHEANCE);
        assertThat(testServPreDetteIntStruc.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testServPreDetteIntStruc.getTotal()).isEqualTo(DEFAULT_TOTAL);
    }

    @Test
    @Transactional
    public void createServPreDetteIntStrucWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = servPreDetteIntStrucRepository.findAll().size();

        // Create the ServPreDetteIntStruc with an existing ID
        servPreDetteIntStruc.setId(1L);
        ServPreDetteIntStrucDTO servPreDetteIntStrucDTO = servPreDetteIntStrucMapper.toDto(servPreDetteIntStruc);

        // An entity with an existing ID cannot be created, so this API call must fail
        restServPreDetteIntStrucMockMvc.perform(post("/api/serv-pre-dette-int-strucs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(servPreDetteIntStrucDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ServPreDetteIntStruc in the database
        List<ServPreDetteIntStruc> servPreDetteIntStrucList = servPreDetteIntStrucRepository.findAll();
        assertThat(servPreDetteIntStrucList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllServPreDetteIntStrucs() throws Exception {
        // Initialize the database
        servPreDetteIntStrucRepository.saveAndFlush(servPreDetteIntStruc);

        // Get all the servPreDetteIntStrucList
        restServPreDetteIntStrucMockMvc.perform(get("/api/serv-pre-dette-int-strucs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(servPreDetteIntStruc.getId().intValue())))
            .andExpect(jsonPath("$.[*].organisme").value(hasItem(DEFAULT_ORGANISME)))
            .andExpect(jsonPath("$.[*].categorie").value(hasItem(DEFAULT_CATEGORIE)))
            .andExpect(jsonPath("$.[*].groupe").value(hasItem(DEFAULT_GROUPE)))
            .andExpect(jsonPath("$.[*].principale").value(hasItem(DEFAULT_PRINCIPALE)))
            .andExpect(jsonPath("$.[*].interet").value(hasItem(DEFAULT_INTERET)))
            .andExpect(jsonPath("$.[*].echeance").value(hasItem(DEFAULT_ECHEANCE.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].total").value(hasItem(DEFAULT_TOTAL.intValue())));
    }
    
    @Test
    @Transactional
    public void getServPreDetteIntStruc() throws Exception {
        // Initialize the database
        servPreDetteIntStrucRepository.saveAndFlush(servPreDetteIntStruc);

        // Get the servPreDetteIntStruc
        restServPreDetteIntStrucMockMvc.perform(get("/api/serv-pre-dette-int-strucs/{id}", servPreDetteIntStruc.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(servPreDetteIntStruc.getId().intValue()))
            .andExpect(jsonPath("$.organisme").value(DEFAULT_ORGANISME))
            .andExpect(jsonPath("$.categorie").value(DEFAULT_CATEGORIE))
            .andExpect(jsonPath("$.groupe").value(DEFAULT_GROUPE))
            .andExpect(jsonPath("$.principale").value(DEFAULT_PRINCIPALE))
            .andExpect(jsonPath("$.interet").value(DEFAULT_INTERET))
            .andExpect(jsonPath("$.echeance").value(DEFAULT_ECHEANCE.toString()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.total").value(DEFAULT_TOTAL.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingServPreDetteIntStruc() throws Exception {
        // Get the servPreDetteIntStruc
        restServPreDetteIntStrucMockMvc.perform(get("/api/serv-pre-dette-int-strucs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateServPreDetteIntStruc() throws Exception {
        // Initialize the database
        servPreDetteIntStrucRepository.saveAndFlush(servPreDetteIntStruc);

        int databaseSizeBeforeUpdate = servPreDetteIntStrucRepository.findAll().size();

        // Update the servPreDetteIntStruc
        ServPreDetteIntStruc updatedServPreDetteIntStruc = servPreDetteIntStrucRepository.findById(servPreDetteIntStruc.getId()).get();
        // Disconnect from session so that the updates on updatedServPreDetteIntStruc are not directly saved in db
        em.detach(updatedServPreDetteIntStruc);
        updatedServPreDetteIntStruc
            .organisme(UPDATED_ORGANISME)
            .categorie(UPDATED_CATEGORIE)
            .groupe(UPDATED_GROUPE)
            .principale(UPDATED_PRINCIPALE)
            .interet(UPDATED_INTERET)
            .echeance(UPDATED_ECHEANCE)
            .date(UPDATED_DATE)
            .total(UPDATED_TOTAL);
        ServPreDetteIntStrucDTO servPreDetteIntStrucDTO = servPreDetteIntStrucMapper.toDto(updatedServPreDetteIntStruc);

        restServPreDetteIntStrucMockMvc.perform(put("/api/serv-pre-dette-int-strucs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(servPreDetteIntStrucDTO)))
            .andExpect(status().isOk());

        // Validate the ServPreDetteIntStruc in the database
        List<ServPreDetteIntStruc> servPreDetteIntStrucList = servPreDetteIntStrucRepository.findAll();
        assertThat(servPreDetteIntStrucList).hasSize(databaseSizeBeforeUpdate);
        ServPreDetteIntStruc testServPreDetteIntStruc = servPreDetteIntStrucList.get(servPreDetteIntStrucList.size() - 1);
        assertThat(testServPreDetteIntStruc.getOrganisme()).isEqualTo(UPDATED_ORGANISME);
        assertThat(testServPreDetteIntStruc.getCategorie()).isEqualTo(UPDATED_CATEGORIE);
        assertThat(testServPreDetteIntStruc.getGroupe()).isEqualTo(UPDATED_GROUPE);
        assertThat(testServPreDetteIntStruc.getPrincipale()).isEqualTo(UPDATED_PRINCIPALE);
        assertThat(testServPreDetteIntStruc.getInteret()).isEqualTo(UPDATED_INTERET);
        assertThat(testServPreDetteIntStruc.getEcheance()).isEqualTo(UPDATED_ECHEANCE);
        assertThat(testServPreDetteIntStruc.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testServPreDetteIntStruc.getTotal()).isEqualTo(UPDATED_TOTAL);
    }

    @Test
    @Transactional
    public void updateNonExistingServPreDetteIntStruc() throws Exception {
        int databaseSizeBeforeUpdate = servPreDetteIntStrucRepository.findAll().size();

        // Create the ServPreDetteIntStruc
        ServPreDetteIntStrucDTO servPreDetteIntStrucDTO = servPreDetteIntStrucMapper.toDto(servPreDetteIntStruc);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServPreDetteIntStrucMockMvc.perform(put("/api/serv-pre-dette-int-strucs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(servPreDetteIntStrucDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ServPreDetteIntStruc in the database
        List<ServPreDetteIntStruc> servPreDetteIntStrucList = servPreDetteIntStrucRepository.findAll();
        assertThat(servPreDetteIntStrucList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteServPreDetteIntStruc() throws Exception {
        // Initialize the database
        servPreDetteIntStrucRepository.saveAndFlush(servPreDetteIntStruc);

        int databaseSizeBeforeDelete = servPreDetteIntStrucRepository.findAll().size();

        // Delete the servPreDetteIntStruc
        restServPreDetteIntStrucMockMvc.perform(delete("/api/serv-pre-dette-int-strucs/{id}", servPreDetteIntStruc.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ServPreDetteIntStruc> servPreDetteIntStrucList = servPreDetteIntStrucRepository.findAll();
        assertThat(servPreDetteIntStrucList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
