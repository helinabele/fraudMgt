package org.audit.app.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link org.audit.app.domain.TeamLead} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TeamLeadDTO implements Serializable {

    private String id;

    @Size(max = 50)
    private String teamLeadName;

    private String description;

    private Integer managerialId;

    private UserDTO user;

    private ManagerialDTO managers;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTeamLeadName() {
        return teamLeadName;
    }

    public void setTeamLeadName(String teamLeadName) {
        this.teamLeadName = teamLeadName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getManagerialId() {
        return managerialId;
    }

    public void setManagerialId(Integer managerialId) {
        this.managerialId = managerialId;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public ManagerialDTO getManagers() {
        return managers;
    }

    public void setManagers(ManagerialDTO managers) {
        this.managers = managers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TeamLeadDTO)) {
            return false;
        }

        TeamLeadDTO teamLeadDTO = (TeamLeadDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, teamLeadDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TeamLeadDTO{" +
            "id='" + getId() + "'" +
            ", teamLeadName='" + getTeamLeadName() + "'" +
            ", description='" + getDescription() + "'" +
            ", managerialId=" + getManagerialId() +
            ", user=" + getUser() +
            ", managers=" + getManagers() +
            "}";
    }
}
