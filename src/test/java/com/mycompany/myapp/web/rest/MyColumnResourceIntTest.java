package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.App3App;

import com.mycompany.myapp.domain.MyColumn;
import com.mycompany.myapp.repository.MyColumnRepository;
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
 * Test class for the MyColumnResource REST controller.
 *
 * @see MyColumnResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = App3App.class)
public class MyColumnResourceIntTest {

    private static final String DEFAULT_COLUMN_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COLUMN_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    @Autowired
    private MyColumnRepository myColumnRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMyColumnMockMvc;

    private MyColumn myColumn;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MyColumnResource myColumnResource = new MyColumnResource(myColumnRepository);
        this.restMyColumnMockMvc = MockMvcBuilders.standaloneSetup(myColumnResource)
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
    public static MyColumn createEntity(EntityManager em) {
        MyColumn myColumn = new MyColumn()
            .columnName(DEFAULT_COLUMN_NAME)
            .type(DEFAULT_TYPE);
        return myColumn;
    }

    @Before
    public void initTest() {
        myColumn = createEntity(em);
    }

    @Test
    @Transactional
    public void createMyColumn() throws Exception {
        int databaseSizeBeforeCreate = myColumnRepository.findAll().size();

        // Create the MyColumn
        restMyColumnMockMvc.perform(post("/api/my-columns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(myColumn)))
            .andExpect(status().isCreated());

        // Validate the MyColumn in the database
        List<MyColumn> myColumnList = myColumnRepository.findAll();
        assertThat(myColumnList).hasSize(databaseSizeBeforeCreate + 1);
        MyColumn testMyColumn = myColumnList.get(myColumnList.size() - 1);
        assertThat(testMyColumn.getColumnName()).isEqualTo(DEFAULT_COLUMN_NAME);
        assertThat(testMyColumn.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void createMyColumnWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = myColumnRepository.findAll().size();

        // Create the MyColumn with an existing ID
        myColumn.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMyColumnMockMvc.perform(post("/api/my-columns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(myColumn)))
            .andExpect(status().isBadRequest());

        // Validate the MyColumn in the database
        List<MyColumn> myColumnList = myColumnRepository.findAll();
        assertThat(myColumnList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMyColumns() throws Exception {
        // Initialize the database
        myColumnRepository.saveAndFlush(myColumn);

        // Get all the myColumnList
        restMyColumnMockMvc.perform(get("/api/my-columns?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(myColumn.getId().intValue())))
            .andExpect(jsonPath("$.[*].columnName").value(hasItem(DEFAULT_COLUMN_NAME.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }
    

    @Test
    @Transactional
    public void getMyColumn() throws Exception {
        // Initialize the database
        myColumnRepository.saveAndFlush(myColumn);

        // Get the myColumn
        restMyColumnMockMvc.perform(get("/api/my-columns/{id}", myColumn.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(myColumn.getId().intValue()))
            .andExpect(jsonPath("$.columnName").value(DEFAULT_COLUMN_NAME.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingMyColumn() throws Exception {
        // Get the myColumn
        restMyColumnMockMvc.perform(get("/api/my-columns/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMyColumn() throws Exception {
        // Initialize the database
        myColumnRepository.saveAndFlush(myColumn);

        int databaseSizeBeforeUpdate = myColumnRepository.findAll().size();

        // Update the myColumn
        MyColumn updatedMyColumn = myColumnRepository.findById(myColumn.getId()).get();
        // Disconnect from session so that the updates on updatedMyColumn are not directly saved in db
        em.detach(updatedMyColumn);
        updatedMyColumn
            .columnName(UPDATED_COLUMN_NAME)
            .type(UPDATED_TYPE);

        restMyColumnMockMvc.perform(put("/api/my-columns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMyColumn)))
            .andExpect(status().isOk());

        // Validate the MyColumn in the database
        List<MyColumn> myColumnList = myColumnRepository.findAll();
        assertThat(myColumnList).hasSize(databaseSizeBeforeUpdate);
        MyColumn testMyColumn = myColumnList.get(myColumnList.size() - 1);
        assertThat(testMyColumn.getColumnName()).isEqualTo(UPDATED_COLUMN_NAME);
        assertThat(testMyColumn.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingMyColumn() throws Exception {
        int databaseSizeBeforeUpdate = myColumnRepository.findAll().size();

        // Create the MyColumn

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMyColumnMockMvc.perform(put("/api/my-columns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(myColumn)))
            .andExpect(status().isBadRequest());

        // Validate the MyColumn in the database
        List<MyColumn> myColumnList = myColumnRepository.findAll();
        assertThat(myColumnList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMyColumn() throws Exception {
        // Initialize the database
        myColumnRepository.saveAndFlush(myColumn);

        int databaseSizeBeforeDelete = myColumnRepository.findAll().size();

        // Get the myColumn
        restMyColumnMockMvc.perform(delete("/api/my-columns/{id}", myColumn.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MyColumn> myColumnList = myColumnRepository.findAll();
        assertThat(myColumnList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MyColumn.class);
        MyColumn myColumn1 = new MyColumn();
        myColumn1.setId(1L);
        MyColumn myColumn2 = new MyColumn();
        myColumn2.setId(myColumn1.getId());
        assertThat(myColumn1).isEqualTo(myColumn2);
        myColumn2.setId(2L);
        assertThat(myColumn1).isNotEqualTo(myColumn2);
        myColumn1.setId(null);
        assertThat(myColumn1).isNotEqualTo(myColumn2);
    }
}
