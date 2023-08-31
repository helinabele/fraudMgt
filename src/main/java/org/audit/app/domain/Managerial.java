package org.audit.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Managerial.
 */
@Document(collection = "managerial")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Managerial implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Size(max = 50)
    @Field("managerial_name")
    private String managerialName;

    @Field("description")
    private String description;

    @Field("director_id")
    private Integer directorId;

    @DBRef
    @Field("user")
    private User user;

    @DBRef
    @Field("directors")
    @JsonIgnoreProperties(value = { "user", "managers" }, allowSetters = true)
    private Director directors;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Managerial id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getManagerialName() {
        return this.managerialName;
    }

    public Managerial managerialName(String managerialName) {
        this.setManagerialName(managerialName);
        return this;
    }

    public void setManagerialName(String managerialName) {
        this.managerialName = managerialName;
    }

    public String getDescription() {
        return this.description;
    }

    public Managerial description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDirectorId() {
        return this.directorId;
    }

    public Managerial directorId(Integer directorId) {
        this.setDirectorId(directorId);
        return this;
    }

    public void setDirectorId(Integer directorId) {
        this.directorId = directorId;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Managerial user(User user) {
        this.setUser(user);
        return this;
    }

    public Director getDirectors() {
        return this.directors;
    }

    public void setDirectors(Director director) {
        this.directors = director;
    }

    public Managerial directors(Director director) {
        this.setDirectors(director);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Managerial)) {
            return false;
        }
        return id != null && id.equals(((Managerial) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Managerial{" +
            "id=" + getId() +
            ", managerialName='" + getManagerialName() + "'" +
            ", description='" + getDescription() + "'" +
            ", directorId=" + getDirectorId() +
            "}";
    }
}
