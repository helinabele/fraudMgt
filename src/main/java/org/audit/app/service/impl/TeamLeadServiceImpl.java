package org.audit.app.service.impl;

import java.util.Optional;
import org.audit.app.domain.TeamLead;
import org.audit.app.repository.TeamLeadRepository;
import org.audit.app.service.TeamLeadService;
import org.audit.app.service.dto.TeamLeadDTO;
import org.audit.app.service.mapper.TeamLeadMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link TeamLead}.
 */
@Service
public class TeamLeadServiceImpl implements TeamLeadService {

    private final Logger log = LoggerFactory.getLogger(TeamLeadServiceImpl.class);

    private final TeamLeadRepository teamLeadRepository;

    private final TeamLeadMapper teamLeadMapper;

    public TeamLeadServiceImpl(TeamLeadRepository teamLeadRepository, TeamLeadMapper teamLeadMapper) {
        this.teamLeadRepository = teamLeadRepository;
        this.teamLeadMapper = teamLeadMapper;
    }

    @Override
    public TeamLeadDTO save(TeamLeadDTO teamLeadDTO) {
        log.debug("Request to save TeamLead : {}", teamLeadDTO);
        TeamLead teamLead = teamLeadMapper.toEntity(teamLeadDTO);
        teamLead = teamLeadRepository.save(teamLead);
        return teamLeadMapper.toDto(teamLead);
    }

    @Override
    public TeamLeadDTO update(TeamLeadDTO teamLeadDTO) {
        log.debug("Request to update TeamLead : {}", teamLeadDTO);
        TeamLead teamLead = teamLeadMapper.toEntity(teamLeadDTO);
        teamLead = teamLeadRepository.save(teamLead);
        return teamLeadMapper.toDto(teamLead);
    }

    @Override
    public Optional<TeamLeadDTO> partialUpdate(TeamLeadDTO teamLeadDTO) {
        log.debug("Request to partially update TeamLead : {}", teamLeadDTO);

        return teamLeadRepository
            .findById(teamLeadDTO.getId())
            .map(existingTeamLead -> {
                teamLeadMapper.partialUpdate(existingTeamLead, teamLeadDTO);

                return existingTeamLead;
            })
            .map(teamLeadRepository::save)
            .map(teamLeadMapper::toDto);
    }

    @Override
    public Page<TeamLeadDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TeamLeads");
        return teamLeadRepository.findAll(pageable).map(teamLeadMapper::toDto);
    }

    public Page<TeamLeadDTO> findAllWithEagerRelationships(Pageable pageable) {
        return teamLeadRepository.findAllWithEagerRelationships(pageable).map(teamLeadMapper::toDto);
    }

    @Override
    public Optional<TeamLeadDTO> findOne(String id) {
        log.debug("Request to get TeamLead : {}", id);
        return teamLeadRepository.findOneWithEagerRelationships(id).map(teamLeadMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete TeamLead : {}", id);
        teamLeadRepository.deleteById(id);
    }
}
