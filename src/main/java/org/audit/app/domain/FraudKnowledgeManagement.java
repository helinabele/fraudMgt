package org.audit.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import javax.validation.constraints.*;
import org.audit.app.domain.enumeration.FraudTypeByIncident;
import org.audit.app.domain.enumeration.SuspectedFraudster;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A FraudKnowledgeManagement.
 */
@Document(collection = "fraud_knowledge_management")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FraudKnowledgeManagement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("report_number")
    private Integer reportNumber;

    @NotNull
    @Field("fraud_incident")
    private FraudTypeByIncident fraudIncident;

    @NotNull
    @Field("actual_incident")
    private String actualIncident;

    @NotNull
    @Field("attempt_incident")
    private String attemptIncident;

    @NotNull
    @Field("reason_for_failure")
    private String reasonForFailure;

    @NotNull
    @Field("unit")
    private String unit;

    @NotNull
    @Field("incident_date")
    private Instant incidentDate;

    @NotNull
    @Field("date_of_detection")
    private Instant dateOfDetection;

    @Field("reason_for_delay")
    private String reasonForDelay;

    @NotNull
    @Field("project_creation_date")
    private LocalDate projectCreationDate;

    @NotNull
    @Field("report_date")
    private LocalDate reportDate;

    @NotNull
    @Field("suspected_fraudster")
    private SuspectedFraudster suspectedFraudster;

    @NotNull
    @Field("financial_loss_amount")
    private Float financialLossAmount;

    @NotNull
    @Field("actual_fraud_amount")
    private String actualFraudAmount;

    @NotNull
    @Field("debit_account")
    private String debitAccount;

    @NotNull
    @Field("credit_account")
    private String creditAccount;

    @Field("techniquesand_technologies_used")
    private String techniquesandTechnologiesUsed;

    @NotNull
    @Field("cause_for_an_incident")
    private String causeForAnIncident;

    @Field("system_and_procedural_loophole")
    private String systemAndProceduralLoophole;

    @NotNull
    @Field("effect")
    private String effect;

    @NotNull
    @Field("recommendations_drawn")
    private String recommendationsDrawn;

    @Field("position_jg")
    private String positionJG;

    @Field("name_id_no")
    private String nameIdNo;

    @Field("action_involved")
    private String actionInvolved;

    @Field("ng_screener_report")
    private String ngScreenerReport;

    @Field("committee_decision")
    private String committeeDecision;

    @Field("measure_taken")
    private String measureTaken;

    @Field("fraud_amount_recovered")
    private String fraudAmountRecovered;

    @Field("fraud_amount_written_off")
    private String fraudAmountWrittenOff;

    @Field("previously_held_for_fraud_outstanding")
    private String previouslyHeldForFraudOutstanding;

    @DBRef
    @Field("employee")
    @JsonIgnoreProperties(value = { "user", "director", "manager", "teamLead", "team" }, allowSetters = true)
    private Employee employee;

    @DBRef
    @Field("fraudInvestigationReport")
    @JsonIgnoreProperties(value = { "employee", "task", "team", "findings" }, allowSetters = true)
    private FraudInvestigationReport fraudInvestigationReport;

    @DBRef
    @Field("fraudType")
    private FraudType fraudType;

    @DBRef
    @Field("bankAccount")
    private BankAccount bankAccount;

    @DBRef
    @Field("bankService")
    private BankService bankService;

    @DBRef
    @Field("internalEmployee")
    private InternalEmployee internalEmployee;

    @DBRef
    @Field("externalEmployee")
    private ExternalEmployee externalEmployee;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public FraudKnowledgeManagement id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getReportNumber() {
        return this.reportNumber;
    }

    public FraudKnowledgeManagement reportNumber(Integer reportNumber) {
        this.setReportNumber(reportNumber);
        return this;
    }

    public void setReportNumber(Integer reportNumber) {
        this.reportNumber = reportNumber;
    }

    public FraudTypeByIncident getFraudIncident() {
        return this.fraudIncident;
    }

    public FraudKnowledgeManagement fraudIncident(FraudTypeByIncident fraudIncident) {
        this.setFraudIncident(fraudIncident);
        return this;
    }

    public void setFraudIncident(FraudTypeByIncident fraudIncident) {
        this.fraudIncident = fraudIncident;
    }

    public String getActualIncident() {
        return this.actualIncident;
    }

    public FraudKnowledgeManagement actualIncident(String actualIncident) {
        this.setActualIncident(actualIncident);
        return this;
    }

    public void setActualIncident(String actualIncident) {
        this.actualIncident = actualIncident;
    }

    public String getAttemptIncident() {
        return this.attemptIncident;
    }

    public FraudKnowledgeManagement attemptIncident(String attemptIncident) {
        this.setAttemptIncident(attemptIncident);
        return this;
    }

    public void setAttemptIncident(String attemptIncident) {
        this.attemptIncident = attemptIncident;
    }

    public String getReasonForFailure() {
        return this.reasonForFailure;
    }

    public FraudKnowledgeManagement reasonForFailure(String reasonForFailure) {
        this.setReasonForFailure(reasonForFailure);
        return this;
    }

    public void setReasonForFailure(String reasonForFailure) {
        this.reasonForFailure = reasonForFailure;
    }

    public String getUnit() {
        return this.unit;
    }

    public FraudKnowledgeManagement unit(String unit) {
        this.setUnit(unit);
        return this;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Instant getIncidentDate() {
        return this.incidentDate;
    }

    public FraudKnowledgeManagement incidentDate(Instant incidentDate) {
        this.setIncidentDate(incidentDate);
        return this;
    }

    public void setIncidentDate(Instant incidentDate) {
        this.incidentDate = incidentDate;
    }

    public Instant getDateOfDetection() {
        return this.dateOfDetection;
    }

    public FraudKnowledgeManagement dateOfDetection(Instant dateOfDetection) {
        this.setDateOfDetection(dateOfDetection);
        return this;
    }

    public void setDateOfDetection(Instant dateOfDetection) {
        this.dateOfDetection = dateOfDetection;
    }

    public String getReasonForDelay() {
        return this.reasonForDelay;
    }

    public FraudKnowledgeManagement reasonForDelay(String reasonForDelay) {
        this.setReasonForDelay(reasonForDelay);
        return this;
    }

    public void setReasonForDelay(String reasonForDelay) {
        this.reasonForDelay = reasonForDelay;
    }

    public LocalDate getProjectCreationDate() {
        return this.projectCreationDate;
    }

    public FraudKnowledgeManagement projectCreationDate(LocalDate projectCreationDate) {
        this.setProjectCreationDate(projectCreationDate);
        return this;
    }

    public void setProjectCreationDate(LocalDate projectCreationDate) {
        this.projectCreationDate = projectCreationDate;
    }

    public LocalDate getReportDate() {
        return this.reportDate;
    }

    public FraudKnowledgeManagement reportDate(LocalDate reportDate) {
        this.setReportDate(reportDate);
        return this;
    }

    public void setReportDate(LocalDate reportDate) {
        this.reportDate = reportDate;
    }

    public SuspectedFraudster getSuspectedFraudster() {
        return this.suspectedFraudster;
    }

    public FraudKnowledgeManagement suspectedFraudster(SuspectedFraudster suspectedFraudster) {
        this.setSuspectedFraudster(suspectedFraudster);
        return this;
    }

    public void setSuspectedFraudster(SuspectedFraudster suspectedFraudster) {
        this.suspectedFraudster = suspectedFraudster;
    }

    public Float getFinancialLossAmount() {
        return this.financialLossAmount;
    }

    public FraudKnowledgeManagement financialLossAmount(Float financialLossAmount) {
        this.setFinancialLossAmount(financialLossAmount);
        return this;
    }

    public void setFinancialLossAmount(Float financialLossAmount) {
        this.financialLossAmount = financialLossAmount;
    }

    public String getActualFraudAmount() {
        return this.actualFraudAmount;
    }

    public FraudKnowledgeManagement actualFraudAmount(String actualFraudAmount) {
        this.setActualFraudAmount(actualFraudAmount);
        return this;
    }

    public void setActualFraudAmount(String actualFraudAmount) {
        this.actualFraudAmount = actualFraudAmount;
    }

    public String getDebitAccount() {
        return this.debitAccount;
    }

    public FraudKnowledgeManagement debitAccount(String debitAccount) {
        this.setDebitAccount(debitAccount);
        return this;
    }

    public void setDebitAccount(String debitAccount) {
        this.debitAccount = debitAccount;
    }

    public String getCreditAccount() {
        return this.creditAccount;
    }

    public FraudKnowledgeManagement creditAccount(String creditAccount) {
        this.setCreditAccount(creditAccount);
        return this;
    }

    public void setCreditAccount(String creditAccount) {
        this.creditAccount = creditAccount;
    }

    public String getTechniquesandTechnologiesUsed() {
        return this.techniquesandTechnologiesUsed;
    }

    public FraudKnowledgeManagement techniquesandTechnologiesUsed(String techniquesandTechnologiesUsed) {
        this.setTechniquesandTechnologiesUsed(techniquesandTechnologiesUsed);
        return this;
    }

    public void setTechniquesandTechnologiesUsed(String techniquesandTechnologiesUsed) {
        this.techniquesandTechnologiesUsed = techniquesandTechnologiesUsed;
    }

    public String getCauseForAnIncident() {
        return this.causeForAnIncident;
    }

    public FraudKnowledgeManagement causeForAnIncident(String causeForAnIncident) {
        this.setCauseForAnIncident(causeForAnIncident);
        return this;
    }

    public void setCauseForAnIncident(String causeForAnIncident) {
        this.causeForAnIncident = causeForAnIncident;
    }

    public String getSystemAndProceduralLoophole() {
        return this.systemAndProceduralLoophole;
    }

    public FraudKnowledgeManagement systemAndProceduralLoophole(String systemAndProceduralLoophole) {
        this.setSystemAndProceduralLoophole(systemAndProceduralLoophole);
        return this;
    }

    public void setSystemAndProceduralLoophole(String systemAndProceduralLoophole) {
        this.systemAndProceduralLoophole = systemAndProceduralLoophole;
    }

    public String getEffect() {
        return this.effect;
    }

    public FraudKnowledgeManagement effect(String effect) {
        this.setEffect(effect);
        return this;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public String getRecommendationsDrawn() {
        return this.recommendationsDrawn;
    }

    public FraudKnowledgeManagement recommendationsDrawn(String recommendationsDrawn) {
        this.setRecommendationsDrawn(recommendationsDrawn);
        return this;
    }

    public void setRecommendationsDrawn(String recommendationsDrawn) {
        this.recommendationsDrawn = recommendationsDrawn;
    }

    public String getPositionJG() {
        return this.positionJG;
    }

    public FraudKnowledgeManagement positionJG(String positionJG) {
        this.setPositionJG(positionJG);
        return this;
    }

    public void setPositionJG(String positionJG) {
        this.positionJG = positionJG;
    }

    public String getNameIdNo() {
        return this.nameIdNo;
    }

    public FraudKnowledgeManagement nameIdNo(String nameIdNo) {
        this.setNameIdNo(nameIdNo);
        return this;
    }

    public void setNameIdNo(String nameIdNo) {
        this.nameIdNo = nameIdNo;
    }

    public String getActionInvolved() {
        return this.actionInvolved;
    }

    public FraudKnowledgeManagement actionInvolved(String actionInvolved) {
        this.setActionInvolved(actionInvolved);
        return this;
    }

    public void setActionInvolved(String actionInvolved) {
        this.actionInvolved = actionInvolved;
    }

    public String getNgScreenerReport() {
        return this.ngScreenerReport;
    }

    public FraudKnowledgeManagement ngScreenerReport(String ngScreenerReport) {
        this.setNgScreenerReport(ngScreenerReport);
        return this;
    }

    public void setNgScreenerReport(String ngScreenerReport) {
        this.ngScreenerReport = ngScreenerReport;
    }

    public String getCommitteeDecision() {
        return this.committeeDecision;
    }

    public FraudKnowledgeManagement committeeDecision(String committeeDecision) {
        this.setCommitteeDecision(committeeDecision);
        return this;
    }

    public void setCommitteeDecision(String committeeDecision) {
        this.committeeDecision = committeeDecision;
    }

    public String getMeasureTaken() {
        return this.measureTaken;
    }

    public FraudKnowledgeManagement measureTaken(String measureTaken) {
        this.setMeasureTaken(measureTaken);
        return this;
    }

    public void setMeasureTaken(String measureTaken) {
        this.measureTaken = measureTaken;
    }

    public String getFraudAmountRecovered() {
        return this.fraudAmountRecovered;
    }

    public FraudKnowledgeManagement fraudAmountRecovered(String fraudAmountRecovered) {
        this.setFraudAmountRecovered(fraudAmountRecovered);
        return this;
    }

    public void setFraudAmountRecovered(String fraudAmountRecovered) {
        this.fraudAmountRecovered = fraudAmountRecovered;
    }

    public String getFraudAmountWrittenOff() {
        return this.fraudAmountWrittenOff;
    }

    public FraudKnowledgeManagement fraudAmountWrittenOff(String fraudAmountWrittenOff) {
        this.setFraudAmountWrittenOff(fraudAmountWrittenOff);
        return this;
    }

    public void setFraudAmountWrittenOff(String fraudAmountWrittenOff) {
        this.fraudAmountWrittenOff = fraudAmountWrittenOff;
    }

    public String getPreviouslyHeldForFraudOutstanding() {
        return this.previouslyHeldForFraudOutstanding;
    }

    public FraudKnowledgeManagement previouslyHeldForFraudOutstanding(String previouslyHeldForFraudOutstanding) {
        this.setPreviouslyHeldForFraudOutstanding(previouslyHeldForFraudOutstanding);
        return this;
    }

    public void setPreviouslyHeldForFraudOutstanding(String previouslyHeldForFraudOutstanding) {
        this.previouslyHeldForFraudOutstanding = previouslyHeldForFraudOutstanding;
    }

    public Employee getEmployee() {
        return this.employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public FraudKnowledgeManagement employee(Employee employee) {
        this.setEmployee(employee);
        return this;
    }

    public FraudInvestigationReport getFraudInvestigationReport() {
        return this.fraudInvestigationReport;
    }

    public void setFraudInvestigationReport(FraudInvestigationReport fraudInvestigationReport) {
        this.fraudInvestigationReport = fraudInvestigationReport;
    }

    public FraudKnowledgeManagement fraudInvestigationReport(FraudInvestigationReport fraudInvestigationReport) {
        this.setFraudInvestigationReport(fraudInvestigationReport);
        return this;
    }

    public FraudType getFraudType() {
        return this.fraudType;
    }

    public void setFraudType(FraudType fraudType) {
        this.fraudType = fraudType;
    }

    public FraudKnowledgeManagement fraudType(FraudType fraudType) {
        this.setFraudType(fraudType);
        return this;
    }

    public BankAccount getBankAccount() {
        return this.bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    public FraudKnowledgeManagement bankAccount(BankAccount bankAccount) {
        this.setBankAccount(bankAccount);
        return this;
    }

    public BankService getBankService() {
        return this.bankService;
    }

    public void setBankService(BankService bankService) {
        this.bankService = bankService;
    }

    public FraudKnowledgeManagement bankService(BankService bankService) {
        this.setBankService(bankService);
        return this;
    }

    public InternalEmployee getInternalEmployee() {
        return this.internalEmployee;
    }

    public void setInternalEmployee(InternalEmployee internalEmployee) {
        this.internalEmployee = internalEmployee;
    }

    public FraudKnowledgeManagement internalEmployee(InternalEmployee internalEmployee) {
        this.setInternalEmployee(internalEmployee);
        return this;
    }

    public ExternalEmployee getExternalEmployee() {
        return this.externalEmployee;
    }

    public void setExternalEmployee(ExternalEmployee externalEmployee) {
        this.externalEmployee = externalEmployee;
    }

    public FraudKnowledgeManagement externalEmployee(ExternalEmployee externalEmployee) {
        this.setExternalEmployee(externalEmployee);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FraudKnowledgeManagement)) {
            return false;
        }
        return id != null && id.equals(((FraudKnowledgeManagement) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FraudKnowledgeManagement{" +
            "id=" + getId() +
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
            "}";
    }
}
