package org.audit.app.domain;

import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A FraudType.
 */
@Document(collection = "fraud_type")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FraudType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("fraud_name")
    private String fraudName;

    @Field("description")
    private String description;

    @Field("attachment")
    private byte[] attachment;

    @Field("attachment_content_type")
    private String attachmentContentType;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public FraudType id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFraudName() {
        return this.fraudName;
    }

    public FraudType fraudName(String fraudName) {
        this.setFraudName(fraudName);
        return this;
    }

    public void setFraudName(String fraudName) {
        this.fraudName = fraudName;
    }

    public String getDescription() {
        return this.description;
    }

    public FraudType description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getAttachment() {
        return this.attachment;
    }

    public FraudType attachment(byte[] attachment) {
        this.setAttachment(attachment);
        return this;
    }

    public void setAttachment(byte[] attachment) {
        this.attachment = attachment;
    }

    public String getAttachmentContentType() {
        return this.attachmentContentType;
    }

    public FraudType attachmentContentType(String attachmentContentType) {
        this.attachmentContentType = attachmentContentType;
        return this;
    }

    public void setAttachmentContentType(String attachmentContentType) {
        this.attachmentContentType = attachmentContentType;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FraudType)) {
            return false;
        }
        return id != null && id.equals(((FraudType) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FraudType{" +
            "id=" + getId() +
            ", fraudName='" + getFraudName() + "'" +
            ", description='" + getDescription() + "'" +
            ", attachment='" + getAttachment() + "'" +
            ", attachmentContentType='" + getAttachmentContentType() + "'" +
            "}";
    }
}
