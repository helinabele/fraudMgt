package org.audit.app.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link org.audit.app.domain.Director} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DirectorDTO implements Serializable {

    private String id;

    @NotNull
    @Size(max = 50)
    private String directorName;

    private String description;

    private UserDTO user;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDirectorName() {
        return directorName;
    }

    public void setDirectorName(String directorName) {
        this.directorName = directorName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DirectorDTO)) {
            return false;
        }

        DirectorDTO directorDTO = (DirectorDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, directorDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DirectorDTO{" +
            "id='" + getId() + "'" +
            ", directorName='" + getDirectorName() + "'" +
            ", description='" + getDescription() + "'" +
            ", user=" + getUser() +
            "}";
    }
}
