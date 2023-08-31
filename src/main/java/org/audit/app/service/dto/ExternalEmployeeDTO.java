package org.audit.app.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link org.audit.app.domain.ExternalEmployee} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ExternalEmployeeDTO implements Serializable {

    private String id;

    @NotNull
    private String name;

    private String occupation;

    private String telephone;

    @NotNull
    private String address;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ExternalEmployeeDTO)) {
            return false;
        }

        ExternalEmployeeDTO externalEmployeeDTO = (ExternalEmployeeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, externalEmployeeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ExternalEmployeeDTO{" +
            "id='" + getId() + "'" +
            ", name='" + getName() + "'" +
            ", occupation='" + getOccupation() + "'" +
            ", telephone='" + getTelephone() + "'" +
            ", address='" + getAddress() + "'" +
            "}";
    }
}
