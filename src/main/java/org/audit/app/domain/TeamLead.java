package org.audit.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A TeamLead.
 */
@Document(collection = "team_lead")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TeamLead implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Size(max = 50)
    @Field("team_lead_name")
    private String teamLeadName;

    @Field("description")
    private String description;

    @Field("managerial_id")
    private Integer managerialId;

    @DBRef
    @Field("user")
    private User user;

    @DBRef
    @Field("managers")
    @JsonIgnoreProperties(value = { "user", "directors" }, allowSetters = true)
    private Managerial managers;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public TeamLead id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTeamLeadName() {
        return this.teamLeadName;
    }

    public TeamLead teamLeadName(String teamLeadName) {
        this.setTeamLeadName(teamLeadName);
        return this;
    }

    public void setTeamLeadName(String teamLeadName) {
        this.teamLeadName = teamLeadName;
    }

    public String getDescription() {
        return this.description;
    }

    public TeamLead description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getManagerialId() {
        return this.managerialId;
    }

    public TeamLead managerialId(Integer managerialId) {
        this.setManagerialId(managerialId);
        return this;
    }

    public void setManagerialId(Integer managerialId) {
        this.managerialId = managerialId;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public TeamLead user(User user) {
        this.setUser(user);
        return this;
    }

    public Managerial getManagers() {
        return this.managers;
    }

    public void setManagers(Managerial managerial) {
        this.managers = managerial;
    }

    public TeamLead managers(Managerial managerial) {
        this.setManagers(managerial);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TeamLead)) {
            return false;
        }
        return id != null && id.equals(((TeamLead) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TeamLead{" +
            "id=" + getId() +
            ", teamLeadName='" + getTeamLeadName() + "'" +
            ", description='" + getDescription() + "'" +
            ", managerialId=" + getManagerialId() +
            "}";
    }
}
