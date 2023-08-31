package org.audit.app.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link org.audit.app.domain.FraudType} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FraudTypeDTO implements Serializable {

    private String id;

    private String fraudName;

    private String description;

    private byte[] attachment;

    private String attachmentContentType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFraudName() {
        return fraudName;
    }

    public void setFraudName(String fraudName) {
        this.fraudName = fraudName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getAttachment() {
        return attachment;
    }

    public void setAttachment(byte[] attachment) {
        this.attachment = attachment;
    }

    public String getAttachmentContentType() {
        return attachmentContentType;
    }

    public void setAttachmentContentType(String attachmentContentType) {
        this.attachmentContentType = attachmentContentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FraudTypeDTO)) {
            return false;
        }

        FraudTypeDTO fraudTypeDTO = (FraudTypeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, fraudTypeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FraudTypeDTO{" +
            "id='" + getId() + "'" +
            ", fraudName='" + getFraudName() + "'" +
            ", description='" + getDescription() + "'" +
            ", attachment='" + getAttachment() + "'" +
            "}";
    }
}
