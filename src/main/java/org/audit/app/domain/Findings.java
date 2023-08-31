package org.audit.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Findings.
 */
@Document(collection = "findings")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Findings implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("finding_and_analysis")
    private String findingAndAnalysis;

    @Field("finding_and_analysis_annex")
    private byte[] findingAndAnalysisAnnex;

    @Field("finding_and_analysis_annex_content_type")
    private String findingAndAnalysisAnnexContentType;

    @DBRef
    @Field("fraudInvestigationReport")
    @JsonIgnoreProperties(value = { "employee", "task", "team", "findings" }, allowSetters = true)
    private FraudInvestigationReport fraudInvestigationReport;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Findings id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFindingAndAnalysis() {
        return this.findingAndAnalysis;
    }

    public Findings findingAndAnalysis(String findingAndAnalysis) {
        this.setFindingAndAnalysis(findingAndAnalysis);
        return this;
    }

    public void setFindingAndAnalysis(String findingAndAnalysis) {
        this.findingAndAnalysis = findingAndAnalysis;
    }

    public byte[] getFindingAndAnalysisAnnex() {
        return this.findingAndAnalysisAnnex;
    }

    public Findings findingAndAnalysisAnnex(byte[] findingAndAnalysisAnnex) {
        this.setFindingAndAnalysisAnnex(findingAndAnalysisAnnex);
        return this;
    }

    public void setFindingAndAnalysisAnnex(byte[] findingAndAnalysisAnnex) {
        this.findingAndAnalysisAnnex = findingAndAnalysisAnnex;
    }

    public String getFindingAndAnalysisAnnexContentType() {
        return this.findingAndAnalysisAnnexContentType;
    }

    public Findings findingAndAnalysisAnnexContentType(String findingAndAnalysisAnnexContentType) {
        this.findingAndAnalysisAnnexContentType = findingAndAnalysisAnnexContentType;
        return this;
    }

    public void setFindingAndAnalysisAnnexContentType(String findingAndAnalysisAnnexContentType) {
        this.findingAndAnalysisAnnexContentType = findingAndAnalysisAnnexContentType;
    }

    public FraudInvestigationReport getFraudInvestigationReport() {
        return this.fraudInvestigationReport;
    }

    public void setFraudInvestigationReport(FraudInvestigationReport fraudInvestigationReport) {
        this.fraudInvestigationReport = fraudInvestigationReport;
    }

    public Findings fraudInvestigationReport(FraudInvestigationReport fraudInvestigationReport) {
        this.setFraudInvestigationReport(fraudInvestigationReport);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Findings)) {
            return false;
        }
        return id != null && id.equals(((Findings) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Findings{" +
            "id=" + getId() +
            ", findingAndAnalysis='" + getFindingAndAnalysis() + "'" +
            ", findingAndAnalysisAnnex='" + getFindingAndAnalysisAnnex() + "'" +
            ", findingAndAnalysisAnnexContentType='" + getFindingAndAnalysisAnnexContentType() + "'" +
            "}";
    }
}
