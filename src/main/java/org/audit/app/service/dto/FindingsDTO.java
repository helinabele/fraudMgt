package org.audit.app.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link org.audit.app.domain.Findings} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FindingsDTO implements Serializable {

    private String id;

    private String findingAndAnalysis;

    private byte[] findingAndAnalysisAnnex;

    private String findingAndAnalysisAnnexContentType;
    private FraudInvestigationReportDTO fraudInvestigationReport;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFindingAndAnalysis() {
        return findingAndAnalysis;
    }

    public void setFindingAndAnalysis(String findingAndAnalysis) {
        this.findingAndAnalysis = findingAndAnalysis;
    }

    public byte[] getFindingAndAnalysisAnnex() {
        return findingAndAnalysisAnnex;
    }

    public void setFindingAndAnalysisAnnex(byte[] findingAndAnalysisAnnex) {
        this.findingAndAnalysisAnnex = findingAndAnalysisAnnex;
    }

    public String getFindingAndAnalysisAnnexContentType() {
        return findingAndAnalysisAnnexContentType;
    }

    public void setFindingAndAnalysisAnnexContentType(String findingAndAnalysisAnnexContentType) {
        this.findingAndAnalysisAnnexContentType = findingAndAnalysisAnnexContentType;
    }

    public FraudInvestigationReportDTO getFraudInvestigationReport() {
        return fraudInvestigationReport;
    }

    public void setFraudInvestigationReport(FraudInvestigationReportDTO fraudInvestigationReport) {
        this.fraudInvestigationReport = fraudInvestigationReport;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FindingsDTO)) {
            return false;
        }

        FindingsDTO findingsDTO = (FindingsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, findingsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FindingsDTO{" +
            "id='" + getId() + "'" +
            ", findingAndAnalysis='" + getFindingAndAnalysis() + "'" +
            ", findingAndAnalysisAnnex='" + getFindingAndAnalysisAnnex() + "'" +
            ", fraudInvestigationReport=" + getFraudInvestigationReport() +
            "}";
    }
}
