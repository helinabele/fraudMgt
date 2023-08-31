package org.audit.app.service.dto;

import java.io.Serializable;
import java.util.Objects;
import org.audit.app.domain.enumeration.Gender;

/**
 * A DTO for the {@link org.audit.app.domain.WhistleBlowerReport} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class WhistleBlowerReportDTO implements Serializable {

    private String id;

    private String fullName;

    private Gender genderType;

    private String emailAdress;

    private Integer phone;

    private String organization;

    private String message;

    private byte[] attachment;

    private String attachmentContentType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Gender getGenderType() {
        return genderType;
    }

    public void setGenderType(Gender genderType) {
        this.genderType = genderType;
    }

    public String getEmailAdress() {
        return emailAdress;
    }

    public void setEmailAdress(String emailAdress) {
        this.emailAdress = emailAdress;
    }

    public Integer getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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
        if (!(o instanceof WhistleBlowerReportDTO)) {
            return false;
        }

        WhistleBlowerReportDTO whistleBlowerReportDTO = (WhistleBlowerReportDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, whistleBlowerReportDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WhistleBlowerReportDTO{" +
            "id='" + getId() + "'" +
            ", fullName='" + getFullName() + "'" +
            ", genderType='" + getGenderType() + "'" +
            ", emailAdress='" + getEmailAdress() + "'" +
            ", phone=" + getPhone() +
            ", organization='" + getOrganization() + "'" +
            ", message='" + getMessage() + "'" +
            ", attachment='" + getAttachment() + "'" +
            "}";
    }
}
