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
import org.audit.app.domain.TeamLead;
import org.audit.app.repository.TeamLeadRepository;
import org.audit.app.service.TeamLeadService;
import org.audit.app.service.dto.TeamLeadDTO;
import org.audit.app.service.mapper.TeamLeadMapper;
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
 * Integration tests for the {@link TeamLeadResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class TeamLeadResourceIT {

    private static final String DEFAULT_TEAM_LEAD_NAME = "AAAAAAAAAA";
    private static final String UPDATED_TEAM_LEAD_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_MANAGERIAL_ID = 1;
    private static final Integer UPDATED_MANAGERIAL_ID = 2;

    private static final String ENTITY_API_URL = "/api/team-leads";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private TeamLeadRepository teamLeadRepository;

    @Mock
    private TeamLeadRepository teamLeadRepositoryMock;

    @Autowired
    private TeamLeadMapper teamLeadMapper;

    @Mock
    private TeamLeadService teamLeadServiceMock;

    @Autowired
    private MockMvc restTeamLeadMockMvc;

    private TeamLead teamLead;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TeamLead createEntity() {
        TeamLead teamLead = new TeamLead()
            .teamLeadName(DEFAULT_TEAM_LEAD_NAME)
            .description(DEFAULT_DESCRIPTION)
            .managerialId(DEFAULT_MANAGERIAL_ID);
        return teamLead;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TeamLead createUpdatedEntity() {
        TeamLead teamLead = new TeamLead()
            .teamLeadName(UPDATED_TEAM_LEAD_NAME)
            .description(UPDATED_DESCRIPTION)
            .managerialId(UPDATED_MANAGERIAL_ID);
        return teamLead;
    }

    @BeforeEach
    public void initTest() {
        teamLeadRepository.deleteAll();
        teamLead = createEntity();
    }

    @Test
    void createTeamLead() throws Exception {
        int databaseSizeBeforeCreate = teamLeadRepository.findAll().size();
        // Create the TeamLead
        TeamLeadDTO teamLeadDTO = teamLeadMapper.toDto(teamLead);
        restTeamLeadMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(teamLeadDTO)))
            .andExpect(status().isCreated());

        // Validate the TeamLead in the database
        List<TeamLead> teamLeadList = teamLeadRepository.findAll();
        assertThat(teamLeadList).hasSize(databaseSizeBeforeCreate + 1);
        TeamLead testTeamLead = teamLeadList.get(teamLeadList.size() - 1);
        assertThat(testTeamLead.getTeamLeadName()).isEqualTo(DEFAULT_TEAM_LEAD_NAME);
        assertThat(testTeamLead.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testTeamLead.getManagerialId()).isEqualTo(DEFAULT_MANAGERIAL_ID);
    }

    @Test
    void createTeamLeadWithExistingId() throws Exception {
        // Create the TeamLead with an existing ID
        teamLead.setId("existing_id");
        TeamLeadDTO teamLeadDTO = teamLeadMapper.toDto(teamLead);

        int databaseSizeBeforeCreate = teamLeadRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTeamLeadMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(teamLeadDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TeamLead in the database
        List<TeamLead> teamLeadList = teamLeadRepository.findAll();
        assertThat(teamLeadList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllTeamLeads() throws Exception {
        // Initialize the database
        teamLeadRepository.save(teamLead);

        // Get all the teamLeadList
        restTeamLeadMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(teamLead.getId())))
            .andExpect(jsonPath("$.[*].teamLeadName").value(hasItem(DEFAULT_TEAM_LEAD_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].managerialId").value(hasItem(DEFAULT_MANAGERIAL_ID)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTeamLeadsWithEagerRelationshipsIsEnabled() throws Exception {
        when(teamLeadServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTeamLeadMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(teamLeadServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTeamLeadsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(teamLeadServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTeamLeadMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(teamLeadRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void getTeamLead() throws Exception {
        // Initialize the database
        teamLeadRepository.save(teamLead);

        // Get the teamLead
        restTeamLeadMockMvc
            .perform(get(ENTITY_API_URL_ID, teamLead.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(teamLead.getId()))
            .andExpect(jsonPath("$.teamLeadName").value(DEFAULT_TEAM_LEAD_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.managerialId").value(DEFAULT_MANAGERIAL_ID));
    }

    @Test
    void getNonExistingTeamLead() throws Exception {
        // Get the teamLead
        restTeamLeadMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingTeamLead() throws Exception {
        // Initialize the database
        teamLeadRepository.save(teamLead);

        int databaseSizeBeforeUpdate = teamLeadRepository.findAll().size();

        // Update the teamLead
        TeamLead updatedTeamLead = teamLeadRepository.findById(teamLead.getId()).get();
        updatedTeamLead.teamLeadName(UPDATED_TEAM_LEAD_NAME).description(UPDATED_DESCRIPTION).managerialId(UPDATED_MANAGERIAL_ID);
        TeamLeadDTO teamLeadDTO = teamLeadMapper.toDto(updatedTeamLead);

        restTeamLeadMockMvc
            .perform(
                put(ENTITY_API_URL_ID, teamLeadDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(teamLeadDTO))
            )
            .andExpect(status().isOk());

        // Validate the TeamLead in the database
        List<TeamLead> teamLeadList = teamLeadRepository.findAll();
        assertThat(teamLeadList).hasSize(databaseSizeBeforeUpdate);
        TeamLead testTeamLead = teamLeadList.get(teamLeadList.size() - 1);
        assertThat(testTeamLead.getTeamLeadName()).isEqualTo(UPDATED_TEAM_LEAD_NAME);
        assertThat(testTeamLead.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTeamLead.getManagerialId()).isEqualTo(UPDATED_MANAGERIAL_ID);
    }

    @Test
    void putNonExistingTeamLead() throws Exception {
        int databaseSizeBeforeUpdate = teamLeadRepository.findAll().size();
        teamLead.setId(UUID.randomUUID().toString());

        // Create the TeamLead
        TeamLeadDTO teamLeadDTO = teamLeadMapper.toDto(teamLead);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTeamLeadMockMvc
            .perform(
                put(ENTITY_API_URL_ID, teamLeadDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(teamLeadDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TeamLead in the database
        List<TeamLead> teamLeadList = teamLeadRepository.findAll();
        assertThat(teamLeadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchTeamLead() throws Exception {
        int databaseSizeBeforeUpdate = teamLeadRepository.findAll().size();
        teamLead.setId(UUID.randomUUID().toString());

        // Create the TeamLead
        TeamLeadDTO teamLeadDTO = teamLeadMapper.toDto(teamLead);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTeamLeadMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(teamLeadDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TeamLead in the database
        List<TeamLead> teamLeadList = teamLeadRepository.findAll();
        assertThat(teamLeadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamTeamLead() throws Exception {
        int databaseSizeBeforeUpdate = teamLeadRepository.findAll().size();
        teamLead.setId(UUID.randomUUID().toString());

        // Create the TeamLead
        TeamLeadDTO teamLeadDTO = teamLeadMapper.toDto(teamLead);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTeamLeadMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(teamLeadDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TeamLead in the database
        List<TeamLead> teamLeadList = teamLeadRepository.findAll();
        assertThat(teamLeadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateTeamLeadWithPatch() throws Exception {
        // Initialize the database
        teamLeadRepository.save(teamLead);

        int databaseSizeBeforeUpdate = teamLeadRepository.findAll().size();

        // Update the teamLead using partial update
        TeamLead partialUpdatedTeamLead = new TeamLead();
        partialUpdatedTeamLead.setId(teamLead.getId());

        partialUpdatedTeamLead.teamLeadName(UPDATED_TEAM_LEAD_NAME).description(UPDATED_DESCRIPTION);

        restTeamLeadMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTeamLead.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTeamLead))
            )
            .andExpect(status().isOk());

        // Validate the TeamLead in the database
        List<TeamLead> teamLeadList = teamLeadRepository.findAll();
        assertThat(teamLeadList).hasSize(databaseSizeBeforeUpdate);
        TeamLead testTeamLead = teamLeadList.get(teamLeadList.size() - 1);
        assertThat(testTeamLead.getTeamLeadName()).isEqualTo(UPDATED_TEAM_LEAD_NAME);
        assertThat(testTeamLead.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTeamLead.getManagerialId()).isEqualTo(DEFAULT_MANAGERIAL_ID);
    }

    @Test
    void fullUpdateTeamLeadWithPatch() throws Exception {
        // Initialize the database
        teamLeadRepository.save(teamLead);

        int databaseSizeBeforeUpdate = teamLeadRepository.findAll().size();

        // Update the teamLead using partial update
        TeamLead partialUpdatedTeamLead = new TeamLead();
        partialUpdatedTeamLead.setId(teamLead.getId());

        partialUpdatedTeamLead.teamLeadName(UPDATED_TEAM_LEAD_NAME).description(UPDATED_DESCRIPTION).managerialId(UPDATED_MANAGERIAL_ID);

        restTeamLeadMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTeamLead.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTeamLead))
            )
            .andExpect(status().isOk());

        // Validate the TeamLead in the database
        List<TeamLead> teamLeadList = teamLeadRepository.findAll();
        assertThat(teamLeadList).hasSize(databaseSizeBeforeUpdate);
        TeamLead testTeamLead = teamLeadList.get(teamLeadList.size() - 1);
        assertThat(testTeamLead.getTeamLeadName()).isEqualTo(UPDATED_TEAM_LEAD_NAME);
        assertThat(testTeamLead.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTeamLead.getManagerialId()).isEqualTo(UPDATED_MANAGERIAL_ID);
    }

    @Test
    void patchNonExistingTeamLead() throws Exception {
        int databaseSizeBeforeUpdate = teamLeadRepository.findAll().size();
        teamLead.setId(UUID.randomUUID().toString());

        // Create the TeamLead
        TeamLeadDTO teamLeadDTO = teamLeadMapper.toDto(teamLead);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTeamLeadMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, teamLeadDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(teamLeadDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TeamLead in the database
        List<TeamLead> teamLeadList = teamLeadRepository.findAll();
        assertThat(teamLeadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchTeamLead() throws Exception {
        int databaseSizeBeforeUpdate = teamLeadRepository.findAll().size();
        teamLead.setId(UUID.randomUUID().toString());

        // Create the TeamLead
        TeamLeadDTO teamLeadDTO = teamLeadMapper.toDto(teamLead);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTeamLeadMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(teamLeadDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TeamLead in the database
        List<TeamLead> teamLeadList = teamLeadRepository.findAll();
        assertThat(teamLeadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamTeamLead() throws Exception {
        int databaseSizeBeforeUpdate = teamLeadRepository.findAll().size();
        teamLead.setId(UUID.randomUUID().toString());

        // Create the TeamLead
        TeamLeadDTO teamLeadDTO = teamLeadMapper.toDto(teamLead);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTeamLeadMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(teamLeadDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TeamLead in the database
        List<TeamLead> teamLeadList = teamLeadRepository.findAll();
        assertThat(teamLeadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteTeamLead() throws Exception {
        // Initialize the database
        teamLeadRepository.save(teamLead);

        int databaseSizeBeforeDelete = teamLeadRepository.findAll().size();

        // Delete the teamLead
        restTeamLeadMockMvc
            .perform(delete(ENTITY_API_URL_ID, teamLead.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TeamLead> teamLeadList = teamLeadRepository.findAll();
        assertThat(teamLeadList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
