package org.audit.app.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;
import org.audit.app.domain.enumeration.FraudTypeByIncident;
import org.audit.app.domain.enumeration.SuspectedFraudster;

/**
 * A DTO for the {@link org.audit.app.domain.FraudKnowledgeManagement} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FraudKnowledgeManagementDTO implements Serializable {

    private String id;

    @NotNull
    private Integer reportNumber;

    @NotNull
    private FraudTypeByIncident fraudIncident;

    @NotNull
    private String actualIncident;

    @NotNull
    private String attemptIncident;

    @NotNull
    private String reasonForFailure;

    @NotNull
    private String unit;

    @NotNull
    private Instant incidentDate;

    @NotNull
    private Instant dateOfDetection;

    private String reasonForDelay;

    @NotNull
    private LocalDate projectCreationDate;

    @NotNull
    private LocalDate reportDate;

    @NotNull
    private SuspectedFraudster suspectedFraudster;

    @NotNull
    private Float financialLossAmount;

    @NotNull
    private String actualFraudAmount;

    @NotNull
    private String debitAccount;

    @NotNull
    private String creditAccount;

    private String techniquesandTechnologiesUsed;

    @NotNull
    private String causeForAnIncident;

    private String systemAndProceduralLoophole;

    @NotNull
    private String effect;

    @NotNull
    private String recommendationsDrawn;

    private String positionJG;

    private String nameIdNo;

    private String actionInvolved;

    private String ngScreenerReport;

    private String committeeDecision;

    private String measureTaken;

    private String fraudAmountRecovered;

    private String fraudAmountWrittenOff;

    private String previouslyHeldForFraudOutstanding;

    private EmployeeDTO employee;

    private FraudInvestigationReportDTO fraudInvestigationReport;

    private FraudTypeDTO fraudType;

    private BankAccountDTO bankAccount;

    private BankServiceDTO bankService;

    private InternalEmployeeDTO internalEmployee;

    private ExternalEmployeeDTO externalEmployee;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getReportNumber() {
        return reportNumber;
    }

    public void setReportNumber(Integer reportNumber) {
        this.reportNumber = reportNumber;
    }

    public FraudTypeByIncident getFraudIncident() {
        return fraudIncident;
    }

    public void setFraudIncident(FraudTypeByIncident fraudIncident) {
        this.fraudIncident = fraudIncident;
    }

    public String getActualIncident() {
        return actualIncident;
    }

    public void setActualIncident(String actualIncident) {
        this.actualIncident = actualIncident;
    }

    public String getAttemptIncident() {
        return attemptIncident;
    }

    public void setAttemptIncident(String attemptIncident) {
        this.attemptIncident = attemptIncident;
    }

    public String getReasonForFailure() {
        return reasonForFailure;
    }

    public void setReasonForFailure(String reasonForFailure) {
        this.reasonForFailure = reasonForFailure;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Instant getIncidentDate() {
        return incidentDate;
    }

    public void setIncidentDate(Instant incidentDate) {
        this.incidentDate = incidentDate;
    }

    public Instant getDateOfDetection() {
        return dateOfDetection;
    }

    public void setDateOfDetection(Instant dateOfDetection) {
        this.dateOfDetection = dateOfDetection;
    }

    public String getReasonForDelay() {
        return reasonForDelay;
    }

    public void setReasonForDelay(String reasonForDelay) {
        this.reasonForDelay = reasonForDelay;
    }

    public LocalDate getProjectCreationDate() {
        return projectCreationDate;
    }

    public void setProjectCreationDate(LocalDate projectCreationDate) {
        this.projectCreationDate = projectCreationDate;
    }

    public LocalDate getReportDate() {
        return reportDate;
    }

    public void setReportDate(LocalDate reportDate) {
        this.reportDate = reportDate;
    }

    public SuspectedFraudster getSuspectedFraudster() {
        return suspectedFraudster;
    }

    public void setSuspectedFraudster(SuspectedFraudster suspectedFraudster) {
        this.suspectedFraudster = suspectedFraudster;
    }

    public Float getFinancialLossAmount() {
        return financialLossAmount;
    }

    public void setFinancialLossAmount(Float financialLossAmount) {
        this.financialLossAmount = financialLossAmount;
    }

    public String getActualFraudAmount() {
        return actualFraudAmount;
    }

    public void setActualFraudAmount(String actualFraudAmount) {
        this.actualFraudAmount = actualFraudAmount;
    }

    public String getDebitAccount() {
        return debitAccount;
    }

    public void setDebitAccount(String debitAccount) {
        this.debitAccount = debitAccount;
    }

    public String getCreditAccount() {
        return creditAccount;
    }

    public void setCreditAccount(String creditAccount) {
        this.creditAccount = creditAccount;
    }

    public String getTechniquesandTechnologiesUsed() {
        return techniquesandTechnologiesUsed;
    }

    public void setTechniquesandTechnologiesUsed(String techniquesandTechnologiesUsed) {
        this.techniquesandTechnologiesUsed = techniquesandTechnologiesUsed;
    }

    public String getCauseForAnIncident() {
        return causeForAnIncident;
    }

    public void setCauseForAnIncident(String causeForAnIncident) {
        this.causeForAnIncident = causeForAnIncident;
    }

    public String getSystemAndProceduralLoophole() {
        return systemAndProceduralLoophole;
    }

    public void setSystemAndProceduralLoophole(String systemAndProceduralLoophole) {
        this.systemAndProceduralLoophole = systemAndProceduralLoophole;
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public String getRecommendationsDrawn() {
        return recommendationsDrawn;
    }

    public void setRecommendationsDrawn(String recommendationsDrawn) {
        this.recommendationsDrawn = recommendationsDrawn;
    }

    public String getPositionJG() {
        return positionJG;
    }

    public void setPositionJG(String positionJG) {
        this.positionJG = positionJG;
    }

    public String getNameIdNo() {
        return nameIdNo;
    }

    public void setNameIdNo(String nameIdNo) {
        this.nameIdNo = nameIdNo;
    }

    public String getActionInvolved() {
        return actionInvolved;
    }

    public void setActionInvolved(String actionInvolved) {
        this.actionInvolved = actionInvolved;
    }

    public String getNgScreenerReport() {
        return ngScreenerReport;
    }

    public void setNgScreenerReport(String ngScreenerReport) {
        this.ngScreenerReport = ngScreenerReport;
    }

    public String getCommitteeDecision() {
        return committeeDecision;
    }

    public void setCommitteeDecision(String committeeDecision) {
        this.committeeDecision = committeeDecision;
    }

    public String getMeasureTaken() {
        return measureTaken;
    }

    public void setMeasureTaken(String measureTaken) {
        this.measureTaken = measureTaken;
    }

    public String getFraudAmountRecovered() {
        return fraudAmountRecovered;
    }

    public void setFraudAmountRecovered(String fraudAmountRecovered) {
        this.fraudAmountRecovered = fraudAmountRecovered;
    }

    public String getFraudAmountWrittenOff() {
        return fraudAmountWrittenOff;
    }

    public void setFraudAmountWrittenOff(String fraudAmountWrittenOff) {
        this.fraudAmountWrittenOff = fraudAmountWrittenOff;
    }

    public String getPreviouslyHeldForFraudOutstanding() {
        return previouslyHeldForFraudOutstanding;
    }

    public void setPreviouslyHeldForFraudOutstanding(String previouslyHeldForFraudOutstanding) {
        this.previouslyHeldForFraudOutstanding = previouslyHeldForFraudOutstanding;
    }

    public EmployeeDTO getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeDTO employee) {
        this.employee = employee;
    }

    public FraudInvestigationReportDTO getFraudInvestigationReport() {
        return fraudInvestigationReport;
    }

    public void setFraudInvestigationReport(FraudInvestigationReportDTO fraudInvestigationReport) {
        this.fraudInvestigationReport = fraudInvestigationReport;
    }

    public FraudTypeDTO getFraudType() {
        return fraudType;
    }

    public void setFraudType(FraudTypeDTO fraudType) {
        this.fraudType = fraudType;
    }

    public BankAccountDTO getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccountDTO bankAccount) {
        this.bankAccount = bankAccount;
    }

    public BankServiceDTO getBankService() {
        return bankService;
    }

    public void setBankService(BankServiceDTO bankService) {
        this.bankService = bankService;
    }

    public InternalEmployeeDTO getInternalEmployee() {
        return internalEmployee;
    }

    public void setInternalEmployee(InternalEmployeeDTO internalEmployee) {
        this.internalEmployee = internalEmployee;
    }

    public ExternalEmployeeDTO getExternalEmployee() {
        return externalEmployee;
    }

    public void setExternalEmployee(ExternalEmployeeDTO externalEmployee) {
        this.externalEmployee = externalEmployee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FraudKnowledgeManagementDTO)) {
            return false;
        }

        FraudKnowledgeManagementDTO fraudKnowledgeManagementDTO = (FraudKnowledgeManagementDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, fraudKnowledgeManagementDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FraudKnowledgeManagementDTO{" +
            "id='" + getId() + "'" +
            ", reportNumber=" + getReportNumber() +
            ", fraudIncident='" + getFraudIncident() + "'" +
            ", actualIncident='" + getActualIncident() + "'" +
            ", attemptIncident='" + getAttemptIncident() + "'" +
            ", reasonForFailure='" + getReasonForFailure() + "'" +
            ", unit='" + getUnit() + "'" +
            ", incidentDate='" + getIncidentDate() + "'" +
            ", dateOfDetection='" + getDateOfDetection() + "'" +
            ", reasonForDelay='" + getReasonForDelay() + "'" +
            ", projectCreationDate='" + getProjectCreationDate() + "'" +
            ", reportDate='" + getReportDate() + "'" +
            ", suspectedFraudster='" + getSuspectedFraudster() + "'" +
            ", financialLossAmount=" + getFinancialLossAmount() +
            ", actualFraudAmount='" + getActualFraudAmount() + "'" +
            ", debitAccount='" + getDebitAccount() + "'" +
            ", creditAccount='" + getCreditAccount() + "'" +
            ", techniquesandTechnologiesUsed='" + getTechniquesandTechnologiesUsed() + "'" +
            ", causeForAnIncident='" + getCauseForAnIncident() + "'" +
            ", systemAndProceduralLoophole='" + getSystemAndProceduralLoophole() + "'" +
            ", effect='" + getEffect() + "'" +
            ", recommendationsDrawn='" + getRecommendationsDrawn() + "'" +
            ", positionJG='" + getPositionJG() + "'" +
            ", nameIdNo='" + getNameIdNo() + "'" +
            ", actionInvolved='" + getActionInvolved() + "'" +
            ", ngScreenerReport='" + getNgScreenerReport() + "'" +
            ", committeeDecision='" + getCommitteeDecision() + "'" +
            ", measureTaken='" + getMeasureTaken() + "'" +
            ", fraudAmountRecovered='" + getFraudAmountRecovered() + "'" +
            ", fraudAmountWrittenOff='" + getFraudAmountWrittenOff() + "'" +
            ", previouslyHeldForFraudOutstanding='" + getPreviouslyHeldForFraudOutstanding() + "'" +
            ", employee=" + getEmployee() +
            ", fraudInvestigationReport=" + getFraudInvestigationReport() +
            ", fraudType=" + getFraudType() +
            ", bankAccount=" + getBankAccount() +
            ", bankService=" + getBankService() +
            ", internalEmployee=" + getInternalEmployee() +
            ", externalEmployee=" + getExternalEmployee() +
            "}";
    }
}
