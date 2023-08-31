package org.audit.app.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link org.audit.app.domain.BankService} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BankServiceDTO implements Serializable {

    private String id;

    @NotNull
    private String serviceName;

    private String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BankServiceDTO)) {
            return false;
        }

        BankServiceDTO bankServiceDTO = (BankServiceDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, bankServiceDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BankServiceDTO{" +
            "id='" + getId() + "'" +
            ", serviceName='" + getServiceName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
