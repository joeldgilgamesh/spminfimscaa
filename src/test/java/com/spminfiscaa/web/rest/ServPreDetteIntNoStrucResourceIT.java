package com.spminfiscaa.web.rest;

import com.spminfiscaa.SpminfimscaaApp;
import com.spminfiscaa.domain.ServPreDetteIntNoStruc;
import com.spminfiscaa.repository.ServPreDetteIntNoStrucRepository;
import com.spminfiscaa.service.ServPreDetteIntNoStrucService;
import com.spminfiscaa.service.dto.ServPreDetteIntNoStrucDTO;
import com.spminfiscaa.service.mapper.ServPreDetteIntNoStrucMapper;

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
 * Integration tests for the {@link ServPreDetteIntNoStrucResource} REST controller.
 */
@SpringBootTest(classes = SpminfimscaaApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ServPreDetteIntNoStrucResourceIT {

    private static final String DEFAULT_CATEGORIE = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORIE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_ECHEANCE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ECHEANCE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_TOTAL = 1L;
    private static final Long UPDATED_TOTAL = 2L;

    @Autowired
    private ServPreDetteIntNoStrucRepository servPreDetteIntNoStrucRepository;

    @Autowired
    private ServPreDetteIntNoStrucMapper servPreDetteIntNoStrucMapper;

    @Autowired
    private ServPreDetteIntNoStrucService servPreDetteIntNoStrucService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restServPreDetteIntNoStrucMockMvc;

    private ServPreDetteIntNoStruc servPreDetteIntNoStruc;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServPreDetteIntNoStruc createEntity(EntityManager em) {
        ServPreDetteIntNoStruc servPreDetteIntNoStruc = new ServPreDetteIntNoStruc()
            .categorie(DEFAULT_CATEGORIE)
            .echeance(DEFAULT_ECHEANCE)
            .date(DEFAULT_DATE)
            .total(DEFAULT_TOTAL);
        return servPreDetteIntNoStruc;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServPreDetteIntNoStruc createUpdatedEntity(EntityManager em) {
        ServPreDetteIntNoStruc servPreDetteIntNoStruc = new ServPreDetteIntNoStruc()
            .categorie(UPDATED_CATEGORIE)
            .echeance(UPDATED_ECHEANCE)
            .date(UPDATED_DATE)
            .total(UPDATED_TOTAL);
        return servPreDetteIntNoStruc;
    }

    @BeforeEach
    public void initTest() {
        servPreDetteIntNoStruc = createEntity(em);
    }

    @Test
    @Transactional
    public void createServPreDetteIntNoStruc() throws Exception {
        int databaseSizeBeforeCreate = servPreDetteIntNoStrucRepository.findAll().size();
        // Create the ServPreDetteIntNoStruc
        ServPreDetteIntNoStrucDTO servPreDetteIntNoStrucDTO = servPreDetteIntNoStrucMapper.toDto(servPreDetteIntNoStruc);
        restServPreDetteIntNoStrucMockMvc.perform(post("/api/serv-pre-dette-int-no-strucs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(servPreDetteIntNoStrucDTO)))
            .andExpect(status().isCreated());

        // Validate the ServPreDetteIntNoStruc in the database
        List<ServPreDetteIntNoStruc> servPreDetteIntNoStrucList = servPreDetteIntNoStrucRepository.findAll();
        assertThat(servPreDetteIntNoStrucList).hasSize(databaseSizeBeforeCreate + 1);
        ServPreDetteIntNoStruc testServPreDetteIntNoStruc = servPreDetteIntNoStrucList.get(servPreDetteIntNoStrucList.size() - 1);
        assertThat(testServPreDetteIntNoStruc.getCategorie()).isEqualTo(DEFAULT_CATEGORIE);
        assertThat(testServPreDetteIntNoStruc.getEcheance()).isEqualTo(DEFAULT_ECHEANCE);
        assertThat(testServPreDetteIntNoStruc.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testServPreDetteIntNoStruc.getTotal()).isEqualTo(DEFAULT_TOTAL);
    }

    @Test
    @Transactional
    public void createServPreDetteIntNoStrucWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = servPreDetteIntNoStrucRepository.findAll().size();

        // Create the ServPreDetteIntNoStruc with an existing ID
        servPreDetteIntNoStruc.setId(1L);
        ServPreDetteIntNoStrucDTO servPreDetteIntNoStrucDTO = servPreDetteIntNoStrucMapper.toDto(servPreDetteIntNoStruc);

        // An entity with an existing ID cannot be created, so this API call must fail
        restServPreDetteIntNoStrucMockMvc.perform(post("/api/serv-pre-dette-int-no-strucs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(servPreDetteIntNoStrucDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ServPreDetteIntNoStruc in the database
        List<ServPreDetteIntNoStruc> servPreDetteIntNoStrucList = servPreDetteIntNoStrucRepository.findAll();
        assertThat(servPreDetteIntNoStrucList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllServPreDetteIntNoStrucs() throws Exception {
        // Initialize the database
        servPreDetteIntNoStrucRepository.saveAndFlush(servPreDetteIntNoStruc);

        // Get all the servPreDetteIntNoStrucList
        restServPreDetteIntNoStrucMockMvc.perform(get("/api/serv-pre-dette-int-no-strucs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(servPreDetteIntNoStruc.getId().intValue())))
            .andExpect(jsonPath("$.[*].categorie").value(hasItem(DEFAULT_CATEGORIE)))
            .andExpect(jsonPath("$.[*].echeance").value(hasItem(DEFAULT_ECHEANCE.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].total").value(hasItem(DEFAULT_TOTAL.intValue())));
    }
    
    @Test
    @Transactional
    public void getServPreDetteIntNoStruc() throws Exception {
        // Initialize the database
        servPreDetteIntNoStrucRepository.saveAndFlush(servPreDetteIntNoStruc);

        // Get the servPreDetteIntNoStruc
        restServPreDetteIntNoStrucMockMvc.perform(get("/api/serv-pre-dette-int-no-strucs/{id}", servPreDetteIntNoStruc.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(servPreDetteIntNoStruc.getId().intValue()))
            .andExpect(jsonPath("$.categorie").value(DEFAULT_CATEGORIE))
            .andExpect(jsonPath("$.echeance").value(DEFAULT_ECHEANCE.toString()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.total").value(DEFAULT_TOTAL.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingServPreDetteIntNoStruc() throws Exception {
        // Get the servPreDetteIntNoStruc
        restServPreDetteIntNoStrucMockMvc.perform(get("/api/serv-pre-dette-int-no-strucs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateServPreDetteIntNoStruc() throws Exception {
        // Initialize the database
        servPreDetteIntNoStrucRepository.saveAndFlush(servPreDetteIntNoStruc);

        int databaseSizeBeforeUpdate = servPreDetteIntNoStrucRepository.findAll().size();

        // Update the servPreDetteIntNoStruc
        ServPreDetteIntNoStruc updatedServPreDetteIntNoStruc = servPreDetteIntNoStrucRepository.findById(servPreDetteIntNoStruc.getId()).get();
        // Disconnect from session so that the updates on updatedServPreDetteIntNoStruc are not directly saved in db
        em.detach(updatedServPreDetteIntNoStruc);
        updatedServPreDetteIntNoStruc
            .categorie(UPDATED_CATEGORIE)
            .echeance(UPDATED_ECHEANCE)
            .date(UPDATED_DATE)
            .total(UPDATED_TOTAL);
        ServPreDetteIntNoStrucDTO servPreDetteIntNoStrucDTO = servPreDetteIntNoStrucMapper.toDto(updatedServPreDetteIntNoStruc);

        restServPreDetteIntNoStrucMockMvc.perform(put("/api/serv-pre-dette-int-no-strucs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(servPreDetteIntNoStrucDTO)))
            .andExpect(status().isOk());

        // Validate the ServPreDetteIntNoStruc in the database
        List<ServPreDetteIntNoStruc> servPreDetteIntNoStrucList = servPreDetteIntNoStrucRepository.findAll();
        assertThat(servPreDetteIntNoStrucList).hasSize(databaseSizeBeforeUpdate);
        ServPreDetteIntNoStruc testServPreDetteIntNoStruc = servPreDetteIntNoStrucList.get(servPreDetteIntNoStrucList.size() - 1);
        assertThat(testServPreDetteIntNoStruc.getCategorie()).isEqualTo(UPDATED_CATEGORIE);
        assertThat(testServPreDetteIntNoStruc.getEcheance()).isEqualTo(UPDATED_ECHEANCE);
        assertThat(testServPreDetteIntNoStruc.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testServPreDetteIntNoStruc.getTotal()).isEqualTo(UPDATED_TOTAL);
    }

    @Test
    @Transactional
    public void updateNonExistingServPreDetteIntNoStruc() throws Exception {
        int databaseSizeBeforeUpdate = servPreDetteIntNoStrucRepository.findAll().size();

        // Create the ServPreDetteIntNoStruc
        ServPreDetteIntNoStrucDTO servPreDetteIntNoStrucDTO = servPreDetteIntNoStrucMapper.toDto(servPreDetteIntNoStruc);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServPreDetteIntNoStrucMockMvc.perform(put("/api/serv-pre-dette-int-no-strucs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(servPreDetteIntNoStrucDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ServPreDetteIntNoStruc in the database
        List<ServPreDetteIntNoStruc> servPreDetteIntNoStrucList = servPreDetteIntNoStrucRepository.findAll();
        assertThat(servPreDetteIntNoStrucList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteServPreDetteIntNoStruc() throws Exception {
        // Initialize the database
        servPreDetteIntNoStrucRepository.saveAndFlush(servPreDetteIntNoStruc);

        int databaseSizeBeforeDelete = servPreDetteIntNoStrucRepository.findAll().size();

        // Delete the servPreDetteIntNoStruc
        restServPreDetteIntNoStrucMockMvc.perform(delete("/api/serv-pre-dette-int-no-strucs/{id}", servPreDetteIntNoStruc.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ServPreDetteIntNoStruc> servPreDetteIntNoStrucList = servPreDetteIntNoStrucRepository.findAll();
        assertThat(servPreDetteIntNoStrucList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
