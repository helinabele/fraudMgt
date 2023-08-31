package org.audit.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Director.
 */
@Document(collection = "director")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Director implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Size(max = 50)
    @Field("director_name")
    private String directorName;

    @Field("description")
    private String description;

    @DBRef
    @Field("user")
    private User user;

    @DBRef
    @Field("manager")
    @JsonIgnoreProperties(value = { "user", "directors" }, allowSetters = true)
    private Set<Managerial> managers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Director id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDirectorName() {
        return this.directorName;
    }

    public Director directorName(String directorName) {
        this.setDirectorName(directorName);
        return this;
    }

    public void setDirectorName(String directorName) {
        this.directorName = directorName;
    }

    public String getDescription() {
        return this.description;
    }

    public Director description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Director user(User user) {
        this.setUser(user);
        return this;
    }

    public Set<Managerial> getManagers() {
        return this.managers;
    }

    public void setManagers(Set<Managerial> managerials) {
        if (this.managers != null) {
            this.managers.forEach(i -> i.setDirectors(null));
        }
        if (managerials != null) {
            managerials.forEach(i -> i.setDirectors(this));
        }
        this.managers = managerials;
    }

    public Director managers(Set<Managerial> managerials) {
        this.setManagers(managerials);
        return this;
    }

    public Director addManager(Managerial managerial) {
        this.managers.add(managerial);
        managerial.setDirectors(this);
        return this;
    }

    public Director removeManager(Managerial managerial) {
        this.managers.remove(managerial);
        managerial.setDirectors(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Director)) {
            return false;
        }
        return id != null && id.equals(((Director) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Director{" +
            "id=" + getId() +
            ", directorName='" + getDirectorName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
