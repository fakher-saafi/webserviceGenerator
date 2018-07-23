package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.App3App;

import com.mycompany.myapp.domain.Webservice;
import com.mycompany.myapp.repository.WebserviceRepository;
import com.mycompany.myapp.service.UserService;
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

import com.mycompany.myapp.domain.enumeration.DataType;
import com.mycompany.myapp.domain.enumeration.DatabaseType;
import com.mycompany.myapp.domain.enumeration.SqlProduct;
/**
 * Test class for the WebserviceResource REST controller.
 *
 * @see WebserviceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = App3App.class)
public class WebserviceResourceIntTest {

    private static final String DEFAULT_WEBSERVICE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_WEBSERVICE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final DataType DEFAULT_DATATYPE = DataType.FILE;
    private static final DataType UPDATED_DATATYPE = DataType.DATABASE;

    private static final DatabaseType DEFAULT_DATABASE_TYPE = DatabaseType.SQL;
    private static final DatabaseType UPDATED_DATABASE_TYPE = DatabaseType.MONGODB;

    private static final SqlProduct DEFAULT_DATABASE_PRODUCT = SqlProduct.MYSQL;
    private static final SqlProduct UPDATED_DATABASE_PRODUCT = SqlProduct.POSTGRESQL;

    private static final String DEFAULT_DATABASE_PATH = "AAAAAAAAAA";
    private static final String UPDATED_DATABASE_PATH = "BBBBBBBBBB";

    private static final String DEFAULT_DB_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_DB_USERNAME = "BBBBBBBBBB";

    private static final String DEFAULT_DB_PASS = "AAAAAAAAAA";
    private static final String UPDATED_DB_PASS = "BBBBBBBBBB";

    @Autowired
    private UserService userService;

    @Autowired
    private WebserviceRepository webserviceRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restWebserviceMockMvc;

    private Webservice webservice;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final WebserviceResource webserviceResource = new WebserviceResource(userService, webserviceRepository);
        this.restWebserviceMockMvc = MockMvcBuilders.standaloneSetup(webserviceResource)
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
    public static Webservice createEntity(EntityManager em) {
        Webservice webservice = new Webservice()
            .webserviceName(DEFAULT_WEBSERVICE_NAME)
            .description(DEFAULT_DESCRIPTION)
            .datatype(DEFAULT_DATATYPE)
            .databaseType(DEFAULT_DATABASE_TYPE)
            .databaseProduct(DEFAULT_DATABASE_PRODUCT)
            .databasePath(DEFAULT_DATABASE_PATH)
            .dbUsername(DEFAULT_DB_USERNAME)
            .dbPass(DEFAULT_DB_PASS);
        return webservice;
    }

    @Before
    public void initTest() {
        webservice = createEntity(em);
    }

    @Test
    @Transactional
    public void createWebservice() throws Exception {
        int databaseSizeBeforeCreate = webserviceRepository.findAll().size();

        // Create the Webservice
        restWebserviceMockMvc.perform(post("/api/webservices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(webservice)))
            .andExpect(status().isCreated());

        // Validate the Webservice in the database
        List<Webservice> webserviceList = webserviceRepository.findAll();
        assertThat(webserviceList).hasSize(databaseSizeBeforeCreate + 1);
        Webservice testWebservice = webserviceList.get(webserviceList.size() - 1);
        assertThat(testWebservice.getWebserviceName()).isEqualTo(DEFAULT_WEBSERVICE_NAME);
        assertThat(testWebservice.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testWebservice.getDatatype()).isEqualTo(DEFAULT_DATATYPE);
        assertThat(testWebservice.getDatabaseType()).isEqualTo(DEFAULT_DATABASE_TYPE);
        assertThat(testWebservice.getDatabaseProduct()).isEqualTo(DEFAULT_DATABASE_PRODUCT);
        assertThat(testWebservice.getDatabasePath()).isEqualTo(DEFAULT_DATABASE_PATH);
        assertThat(testWebservice.getDbUsername()).isEqualTo(DEFAULT_DB_USERNAME);
        assertThat(testWebservice.getDbPass()).isEqualTo(DEFAULT_DB_PASS);
    }

    @Test
    @Transactional
    public void createWebserviceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = webserviceRepository.findAll().size();

        // Create the Webservice with an existing ID
        webservice.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWebserviceMockMvc.perform(post("/api/webservices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(webservice)))
            .andExpect(status().isBadRequest());

        // Validate the Webservice in the database
        List<Webservice> webserviceList = webserviceRepository.findAll();
        assertThat(webserviceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllWebservices() throws Exception {
        // Initialize the database
        webserviceRepository.saveAndFlush(webservice);

        // Get all the webserviceList
        restWebserviceMockMvc.perform(get("/api/webservices?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(webservice.getId().intValue())))
            .andExpect(jsonPath("$.[*].webserviceName").value(hasItem(DEFAULT_WEBSERVICE_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].datatype").value(hasItem(DEFAULT_DATATYPE.toString())))
            .andExpect(jsonPath("$.[*].databaseType").value(hasItem(DEFAULT_DATABASE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].databaseProduct").value(hasItem(DEFAULT_DATABASE_PRODUCT.toString())))
            .andExpect(jsonPath("$.[*].databasePath").value(hasItem(DEFAULT_DATABASE_PATH.toString())))
            .andExpect(jsonPath("$.[*].dbUsername").value(hasItem(DEFAULT_DB_USERNAME.toString())))
            .andExpect(jsonPath("$.[*].dbPass").value(hasItem(DEFAULT_DB_PASS.toString())));
    }


    @Test
    @Transactional
    public void getWebservice() throws Exception {
        // Initialize the database
        webserviceRepository.saveAndFlush(webservice);

        // Get the webservice
        restWebserviceMockMvc.perform(get("/api/webservices/{id}", webservice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(webservice.getId().intValue()))
            .andExpect(jsonPath("$.webserviceName").value(DEFAULT_WEBSERVICE_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.datatype").value(DEFAULT_DATATYPE.toString()))
            .andExpect(jsonPath("$.databaseType").value(DEFAULT_DATABASE_TYPE.toString()))
            .andExpect(jsonPath("$.databaseProduct").value(DEFAULT_DATABASE_PRODUCT.toString()))
            .andExpect(jsonPath("$.databasePath").value(DEFAULT_DATABASE_PATH.toString()))
            .andExpect(jsonPath("$.dbUsername").value(DEFAULT_DB_USERNAME.toString()))
            .andExpect(jsonPath("$.dbPass").value(DEFAULT_DB_PASS.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingWebservice() throws Exception {
        // Get the webservice
        restWebserviceMockMvc.perform(get("/api/webservices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWebservice() throws Exception {
        // Initialize the database
        webserviceRepository.saveAndFlush(webservice);

        int databaseSizeBeforeUpdate = webserviceRepository.findAll().size();

        // Update the webservice
        Webservice updatedWebservice = webserviceRepository.findById(webservice.getId()).get();
        // Disconnect from session so that the updates on updatedWebservice are not directly saved in db
        em.detach(updatedWebservice);
        updatedWebservice
            .webserviceName(UPDATED_WEBSERVICE_NAME)
            .description(UPDATED_DESCRIPTION)
            .datatype(UPDATED_DATATYPE)
            .databaseType(UPDATED_DATABASE_TYPE)
            .databaseProduct(UPDATED_DATABASE_PRODUCT)
            .databasePath(UPDATED_DATABASE_PATH)
            .dbUsername(UPDATED_DB_USERNAME)
            .dbPass(UPDATED_DB_PASS);

        restWebserviceMockMvc.perform(put("/api/webservices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedWebservice)))
            .andExpect(status().isOk());

        // Validate the Webservice in the database
        List<Webservice> webserviceList = webserviceRepository.findAll();
        assertThat(webserviceList).hasSize(databaseSizeBeforeUpdate);
        Webservice testWebservice = webserviceList.get(webserviceList.size() - 1);
        assertThat(testWebservice.getWebserviceName()).isEqualTo(UPDATED_WEBSERVICE_NAME);
        assertThat(testWebservice.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testWebservice.getDatatype()).isEqualTo(UPDATED_DATATYPE);
        assertThat(testWebservice.getDatabaseType()).isEqualTo(UPDATED_DATABASE_TYPE);
        assertThat(testWebservice.getDatabaseProduct()).isEqualTo(UPDATED_DATABASE_PRODUCT);
        assertThat(testWebservice.getDatabasePath()).isEqualTo(UPDATED_DATABASE_PATH);
        assertThat(testWebservice.getDbUsername()).isEqualTo(UPDATED_DB_USERNAME);
        assertThat(testWebservice.getDbPass()).isEqualTo(UPDATED_DB_PASS);
    }

    @Test
    @Transactional
    public void updateNonExistingWebservice() throws Exception {
        int databaseSizeBeforeUpdate = webserviceRepository.findAll().size();

        // Create the Webservice

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restWebserviceMockMvc.perform(put("/api/webservices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(webservice)))
            .andExpect(status().isBadRequest());

        // Validate the Webservice in the database
        List<Webservice> webserviceList = webserviceRepository.findAll();
        assertThat(webserviceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteWebservice() throws Exception {
        // Initialize the database
        webserviceRepository.saveAndFlush(webservice);

        int databaseSizeBeforeDelete = webserviceRepository.findAll().size();

        // Get the webservice
        restWebserviceMockMvc.perform(delete("/api/webservices/{id}", webservice.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Webservice> webserviceList = webserviceRepository.findAll();
        assertThat(webserviceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Webservice.class);
        Webservice webservice1 = new Webservice();
        webservice1.setId(1L);
        Webservice webservice2 = new Webservice();
        webservice2.setId(webservice1.getId());
        assertThat(webservice1).isEqualTo(webservice2);
        webservice2.setId(2L);
        assertThat(webservice1).isNotEqualTo(webservice2);
        webservice1.setId(null);
        assertThat(webservice1).isNotEqualTo(webservice2);
    }
}
