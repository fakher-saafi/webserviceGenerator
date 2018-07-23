package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.App3App;

import com.mycompany.myapp.domain.MyTable;
import com.mycompany.myapp.repository.MyTableRepository;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;


import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the MyTableResource REST controller.
 *
 * @see MyTableResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = App3App.class)
public class MyTableResourceIntTest {

    private static final String DEFAULT_TABLE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_TABLE_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_NB_COLOMN = 1;
    private static final Integer UPDATED_NB_COLOMN = 2;

    @Autowired
    private MyTableRepository myTableRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMyTableMockMvc;

    private MyTable myTable;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MyTableResource myTableResource = new MyTableResource(myTableRepository);
        this.restMyTableMockMvc = MockMvcBuilders.standaloneSetup(myTableResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MyTable createEntity(EntityManager em) {
        MyTable myTable = new MyTable()
            .tableName(DEFAULT_TABLE_NAME)
            .nbColomn(DEFAULT_NB_COLOMN);
        return myTable;
    }

    @Before
    public void initTest() {
        myTable = createEntity(em);
    }

    @Test
    @Transactional
    public void createMyTable() throws Exception {
        int databaseSizeBeforeCreate = myTableRepository.findAll().size();

        // Create the MyTable
        restMyTableMockMvc.perform(post("/api/my-tables")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(myTable)))
            .andExpect(status().isCreated());

        // Validate the MyTable in the database
        List<MyTable> myTableList = myTableRepository.findAll();
        assertThat(myTableList).hasSize(databaseSizeBeforeCreate + 1);
        MyTable testMyTable = myTableList.get(myTableList.size() - 1);
        assertThat(testMyTable.getTableName()).isEqualTo(DEFAULT_TABLE_NAME);
        assertThat(testMyTable.getNbColomn()).isEqualTo(DEFAULT_NB_COLOMN);
    }

    @Test
    @Transactional
    public void createMyTableWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = myTableRepository.findAll().size();

        // Create the MyTable with an existing ID
        myTable.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMyTableMockMvc.perform(post("/api/my-tables")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(myTable)))
            .andExpect(status().isBadRequest());

        // Validate the MyTable in the database
        List<MyTable> myTableList = myTableRepository.findAll();
        assertThat(myTableList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMyTables() throws Exception {
        // Initialize the database
        myTableRepository.saveAndFlush(myTable);

        // Get all the myTableList
        restMyTableMockMvc.perform(get("/api/my-tables?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(myTable.getId().intValue())))
            .andExpect(jsonPath("$.[*].tableName").value(hasItem(DEFAULT_TABLE_NAME.toString())))
            .andExpect(jsonPath("$.[*].nbColomn").value(hasItem(DEFAULT_NB_COLOMN)));
    }
    

    @Test
    @Transactional
    public void getMyTable() throws Exception {
        // Initialize the database
        myTableRepository.saveAndFlush(myTable);

        // Get the myTable
        restMyTableMockMvc.perform(get("/api/my-tables/{id}", myTable.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(myTable.getId().intValue()))
            .andExpect(jsonPath("$.tableName").value(DEFAULT_TABLE_NAME.toString()))
            .andExpect(jsonPath("$.nbColomn").value(DEFAULT_NB_COLOMN));
    }
    @Test
    @Transactional
    public void getNonExistingMyTable() throws Exception {
        // Get the myTable
        restMyTableMockMvc.perform(get("/api/my-tables/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMyTable() throws Exception {
        // Initialize the database
        myTableRepository.saveAndFlush(myTable);

        int databaseSizeBeforeUpdate = myTableRepository.findAll().size();

        // Update the myTable
        MyTable updatedMyTable = myTableRepository.findById(myTable.getId()).get();
        // Disconnect from session so that the updates on updatedMyTable are not directly saved in db
        em.detach(updatedMyTable);
        updatedMyTable
            .tableName(UPDATED_TABLE_NAME)
            .nbColomn(UPDATED_NB_COLOMN);

        restMyTableMockMvc.perform(put("/api/my-tables")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMyTable)))
            .andExpect(status().isOk());

        // Validate the MyTable in the database
        List<MyTable> myTableList = myTableRepository.findAll();
        assertThat(myTableList).hasSize(databaseSizeBeforeUpdate);
        MyTable testMyTable = myTableList.get(myTableList.size() - 1);
        assertThat(testMyTable.getTableName()).isEqualTo(UPDATED_TABLE_NAME);
        assertThat(testMyTable.getNbColomn()).isEqualTo(UPDATED_NB_COLOMN);
    }

    @Test
    @Transactional
    public void updateNonExistingMyTable() throws Exception {
        int databaseSizeBeforeUpdate = myTableRepository.findAll().size();

        // Create the MyTable

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMyTableMockMvc.perform(put("/api/my-tables")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(myTable)))
            .andExpect(status().isBadRequest());

        // Validate the MyTable in the database
        List<MyTable> myTableList = myTableRepository.findAll();
        assertThat(myTableList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMyTable() throws Exception {
        // Initialize the database
        myTableRepository.saveAndFlush(myTable);

        int databaseSizeBeforeDelete = myTableRepository.findAll().size();

        // Get the myTable
        restMyTableMockMvc.perform(delete("/api/my-tables/{id}", myTable.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MyTable> myTableList = myTableRepository.findAll();
        assertThat(myTableList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MyTable.class);
        MyTable myTable1 = new MyTable();
        myTable1.setId(1L);
        MyTable myTable2 = new MyTable();
        myTable2.setId(myTable1.getId());
        assertThat(myTable1).isEqualTo(myTable2);
        myTable2.setId(2L);
        assertThat(myTable1).isNotEqualTo(myTable2);
        myTable1.setId(null);
        assertThat(myTable1).isNotEqualTo(myTable2);
    }
}
