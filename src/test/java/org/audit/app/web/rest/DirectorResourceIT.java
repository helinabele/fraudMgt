package org.audit.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.audit.app.IntegrationTest;
import org.audit.app.domain.Director;
import org.audit.app.repository.DirectorRepository;
import org.audit.app.service.DirectorService;
import org.audit.app.service.dto.DirectorDTO;
import org.audit.app.service.mapper.DirectorMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for the {@link DirectorResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class DirectorResourceIT {

    private static final String DEFAULT_DIRECTOR_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DIRECTOR_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/directors";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private DirectorRepository directorRepository;

    @Mock
    private DirectorRepository directorRepositoryMock;

    @Autowired
    private DirectorMapper directorMapper;

    @Mock
    private DirectorService directorServiceMock;

    @Autowired
    private MockMvc restDirectorMockMvc;

    private Director director;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Director createEntity() {
        Director director = new Director().directorName(DEFAULT_DIRECTOR_NAME).description(DEFAULT_DESCRIPTION);
        return director;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Director createUpdatedEntity() {
        Director director = new Director().directorName(UPDATED_DIRECTOR_NAME).description(UPDATED_DESCRIPTION);
        return director;
    }

    @BeforeEach
    public void initTest() {
        directorRepository.deleteAll();
        director = createEntity();
    }

    @Test
    void createDirector() throws Exception {
        int databaseSizeBeforeCreate = directorRepository.findAll().size();
        // Create the Director
        DirectorDTO directorDTO = directorMapper.toDto(director);
        restDirectorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(directorDTO)))
            .andExpect(status().isCreated());

        // Validate the Director in the database
        List<Director> directorList = directorRepository.findAll();
        assertThat(directorList).hasSize(databaseSizeBeforeCreate + 1);
        Director testDirector = directorList.get(directorList.size() - 1);
        assertThat(testDirector.getDirectorName()).isEqualTo(DEFAULT_DIRECTOR_NAME);
        assertThat(testDirector.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    void createDirectorWithExistingId() throws Exception {
        // Create the Director with an existing ID
        director.setId("existing_id");
        DirectorDTO directorDTO = directorMapper.toDto(director);

        int databaseSizeBeforeCreate = directorRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDirectorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(directorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Director in the database
        List<Director> directorList = directorRepository.findAll();
        assertThat(directorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkDirectorNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = directorRepository.findAll().size();
        // set the field null
        director.setDirectorName(null);

        // Create the Director, which fails.
        DirectorDTO directorDTO = directorMapper.toDto(director);

        restDirectorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(directorDTO)))
            .andExpect(status().isBadRequest());

        List<Director> directorList = directorRepository.findAll();
        assertThat(directorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllDirectors() throws Exception {
        // Initialize the database
        directorRepository.save(director);

        // Get all the directorList
        restDirectorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(director.getId())))
            .andExpect(jsonPath("$.[*].directorName").value(hasItem(DEFAULT_DIRECTOR_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDirectorsWithEagerRelationshipsIsEnabled() throws Exception {
        when(directorServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDirectorMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(directorServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDirectorsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(directorServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDirectorMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(directorRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void getDirector() throws Exception {
        // Initialize the database
        directorRepository.save(director);

        // Get the director
        restDirectorMockMvc
            .perform(get(ENTITY_API_URL_ID, director.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(director.getId()))
            .andExpect(jsonPath("$.directorName").value(DEFAULT_DIRECTOR_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    void getNonExistingDirector() throws Exception {
        // Get the director
        restDirectorMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingDirector() throws Exception {
        // Initialize the database
        directorRepository.save(director);

        int databaseSizeBeforeUpdate = directorRepository.findAll().size();

        // Update the director
        Director updatedDirector = directorRepository.findById(director.getId()).get();
        updatedDirector.directorName(UPDATED_DIRECTOR_NAME).description(UPDATED_DESCRIPTION);
        DirectorDTO directorDTO = directorMapper.toDto(updatedDirector);

        restDirectorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, directorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(directorDTO))
            )
            .andExpect(status().isOk());

        // Validate the Director in the database
        List<Director> directorList = directorRepository.findAll();
        assertThat(directorList).hasSize(databaseSizeBeforeUpdate);
        Director testDirector = directorList.get(directorList.size() - 1);
        assertThat(testDirector.getDirectorName()).isEqualTo(UPDATED_DIRECTOR_NAME);
        assertThat(testDirector.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    void putNonExistingDirector() throws Exception {
        int databaseSizeBeforeUpdate = directorRepository.findAll().size();
        director.setId(UUID.randomUUID().toString());

        // Create the Director
        DirectorDTO directorDTO = directorMapper.toDto(director);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDirectorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, directorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(directorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Director in the database
        List<Director> directorList = directorRepository.findAll();
        assertThat(directorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchDirector() throws Exception {
        int databaseSizeBeforeUpdate = directorRepository.findAll().size();
        director.setId(UUID.randomUUID().toString());

        // Create the Director
        DirectorDTO directorDTO = directorMapper.toDto(director);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDirectorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(directorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Director in the database
        List<Director> directorList = directorRepository.findAll();
        assertThat(directorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamDirector() throws Exception {
        int databaseSizeBeforeUpdate = directorRepository.findAll().size();
        director.setId(UUID.randomUUID().toString());

        // Create the Director
        DirectorDTO directorDTO = directorMapper.toDto(director);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDirectorMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(directorDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Director in the database
        List<Director> directorList = directorRepository.findAll();
        assertThat(directorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateDirectorWithPatch() throws Exception {
        // Initialize the database
        directorRepository.save(director);

        int databaseSizeBeforeUpdate = directorRepository.findAll().size();

        // Update the director using partial update
        Director partialUpdatedDirector = new Director();
        partialUpdatedDirector.setId(director.getId());

        partialUpdatedDirector.directorName(UPDATED_DIRECTOR_NAME).description(UPDATED_DESCRIPTION);

        restDirectorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDirector.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDirector))
            )
            .andExpect(status().isOk());

        // Validate the Director in the database
        List<Director> directorList = directorRepository.findAll();
        assertThat(directorList).hasSize(databaseSizeBeforeUpdate);
        Director testDirector = directorList.get(directorList.size() - 1);
        assertThat(testDirector.getDirectorName()).isEqualTo(UPDATED_DIRECTOR_NAME);
        assertThat(testDirector.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    void fullUpdateDirectorWithPatch() throws Exception {
        // Initialize the database
        directorRepository.save(director);

        int databaseSizeBeforeUpdate = directorRepository.findAll().size();

        // Update the director using partial update
        Director partialUpdatedDirector = new Director();
        partialUpdatedDirector.setId(director.getId());

        partialUpdatedDirector.directorName(UPDATED_DIRECTOR_NAME).description(UPDATED_DESCRIPTION);

        restDirectorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDirector.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDirector))
            )
            .andExpect(status().isOk());

        // Validate the Director in the database
        List<Director> directorList = directorRepository.findAll();
        assertThat(directorList).hasSize(databaseSizeBeforeUpdate);
        Director testDirector = directorList.get(directorList.size() - 1);
        assertThat(testDirector.getDirectorName()).isEqualTo(UPDATED_DIRECTOR_NAME);
        assertThat(testDirector.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    void patchNonExistingDirector() throws Exception {
        int databaseSizeBeforeUpdate = directorRepository.findAll().size();
        director.setId(UUID.randomUUID().toString());

        // Create the Director
        DirectorDTO directorDTO = directorMapper.toDto(director);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDirectorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, directorDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(directorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Director in the database
        List<Director> directorList = directorRepository.findAll();
        assertThat(directorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchDirector() throws Exception {
        int databaseSizeBeforeUpdate = directorRepository.findAll().size();
        director.setId(UUID.randomUUID().toString());

        // Create the Director
        DirectorDTO directorDTO = directorMapper.toDto(director);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDirectorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(directorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Director in the database
        List<Director> directorList = directorRepository.findAll();
        assertThat(directorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamDirector() throws Exception {
        int databaseSizeBeforeUpdate = directorRepository.findAll().size();
        director.setId(UUID.randomUUID().toString());

        // Create the Director
        DirectorDTO directorDTO = directorMapper.toDto(director);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDirectorMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(directorDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Director in the database
        List<Director> directorList = directorRepository.findAll();
        assertThat(directorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteDirector() throws Exception {
        // Initialize the database
        directorRepository.save(director);

        int databaseSizeBeforeDelete = directorRepository.findAll().size();

        // Delete the director
        restDirectorMockMvc
            .perform(delete(ENTITY_API_URL_ID, director.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Director> directorList = directorRepository.findAll();
        assertThat(directorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
