package org.audit.app.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link org.audit.app.domain.Managerial} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ManagerialDTO implements Serializable {

    private String id;

    @Size(max = 50)
    private String managerialName;

    private String description;

    private Integer directorId;

    private UserDTO user;

    private DirectorDTO directors;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getManagerialName() {
        return managerialName;
    }

    public void setManagerialName(String managerialName) {
        this.managerialName = managerialName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDirectorId() {
        return directorId;
    }

    public void setDirectorId(Integer directorId) {
        this.directorId = directorId;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public DirectorDTO getDirectors() {
        return directors;
    }

    public void setDirectors(DirectorDTO directors) {
        this.directors = directors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ManagerialDTO)) {
            return false;
        }

        ManagerialDTO managerialDTO = (ManagerialDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, managerialDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ManagerialDTO{" +
            "id='" + getId() + "'" +
            ", managerialName='" + getManagerialName() + "'" +
            ", description='" + getDescription() + "'" +
            ", directorId=" + getDirectorId() +
            ", user=" + getUser() +
            ", directors=" + getDirectors() +
            "}";
    }
}
