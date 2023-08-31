package org.audit.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.audit.app.IntegrationTest;
import org.audit.app.domain.FraudKnowledgeManagement;
import org.audit.app.domain.enumeration.FraudTypeByIncident;
import org.audit.app.domain.enumeration.SuspectedFraudster;
import org.audit.app.repository.FraudKnowledgeManagementRepository;
import org.audit.app.service.FraudKnowledgeManagementService;
import org.audit.app.service.dto.FraudKnowledgeManagementDTO;
import org.audit.app.service.mapper.FraudKnowledgeManagementMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for the {@link FraudKnowledgeManagementResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class FraudKnowledgeManagementResourceIT {

    private static final Integer DEFAULT_REPORT_NUMBER = 1;
    private static final Integer UPDATED_REPORT_NUMBER = 2;

    private static final FraudTypeByIncident DEFAULT_FRAUD_INCIDENT = FraudTypeByIncident.ACTUAL;
    private static final FraudTypeByIncident UPDATED_FRAUD_INCIDENT = FraudTypeByIncident.ATTEMPT;

    private static final String DEFAULT_ACTUAL_INCIDENT = "AAAAAAAAAA";
    private static final String UPDATED_ACTUAL_INCIDENT = "BBBBBBBBBB";

    private static final String DEFAULT_ATTEMPT_INCIDENT = "AAAAAAAAAA";
    private static final String UPDATED_ATTEMPT_INCIDENT = "BBBBBBBBBB";

    private static final String DEFAULT_REASON_FOR_FAILURE = "AAAAAAAAAA";
    private static final String UPDATED_REASON_FOR_FAILURE = "BBBBBBBBBB";

    private static final String DEFAULT_UNIT = "AAAAAAAAAA";
    private static final String UPDATED_UNIT = "BBBBBBBBBB";

    private static final Instant DEFAULT_INCIDENT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_INCIDENT_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATE_OF_DETECTION = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_OF_DETECTION = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_REASON_FOR_DELAY = "AAAAAAAAAA";
    private static final String UPDATED_REASON_FOR_DELAY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_PROJECT_CREATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PROJECT_CREATION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_REPORT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_REPORT_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final SuspectedFraudster DEFAULT_SUSPECTED_FRAUDSTER = SuspectedFraudster.INTERNAL;
    private static final SuspectedFraudster UPDATED_SUSPECTED_FRAUDSTER = SuspectedFraudster.EXTERNAL;

    private static final Float DEFAULT_FINANCIAL_LOSS_AMOUNT = 1F;
    private static final Float UPDATED_FINANCIAL_LOSS_AMOUNT = 2F;

    private static final String DEFAULT_ACTUAL_FRAUD_AMOUNT = "AAAAAAAAAA";
    private static final String UPDATED_ACTUAL_FRAUD_AMOUNT = "BBBBBBBBBB";

    private static final String DEFAULT_DEBIT_ACCOUNT = "AAAAAAAAAA";
    private static final String UPDATED_DEBIT_ACCOUNT = "BBBBBBBBBB";

    private static final String DEFAULT_CREDIT_ACCOUNT = "AAAAAAAAAA";
    private static final String UPDATED_CREDIT_ACCOUNT = "BBBBBBBBBB";

    private static final String DEFAULT_TECHNIQUESAND_TECHNOLOGIES_USED = "AAAAAAAAAA";
    private static final String UPDATED_TECHNIQUESAND_TECHNOLOGIES_USED = "BBBBBBBBBB";

    private static final String DEFAULT_CAUSE_FOR_AN_INCIDENT = "AAAAAAAAAA";
    private static final String UPDATED_CAUSE_FOR_AN_INCIDENT = "BBBBBBBBBB";

    private static final String DEFAULT_SYSTEM_AND_PROCEDURAL_LOOPHOLE = "AAAAAAAAAA";
    private static final String UPDATED_SYSTEM_AND_PROCEDURAL_LOOPHOLE = "BBBBBBBBBB";

    private static final String DEFAULT_EFFECT = "AAAAAAAAAA";
    private static final String UPDATED_EFFECT = "BBBBBBBBBB";

    private static final String DEFAULT_RECOMMENDATIONS_DRAWN = "AAAAAAAAAA";
    private static final String UPDATED_RECOMMENDATIONS_DRAWN = "BBBBBBBBBB";

    private static final String DEFAULT_POSITION_JG = "AAAAAAAAAA";
    private static final String UPDATED_POSITION_JG = "BBBBBBBBBB";

    private static final String DEFAULT_NAME_ID_NO = "AAAAAAAAAA";
    private static final String UPDATED_NAME_ID_NO = "BBBBBBBBBB";

    private static final String DEFAULT_ACTION_INVOLVED = "AAAAAAAAAA";
    private static final String UPDATED_ACTION_INVOLVED = "BBBBBBBBBB";

    private static final String DEFAULT_NG_SCREENER_REPORT = "AAAAAAAAAA";
    private static final String UPDATED_NG_SCREENER_REPORT = "BBBBBBBBBB";

    private static final String DEFAULT_COMMITTEE_DECISION = "AAAAAAAAAA";
    private static final String UPDATED_COMMITTEE_DECISION = "BBBBBBBBBB";

    private static final String DEFAULT_MEASURE_TAKEN = "AAAAAAAAAA";
    private static final String UPDATED_MEASURE_TAKEN = "BBBBBBBBBB";

    private static final String DEFAULT_FRAUD_AMOUNT_RECOVERED = "AAAAAAAAAA";
    private static final String UPDATED_FRAUD_AMOUNT_RECOVERED = "BBBBBBBBBB";

    private static final String DEFAULT_FRAUD_AMOUNT_WRITTEN_OFF = "AAAAAAAAAA";
    private static final String UPDATED_FRAUD_AMOUNT_WRITTEN_OFF = "BBBBBBBBBB";

    private static final String DEFAULT_PREVIOUSLY_HELD_FOR_FRAUD_OUTSTANDING = "AAAAAAAAAA";
    private static final String UPDATED_PREVIOUSLY_HELD_FOR_FRAUD_OUTSTANDING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/fraud-knowledge-managements";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private FraudKnowledgeManagementRepository fraudKnowledgeManagementRepository;

    @Mock
    private FraudKnowledgeManagementRepository fraudKnowledgeManagementRepositoryMock;

    @Autowired
    private FraudKnowledgeManagementMapper fraudKnowledgeManagementMapper;

    @Mock
    private FraudKnowledgeManagementService fraudKnowledgeManagementServiceMock;

    @Autowired
    private MockMvc restFraudKnowledgeManagementMockMvc;

    private FraudKnowledgeManagement fraudKnowledgeManagement;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FraudKnowledgeManagement createEntity() {
        FraudKnowledgeManagement fraudKnowledgeManagement = new FraudKnowledgeManagement()
            .reportNumber(DEFAULT_REPORT_NUMBER)
            .fraudIncident(DEFAULT_FRAUD_INCIDENT)
            .actualIncident(DEFAULT_ACTUAL_INCIDENT)
            .attemptIncident(DEFAULT_ATTEMPT_INCIDENT)
            .reasonForFailure(DEFAULT_REASON_FOR_FAILURE)
            .unit(DEFAULT_UNIT)
            .incidentDate(DEFAULT_INCIDENT_DATE)
            .dateOfDetection(DEFAULT_DATE_OF_DETECTION)
            .reasonForDelay(DEFAULT_REASON_FOR_DELAY)
            .projectCreationDate(DEFAULT_PROJECT_CREATION_DATE)
            .reportDate(DEFAULT_REPORT_DATE)
            .suspectedFraudster(DEFAULT_SUSPECTED_FRAUDSTER)
            .financialLossAmount(DEFAULT_FINANCIAL_LOSS_AMOUNT)
            .actualFraudAmount(DEFAULT_ACTUAL_FRAUD_AMOUNT)
            .debitAccount(DEFAULT_DEBIT_ACCOUNT)
            .creditAccount(DEFAULT_CREDIT_ACCOUNT)
            .techniquesandTechnologiesUsed(DEFAULT_TECHNIQUESAND_TECHNOLOGIES_USED)
            .causeForAnIncident(DEFAULT_CAUSE_FOR_AN_INCIDENT)
            .systemAndProceduralLoophole(DEFAULT_SYSTEM_AND_PROCEDURAL_LOOPHOLE)
            .effect(DEFAULT_EFFECT)
            .recommendationsDrawn(DEFAULT_RECOMMENDATIONS_DRAWN)
            .positionJG(DEFAULT_POSITION_JG)
            .nameIdNo(DEFAULT_NAME_ID_NO)
            .actionInvolved(DEFAULT_ACTION_INVOLVED)
            .ngScreenerReport(DEFAULT_NG_SCREENER_REPORT)
            .committeeDecision(DEFAULT_COMMITTEE_DECISION)
            .measureTaken(DEFAULT_MEASURE_TAKEN)
            .fraudAmountRecovered(DEFAULT_FRAUD_AMOUNT_RECOVERED)
            .fraudAmountWrittenOff(DEFAULT_FRAUD_AMOUNT_WRITTEN_OFF)
            .previouslyHeldForFraudOutstanding(DEFAULT_PREVIOUSLY_HELD_FOR_FRAUD_OUTSTANDING);
        return fraudKnowledgeManagement;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FraudKnowledgeManagement createUpdatedEntity() {
        FraudKnowledgeManagement fraudKnowledgeManagement = new FraudKnowledgeManagement()
            .reportNumber(UPDATED_REPORT_NUMBER)
            .fraudIncident(UPDATED_FRAUD_INCIDENT)
            .actualIncident(UPDATED_ACTUAL_INCIDENT)
            .attemptIncident(UPDATED_ATTEMPT_INCIDENT)
            .reasonForFailure(UPDATED_REASON_FOR_FAILURE)
            .unit(UPDATED_UNIT)
            .incidentDate(UPDATED_INCIDENT_DATE)
            .dateOfDetection(UPDATED_DATE_OF_DETECTION)
            .reasonForDelay(UPDATED_REASON_FOR_DELAY)
            .projectCreationDate(UPDATED_PROJECT_CREATION_DATE)
            .reportDate(UPDATED_REPORT_DATE)
            .suspectedFraudster(UPDATED_SUSPECTED_FRAUDSTER)
            .financialLossAmount(UPDATED_FINANCIAL_LOSS_AMOUNT)
            .actualFraudAmount(UPDATED_ACTUAL_FRAUD_AMOUNT)
            .debitAccount(UPDATED_DEBIT_ACCOUNT)
            .creditAccount(UPDATED_CREDIT_ACCOUNT)
            .techniquesandTechnologiesUsed(UPDATED_TECHNIQUESAND_TECHNOLOGIES_USED)
            .causeForAnIncident(UPDATED_CAUSE_FOR_AN_INCIDENT)
            .systemAndProceduralLoophole(UPDATED_SYSTEM_AND_PROCEDURAL_LOOPHOLE)
            .effect(UPDATED_EFFECT)
            .recommendationsDrawn(UPDATED_RECOMMENDATIONS_DRAWN)
            .positionJG(UPDATED_POSITION_JG)
            .nameIdNo(UPDATED_NAME_ID_NO)
            .actionInvolved(UPDATED_ACTION_INVOLVED)
            .ngScreenerReport(UPDATED_NG_SCREENER_REPORT)
            .committeeDecision(UPDATED_COMMITTEE_DECISION)
            .measureTaken(UPDATED_MEASURE_TAKEN)
            .fraudAmountRecovered(UPDATED_FRAUD_AMOUNT_RECOVERED)
            .fraudAmountWrittenOff(UPDATED_FRAUD_AMOUNT_WRITTEN_OFF)
            .previouslyHeldForFraudOutstanding(UPDATED_PREVIOUSLY_HELD_FOR_FRAUD_OUTSTANDING);
        return fraudKnowledgeManagement;
    }

    @BeforeEach
    public void initTest() {
        fraudKnowledgeManagementRepository.deleteAll();
        fraudKnowledgeManagement = createEntity();
    }

    @Test
    void createFraudKnowledgeManagement() throws Exception {
        int databaseSizeBeforeCreate = fraudKnowledgeManagementRepository.findAll().size();
        // Create the FraudKnowledgeManagement
        FraudKnowledgeManagementDTO fraudKnowledgeManagementDTO = fraudKnowledgeManagementMapper.toDto(fraudKnowledgeManagement);
        restFraudKnowledgeManagementMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fraudKnowledgeManagementDTO))
            )
            .andExpect(status().isCreated());

        // Validate the FraudKnowledgeManagement in the database
        List<FraudKnowledgeManagement> fraudKnowledgeManagementList = fraudKnowledgeManagementRepository.findAll();
        assertThat(fraudKnowledgeManagementList).hasSize(databaseSizeBeforeCreate + 1);
        FraudKnowledgeManagement testFraudKnowledgeManagement = fraudKnowledgeManagementList.get(fraudKnowledgeManagementList.size() - 1);
        assertThat(testFraudKnowledgeManagement.getReportNumber()).isEqualTo(DEFAULT_REPORT_NUMBER);
        assertThat(testFraudKnowledgeManagement.getFraudIncident()).isEqualTo(DEFAULT_FRAUD_INCIDENT);
        assertThat(testFraudKnowledgeManagement.getActualIncident()).isEqualTo(DEFAULT_ACTUAL_INCIDENT);
        assertThat(testFraudKnowledgeManagement.getAttemptIncident()).isEqualTo(DEFAULT_ATTEMPT_INCIDENT);
        assertThat(testFraudKnowledgeManagement.getReasonForFailure()).isEqualTo(DEFAULT_REASON_FOR_FAILURE);
        assertThat(testFraudKnowledgeManagement.getUnit()).isEqualTo(DEFAULT_UNIT);
        assertThat(testFraudKnowledgeManagement.getIncidentDate()).isEqualTo(DEFAULT_INCIDENT_DATE);
        assertThat(testFraudKnowledgeManagement.getDateOfDetection()).isEqualTo(DEFAULT_DATE_OF_DETECTION);
        assertThat(testFraudKnowledgeManagement.getReasonForDelay()).isEqualTo(DEFAULT_REASON_FOR_DELAY);
        assertThat(testFraudKnowledgeManagement.getProjectCreationDate()).isEqualTo(DEFAULT_PROJECT_CREATION_DATE);
        assertThat(testFraudKnowledgeManagement.getReportDate()).isEqualTo(DEFAULT_REPORT_DATE);
        assertThat(testFraudKnowledgeManagement.getSuspectedFraudster()).isEqualTo(DEFAULT_SUSPECTED_FRAUDSTER);
        assertThat(testFraudKnowledgeManagement.getFinancialLossAmount()).isEqualTo(DEFAULT_FINANCIAL_LOSS_AMOUNT);
        assertThat(testFraudKnowledgeManagement.getActualFraudAmount()).isEqualTo(DEFAULT_ACTUAL_FRAUD_AMOUNT);
        assertThat(testFraudKnowledgeManagement.getDebitAccount()).isEqualTo(DEFAULT_DEBIT_ACCOUNT);
        assertThat(testFraudKnowledgeManagement.getCreditAccount()).isEqualTo(DEFAULT_CREDIT_ACCOUNT);
        assertThat(testFraudKnowledgeManagement.getTechniquesandTechnologiesUsed()).isEqualTo(DEFAULT_TECHNIQUESAND_TECHNOLOGIES_USED);
        assertThat(testFraudKnowledgeManagement.getCauseForAnIncident()).isEqualTo(DEFAULT_CAUSE_FOR_AN_INCIDENT);
        assertThat(testFraudKnowledgeManagement.getSystemAndProceduralLoophole()).isEqualTo(DEFAULT_SYSTEM_AND_PROCEDURAL_LOOPHOLE);
        assertThat(testFraudKnowledgeManagement.getEffect()).isEqualTo(DEFAULT_EFFECT);
        assertThat(testFraudKnowledgeManagement.getRecommendationsDrawn()).isEqualTo(DEFAULT_RECOMMENDATIONS_DRAWN);
        assertThat(testFraudKnowledgeManagement.getPositionJG()).isEqualTo(DEFAULT_POSITION_JG);
        assertThat(testFraudKnowledgeManagement.getNameIdNo()).isEqualTo(DEFAULT_NAME_ID_NO);
        assertThat(testFraudKnowledgeManagement.getActionInvolved()).isEqualTo(DEFAULT_ACTION_INVOLVED);
        assertThat(testFraudKnowledgeManagement.getNgScreenerReport()).isEqualTo(DEFAULT_NG_SCREENER_REPORT);
        assertThat(testFraudKnowledgeManagement.getCommitteeDecision()).isEqualTo(DEFAULT_COMMITTEE_DECISION);
        assertThat(testFraudKnowledgeManagement.getMeasureTaken()).isEqualTo(DEFAULT_MEASURE_TAKEN);
        assertThat(testFraudKnowledgeManagement.getFraudAmountRecovered()).isEqualTo(DEFAULT_FRAUD_AMOUNT_RECOVERED);
        assertThat(testFraudKnowledgeManagement.getFraudAmountWrittenOff()).isEqualTo(DEFAULT_FRAUD_AMOUNT_WRITTEN_OFF);
        assertThat(testFraudKnowledgeManagement.getPreviouslyHeldForFraudOutstanding())
            .isEqualTo(DEFAULT_PREVIOUSLY_HELD_FOR_FRAUD_OUTSTANDING);
    }

    @Test
    void createFraudKnowledgeManagementWithExistingId() throws Exception {
        // Create the FraudKnowledgeManagement with an existing ID
        fraudKnowledgeManagement.setId("existing_id");
        FraudKnowledgeManagementDTO fraudKnowledgeManagementDTO = fraudKnowledgeManagementMapper.toDto(fraudKnowledgeManagement);

        int databaseSizeBeforeCreate = fraudKnowledgeManagementRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFraudKnowledgeManagementMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fraudKnowledgeManagementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FraudKnowledgeManagement in the database
        List<FraudKnowledgeManagement> fraudKnowledgeManagementList = fraudKnowledgeManagementRepository.findAll();
        assertThat(fraudKnowledgeManagementList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkReportNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = fraudKnowledgeManagementRepository.findAll().size();
        // set the field null
        fraudKnowledgeManagement.setReportNumber(null);

        // Create the FraudKnowledgeManagement, which fails.
        FraudKnowledgeManagementDTO fraudKnowledgeManagementDTO = fraudKnowledgeManagementMapper.toDto(fraudKnowledgeManagement);

        restFraudKnowledgeManagementMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fraudKnowledgeManagementDTO))
            )
            .andExpect(status().isBadRequest());

        List<FraudKnowledgeManagement> fraudKnowledgeManagementList = fraudKnowledgeManagementRepository.findAll();
        assertThat(fraudKnowledgeManagementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkFraudIncidentIsRequired() throws Exception {
        int databaseSizeBeforeTest = fraudKnowledgeManagementRepository.findAll().size();
        // set the field null
        fraudKnowledgeManagement.setFraudIncident(null);

        // Create the FraudKnowledgeManagement, which fails.
        FraudKnowledgeManagementDTO fraudKnowledgeManagementDTO = fraudKnowledgeManagementMapper.toDto(fraudKnowledgeManagement);

        restFraudKnowledgeManagementMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fraudKnowledgeManagementDTO))
            )
            .andExpect(status().isBadRequest());

        List<FraudKnowledgeManagement> fraudKnowledgeManagementList = fraudKnowledgeManagementRepository.findAll();
        assertThat(fraudKnowledgeManagementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkActualIncidentIsRequired() throws Exception {
        int databaseSizeBeforeTest = fraudKnowledgeManagementRepository.findAll().size();
        // set the field null
        fraudKnowledgeManagement.setActualIncident(null);

        // Create the FraudKnowledgeManagement, which fails.
        FraudKnowledgeManagementDTO fraudKnowledgeManagementDTO = fraudKnowledgeManagementMapper.toDto(fraudKnowledgeManagement);

        restFraudKnowledgeManagementMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fraudKnowledgeManagementDTO))
            )
            .andExpect(status().isBadRequest());

        List<FraudKnowledgeManagement> fraudKnowledgeManagementList = fraudKnowledgeManagementRepository.findAll();
        assertThat(fraudKnowledgeManagementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkAttemptIncidentIsRequired() throws Exception {
        int databaseSizeBeforeTest = fraudKnowledgeManagementRepository.findAll().size();
        // set the field null
        fraudKnowledgeManagement.setAttemptIncident(null);

        // Create the FraudKnowledgeManagement, which fails.
        FraudKnowledgeManagementDTO fraudKnowledgeManagementDTO = fraudKnowledgeManagementMapper.toDto(fraudKnowledgeManagement);

        restFraudKnowledgeManagementMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fraudKnowledgeManagementDTO))
            )
            .andExpect(status().isBadRequest());

        List<FraudKnowledgeManagement> fraudKnowledgeManagementList = fraudKnowledgeManagementRepository.findAll();
        assertThat(fraudKnowledgeManagementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkReasonForFailureIsRequired() throws Exception {
        int databaseSizeBeforeTest = fraudKnowledgeManagementRepository.findAll().size();
        // set the field null
        fraudKnowledgeManagement.setReasonForFailure(null);

        // Create the FraudKnowledgeManagement, which fails.
        FraudKnowledgeManagementDTO fraudKnowledgeManagementDTO = fraudKnowledgeManagementMapper.toDto(fraudKnowledgeManagement);

        restFraudKnowledgeManagementMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fraudKnowledgeManagementDTO))
            )
            .andExpect(status().isBadRequest());

        List<FraudKnowledgeManagement> fraudKnowledgeManagementList = fraudKnowledgeManagementRepository.findAll();
        assertThat(fraudKnowledgeManagementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkUnitIsRequired() throws Exception {
        int databaseSizeBeforeTest = fraudKnowledgeManagementRepository.findAll().size();
        // set the field null
        fraudKnowledgeManagement.setUnit(null);

        // Create the FraudKnowledgeManagement, which fails.
        FraudKnowledgeManagementDTO fraudKnowledgeManagementDTO = fraudKnowledgeManagementMapper.toDto(fraudKnowledgeManagement);

        restFraudKnowledgeManagementMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fraudKnowledgeManagementDTO))
            )
            .andExpect(status().isBadRequest());

        List<FraudKnowledgeManagement> fraudKnowledgeManagementList = fraudKnowledgeManagementRepository.findAll();
        assertThat(fraudKnowledgeManagementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkIncidentDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = fraudKnowledgeManagementRepository.findAll().size();
        // set the field null
        fraudKnowledgeManagement.setIncidentDate(null);

        // Create the FraudKnowledgeManagement, which fails.
        FraudKnowledgeManagementDTO fraudKnowledgeManagementDTO = fraudKnowledgeManagementMapper.toDto(fraudKnowledgeManagement);

        restFraudKnowledgeManagementMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fraudKnowledgeManagementDTO))
            )
            .andExpect(status().isBadRequest());

        List<FraudKnowledgeManagement> fraudKnowledgeManagementList = fraudKnowledgeManagementRepository.findAll();
        assertThat(fraudKnowledgeManagementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkDateOfDetectionIsRequired() throws Exception {
        int databaseSizeBeforeTest = fraudKnowledgeManagementRepository.findAll().size();
        // set the field null
        fraudKnowledgeManagement.setDateOfDetection(null);

        // Create the FraudKnowledgeManagement, which fails.
        FraudKnowledgeManagementDTO fraudKnowledgeManagementDTO = fraudKnowledgeManagementMapper.toDto(fraudKnowledgeManagement);

        restFraudKnowledgeManagementMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fraudKnowledgeManagementDTO))
            )
            .andExpect(status().isBadRequest());

        List<FraudKnowledgeManagement> fraudKnowledgeManagementList = fraudKnowledgeManagementRepository.findAll();
        assertThat(fraudKnowledgeManagementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkProjectCreationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = fraudKnowledgeManagementRepository.findAll().size();
        // set the field null
        fraudKnowledgeManagement.setProjectCreationDate(null);

        // Create the FraudKnowledgeManagement, which fails.
        FraudKnowledgeManagementDTO fraudKnowledgeManagementDTO = fraudKnowledgeManagementMapper.toDto(fraudKnowledgeManagement);

        restFraudKnowledgeManagementMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fraudKnowledgeManagementDTO))
            )
            .andExpect(status().isBadRequest());

        List<FraudKnowledgeManagement> fraudKnowledgeManagementList = fraudKnowledgeManagementRepository.findAll();
        assertThat(fraudKnowledgeManagementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkReportDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = fraudKnowledgeManagementRepository.findAll().size();
        // set the field null
        fraudKnowledgeManagement.setReportDate(null);

        // Create the FraudKnowledgeManagement, which fails.
        FraudKnowledgeManagementDTO fraudKnowledgeManagementDTO = fraudKnowledgeManagementMapper.toDto(fraudKnowledgeManagement);

        restFraudKnowledgeManagementMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fraudKnowledgeManagementDTO))
            )
            .andExpect(status().isBadRequest());

        List<FraudKnowledgeManagement> fraudKnowledgeManagementList = fraudKnowledgeManagementRepository.findAll();
        assertThat(fraudKnowledgeManagementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkSuspectedFraudsterIsRequired() throws Exception {
        int databaseSizeBeforeTest = fraudKnowledgeManagementRepository.findAll().size();
        // set the field null
        fraudKnowledgeManagement.setSuspectedFraudster(null);

        // Create the FraudKnowledgeManagement, which fails.
        FraudKnowledgeManagementDTO fraudKnowledgeManagementDTO = fraudKnowledgeManagementMapper.toDto(fraudKnowledgeManagement);

        restFraudKnowledgeManagementMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fraudKnowledgeManagementDTO))
            )
            .andExpect(status().isBadRequest());

        List<FraudKnowledgeManagement> fraudKnowledgeManagementList = fraudKnowledgeManagementRepository.findAll();
        assertThat(fraudKnowledgeManagementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkFinancialLossAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = fraudKnowledgeManagementRepository.findAll().size();
        // set the field null
        fraudKnowledgeManagement.setFinancialLossAmount(null);

        // Create the FraudKnowledgeManagement, which fails.
        FraudKnowledgeManagementDTO fraudKnowledgeManagementDTO = fraudKnowledgeManagementMapper.toDto(fraudKnowledgeManagement);

        restFraudKnowledgeManagementMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fraudKnowledgeManagementDTO))
            )
            .andExpect(status().isBadRequest());

        List<FraudKnowledgeManagement> fraudKnowledgeManagementList = fraudKnowledgeManagementRepository.findAll();
        assertThat(fraudKnowledgeManagementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkActualFraudAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = fraudKnowledgeManagementRepository.findAll().size();
        // set the field null
        fraudKnowledgeManagement.setActualFraudAmount(null);

        // Create the FraudKnowledgeManagement, which fails.
        FraudKnowledgeManagementDTO fraudKnowledgeManagementDTO = fraudKnowledgeManagementMapper.toDto(fraudKnowledgeManagement);

        restFraudKnowledgeManagementMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fraudKnowledgeManagementDTO))
            )
            .andExpect(status().isBadRequest());

        List<FraudKnowledgeManagement> fraudKnowledgeManagementList = fraudKnowledgeManagementRepository.findAll();
        assertThat(fraudKnowledgeManagementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkDebitAccountIsRequired() throws Exception {
        int databaseSizeBeforeTest = fraudKnowledgeManagementRepository.findAll().size();
        // set the field null
        fraudKnowledgeManagement.setDebitAccount(null);

        // Create the FraudKnowledgeManagement, which fails.
        FraudKnowledgeManagementDTO fraudKnowledgeManagementDTO = fraudKnowledgeManagementMapper.toDto(fraudKnowledgeManagement);

        restFraudKnowledgeManagementMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fraudKnowledgeManagementDTO))
            )
            .andExpect(status().isBadRequest());

        List<FraudKnowledgeManagement> fraudKnowledgeManagementList = fraudKnowledgeManagementRepository.findAll();
        assertThat(fraudKnowledgeManagementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkCreditAccountIsRequired() throws Exception {
        int databaseSizeBeforeTest = fraudKnowledgeManagementRepository.findAll().size();
        // set the field null
        fraudKnowledgeManagement.setCreditAccount(null);

        // Create the FraudKnowledgeManagement, which fails.
        FraudKnowledgeManagementDTO fraudKnowledgeManagementDTO = fraudKnowledgeManagementMapper.toDto(fraudKnowledgeManagement);

        restFraudKnowledgeManagementMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fraudKnowledgeManagementDTO))
            )
            .andExpect(status().isBadRequest());

        List<FraudKnowledgeManagement> fraudKnowledgeManagementList = fraudKnowledgeManagementRepository.findAll();
        assertThat(fraudKnowledgeManagementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkCauseForAnIncidentIsRequired() throws Exception {
        int databaseSizeBeforeTest = fraudKnowledgeManagementRepository.findAll().size();
        // set the field null
        fraudKnowledgeManagement.setCauseForAnIncident(null);

        // Create the FraudKnowledgeManagement, which fails.
        FraudKnowledgeManagementDTO fraudKnowledgeManagementDTO = fraudKnowledgeManagementMapper.toDto(fraudKnowledgeManagement);

        restFraudKnowledgeManagementMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fraudKnowledgeManagementDTO))
            )
            .andExpect(status().isBadRequest());

        List<FraudKnowledgeManagement> fraudKnowledgeManagementList = fraudKnowledgeManagementRepository.findAll();
        assertThat(fraudKnowledgeManagementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkEffectIsRequired() throws Exception {
        int databaseSizeBeforeTest = fraudKnowledgeManagementRepository.findAll().size();
        // set the field null
        fraudKnowledgeManagement.setEffect(null);

        // Create the FraudKnowledgeManagement, which fails.
        FraudKnowledgeManagementDTO fraudKnowledgeManagementDTO = fraudKnowledgeManagementMapper.toDto(fraudKnowledgeManagement);

        restFraudKnowledgeManagementMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fraudKnowledgeManagementDTO))
            )
            .andExpect(status().isBadRequest());

        List<FraudKnowledgeManagement> fraudKnowledgeManagementList = fraudKnowledgeManagementRepository.findAll();
        assertThat(fraudKnowledgeManagementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkRecommendationsDrawnIsRequired() throws Exception {
        int databaseSizeBeforeTest = fraudKnowledgeManagementRepository.findAll().size();
        // set the field null
        fraudKnowledgeManagement.setRecommendationsDrawn(null);

        // Create the FraudKnowledgeManagement, which fails.
        FraudKnowledgeManagementDTO fraudKnowledgeManagementDTO = fraudKnowledgeManagementMapper.toDto(fraudKnowledgeManagement);

        restFraudKnowledgeManagementMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fraudKnowledgeManagementDTO))
            )
            .andExpect(status().isBadRequest());

        List<FraudKnowledgeManagement> fraudKnowledgeManagementList = fraudKnowledgeManagementRepository.findAll();
        assertThat(fraudKnowledgeManagementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllFraudKnowledgeManagements() throws Exception {
        // Initialize the database
        fraudKnowledgeManagementRepository.save(fraudKnowledgeManagement);

        // Get all the fraudKnowledgeManagementList
        restFraudKnowledgeManagementMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fraudKnowledgeManagement.getId())))
            .andExpect(jsonPath("$.[*].reportNumber").value(hasItem(DEFAULT_REPORT_NUMBER)))
            .andExpect(jsonPath("$.[*].fraudIncident").value(hasItem(DEFAULT_FRAUD_INCIDENT.toString())))
            .andExpect(jsonPath("$.[*].actualIncident").value(hasItem(DEFAULT_ACTUAL_INCIDENT)))
            .andExpect(jsonPath("$.[*].attemptIncident").value(hasItem(DEFAULT_ATTEMPT_INCIDENT)))
            .andExpect(jsonPath("$.[*].reasonForFailure").value(hasItem(DEFAULT_REASON_FOR_FAILURE)))
            .andExpect(jsonPath("$.[*].unit").value(hasItem(DEFAULT_UNIT)))
            .andExpect(jsonPath("$.[*].incidentDate").value(hasItem(DEFAULT_INCIDENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].dateOfDetection").value(hasItem(DEFAULT_DATE_OF_DETECTION.toString())))
            .andExpect(jsonPath("$.[*].reasonForDelay").value(hasItem(DEFAULT_REASON_FOR_DELAY)))
            .andExpect(jsonPath("$.[*].projectCreationDate").value(hasItem(DEFAULT_PROJECT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].reportDate").value(hasItem(DEFAULT_REPORT_DATE.toString())))
            .andExpect(jsonPath("$.[*].suspectedFraudster").value(hasItem(DEFAULT_SUSPECTED_FRAUDSTER.toString())))
            .andExpect(jsonPath("$.[*].financialLossAmount").value(hasItem(DEFAULT_FINANCIAL_LOSS_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].actualFraudAmount").value(hasItem(DEFAULT_ACTUAL_FRAUD_AMOUNT)))
            .andExpect(jsonPath("$.[*].debitAccount").value(hasItem(DEFAULT_DEBIT_ACCOUNT)))
            .andExpect(jsonPath("$.[*].creditAccount").value(hasItem(DEFAULT_CREDIT_ACCOUNT)))
            .andExpect(jsonPath("$.[*].techniquesandTechnologiesUsed").value(hasItem(DEFAULT_TECHNIQUESAND_TECHNOLOGIES_USED)))
            .andExpect(jsonPath("$.[*].causeForAnIncident").value(hasItem(DEFAULT_CAUSE_FOR_AN_INCIDENT)))
            .andExpect(jsonPath("$.[*].systemAndProceduralLoophole").value(hasItem(DEFAULT_SYSTEM_AND_PROCEDURAL_LOOPHOLE)))
            .andExpect(jsonPath("$.[*].effect").value(hasItem(DEFAULT_EFFECT)))
            .andExpect(jsonPath("$.[*].recommendationsDrawn").value(hasItem(DEFAULT_RECOMMENDATIONS_DRAWN)))
            .andExpect(jsonPath("$.[*].positionJG").value(hasItem(DEFAULT_POSITION_JG)))
            .andExpect(jsonPath("$.[*].nameIdNo").value(hasItem(DEFAULT_NAME_ID_NO)))
            .andExpect(jsonPath("$.[*].actionInvolved").value(hasItem(DEFAULT_ACTION_INVOLVED)))
            .andExpect(jsonPath("$.[*].ngScreenerReport").value(hasItem(DEFAULT_NG_SCREENER_REPORT)))
            .andExpect(jsonPath("$.[*].committeeDecision").value(hasItem(DEFAULT_COMMITTEE_DECISION)))
            .andExpect(jsonPath("$.[*].measureTaken").value(hasItem(DEFAULT_MEASURE_TAKEN)))
            .andExpect(jsonPath("$.[*].fraudAmountRecovered").value(hasItem(DEFAULT_FRAUD_AMOUNT_RECOVERED)))
            .andExpect(jsonPath("$.[*].fraudAmountWrittenOff").value(hasItem(DEFAULT_FRAUD_AMOUNT_WRITTEN_OFF)))
            .andExpect(jsonPath("$.[*].previouslyHeldForFraudOutstanding").value(hasItem(DEFAULT_PREVIOUSLY_HELD_FOR_FRAUD_OUTSTANDING)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllFraudKnowledgeManagementsWithEagerRelationshipsIsEnabled() throws Exception {
        when(fraudKnowledgeManagementServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restFraudKnowledgeManagementMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(fraudKnowledgeManagementServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllFraudKnowledgeManagementsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(fraudKnowledgeManagementServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restFraudKnowledgeManagementMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(fraudKnowledgeManagementRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void getFraudKnowledgeManagement() throws Exception {
        // Initialize the database
        fraudKnowledgeManagementRepository.save(fraudKnowledgeManagement);

        // Get the fraudKnowledgeManagement
        restFraudKnowledgeManagementMockMvc
            .perform(get(ENTITY_API_URL_ID, fraudKnowledgeManagement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fraudKnowledgeManagement.getId()))
            .andExpect(jsonPath("$.reportNumber").value(DEFAULT_REPORT_NUMBER))
            .andExpect(jsonPath("$.fraudIncident").value(DEFAULT_FRAUD_INCIDENT.toString()))
            .andExpect(jsonPath("$.actualIncident").value(DEFAULT_ACTUAL_INCIDENT))
            .andExpect(jsonPath("$.attemptIncident").value(DEFAULT_ATTEMPT_INCIDENT))
            .andExpect(jsonPath("$.reasonForFailure").value(DEFAULT_REASON_FOR_FAILURE))
            .andExpect(jsonPath("$.unit").value(DEFAULT_UNIT))
            .andExpect(jsonPath("$.incidentDate").value(DEFAULT_INCIDENT_DATE.toString()))
            .andExpect(jsonPath("$.dateOfDetection").value(DEFAULT_DATE_OF_DETECTION.toString()))
            .andExpect(jsonPath("$.reasonForDelay").value(DEFAULT_REASON_FOR_DELAY))
            .andExpect(jsonPath("$.projectCreationDate").value(DEFAULT_PROJECT_CREATION_DATE.toString()))
            .andExpect(jsonPath("$.reportDate").value(DEFAULT_REPORT_DATE.toString()))
            .andExpect(jsonPath("$.suspectedFraudster").value(DEFAULT_SUSPECTED_FRAUDSTER.toString()))
            .andExpect(jsonPath("$.financialLossAmount").value(DEFAULT_FINANCIAL_LOSS_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.actualFraudAmount").value(DEFAULT_ACTUAL_FRAUD_AMOUNT))
            .andExpect(jsonPath("$.debitAccount").value(DEFAULT_DEBIT_ACCOUNT))
            .andExpect(jsonPath("$.creditAccount").value(DEFAULT_CREDIT_ACCOUNT))
            .andExpect(jsonPath("$.techniquesandTechnologiesUsed").value(DEFAULT_TECHNIQUESAND_TECHNOLOGIES_USED))
            .andExpect(jsonPath("$.causeForAnIncident").value(DEFAULT_CAUSE_FOR_AN_INCIDENT))
            .andExpect(jsonPath("$.systemAndProceduralLoophole").value(DEFAULT_SYSTEM_AND_PROCEDURAL_LOOPHOLE))
            .andExpect(jsonPath("$.effect").value(DEFAULT_EFFECT))
            .andExpect(jsonPath("$.recommendationsDrawn").value(DEFAULT_RECOMMENDATIONS_DRAWN))
            .andExpect(jsonPath("$.positionJG").value(DEFAULT_POSITION_JG))
            .andExpect(jsonPath("$.nameIdNo").value(DEFAULT_NAME_ID_NO))
            .andExpect(jsonPath("$.actionInvolved").value(DEFAULT_ACTION_INVOLVED))
            .andExpect(jsonPath("$.ngScreenerReport").value(DEFAULT_NG_SCREENER_REPORT))
            .andExpect(jsonPath("$.committeeDecision").value(DEFAULT_COMMITTEE_DECISION))
            .andExpect(jsonPath("$.measureTaken").value(DEFAULT_MEASURE_TAKEN))
            .andExpect(jsonPath("$.fraudAmountRecovered").value(DEFAULT_FRAUD_AMOUNT_RECOVERED))
            .andExpect(jsonPath("$.fraudAmountWrittenOff").value(DEFAULT_FRAUD_AMOUNT_WRITTEN_OFF))
            .andExpect(jsonPath("$.previouslyHeldForFraudOutstanding").value(DEFAULT_PREVIOUSLY_HELD_FOR_FRAUD_OUTSTANDING));
    }

    @Test
    void getNonExistingFraudKnowledgeManagement() throws Exception {
        // Get the fraudKnowledgeManagement
        restFraudKnowledgeManagementMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingFraudKnowledgeManagement() throws Exception {
        // Initialize the database
        fraudKnowledgeManagementRepository.save(fraudKnowledgeManagement);

        int databaseSizeBeforeUpdate = fraudKnowledgeManagementRepository.findAll().size();

        // Update the fraudKnowledgeManagement
        FraudKnowledgeManagement updatedFraudKnowledgeManagement = fraudKnowledgeManagementRepository
            .findById(fraudKnowledgeManagement.getId())
            .get();
        updatedFraudKnowledgeManagement
            .reportNumber(UPDATED_REPORT_NUMBER)
            .fraudIncident(UPDATED_FRAUD_INCIDENT)
            .actualIncident(UPDATED_ACTUAL_INCIDENT)
            .attemptIncident(UPDATED_ATTEMPT_INCIDENT)
            .reasonForFailure(UPDATED_REASON_FOR_FAILURE)
            .unit(UPDATED_UNIT)
            .incidentDate(UPDATED_INCIDENT_DATE)
            .dateOfDetection(UPDATED_DATE_OF_DETECTION)
            .reasonForDelay(UPDATED_REASON_FOR_DELAY)
            .projectCreationDate(UPDATED_PROJECT_CREATION_DATE)
            .reportDate(UPDATED_REPORT_DATE)
            .suspectedFraudster(UPDATED_SUSPECTED_FRAUDSTER)
            .financialLossAmount(UPDATED_FINANCIAL_LOSS_AMOUNT)
            .actualFraudAmount(UPDATED_ACTUAL_FRAUD_AMOUNT)
            .debitAccount(UPDATED_DEBIT_ACCOUNT)
            .creditAccount(UPDATED_CREDIT_ACCOUNT)
            .techniquesandTechnologiesUsed(UPDATED_TECHNIQUESAND_TECHNOLOGIES_USED)
            .causeForAnIncident(UPDATED_CAUSE_FOR_AN_INCIDENT)
            .systemAndProceduralLoophole(UPDATED_SYSTEM_AND_PROCEDURAL_LOOPHOLE)
            .effect(UPDATED_EFFECT)
            .recommendationsDrawn(UPDATED_RECOMMENDATIONS_DRAWN)
            .positionJG(UPDATED_POSITION_JG)
            .nameIdNo(UPDATED_NAME_ID_NO)
            .actionInvolved(UPDATED_ACTION_INVOLVED)
            .ngScreenerReport(UPDATED_NG_SCREENER_REPORT)
            .committeeDecision(UPDATED_COMMITTEE_DECISION)
            .measureTaken(UPDATED_MEASURE_TAKEN)
            .fraudAmountRecovered(UPDATED_FRAUD_AMOUNT_RECOVERED)
            .fraudAmountWrittenOff(UPDATED_FRAUD_AMOUNT_WRITTEN_OFF)
            .previouslyHeldForFraudOutstanding(UPDATED_PREVIOUSLY_HELD_FOR_FRAUD_OUTSTANDING);
        FraudKnowledgeManagementDTO fraudKnowledgeManagementDTO = fraudKnowledgeManagementMapper.toDto(updatedFraudKnowledgeManagement);

        restFraudKnowledgeManagementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fraudKnowledgeManagementDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fraudKnowledgeManagementDTO))
            )
            .andExpect(status().isOk());

        // Validate the FraudKnowledgeManagement in the database
        List<FraudKnowledgeManagement> fraudKnowledgeManagementList = fraudKnowledgeManagementRepository.findAll();
        assertThat(fraudKnowledgeManagementList).hasSize(databaseSizeBeforeUpdate);
        FraudKnowledgeManagement testFraudKnowledgeManagement = fraudKnowledgeManagementList.get(fraudKnowledgeManagementList.size() - 1);
        assertThat(testFraudKnowledgeManagement.getReportNumber()).isEqualTo(UPDATED_REPORT_NUMBER);
        assertThat(testFraudKnowledgeManagement.getFraudIncident()).isEqualTo(UPDATED_FRAUD_INCIDENT);
        assertThat(testFraudKnowledgeManagement.getActualIncident()).isEqualTo(UPDATED_ACTUAL_INCIDENT);
        assertThat(testFraudKnowledgeManagement.getAttemptIncident()).isEqualTo(UPDATED_ATTEMPT_INCIDENT);
        assertThat(testFraudKnowledgeManagement.getReasonForFailure()).isEqualTo(UPDATED_REASON_FOR_FAILURE);
        assertThat(testFraudKnowledgeManagement.getUnit()).isEqualTo(UPDATED_UNIT);
        assertThat(testFraudKnowledgeManagement.getIncidentDate()).isEqualTo(UPDATED_INCIDENT_DATE);
        assertThat(testFraudKnowledgeManagement.getDateOfDetection()).isEqualTo(UPDATED_DATE_OF_DETECTION);
        assertThat(testFraudKnowledgeManagement.getReasonForDelay()).isEqualTo(UPDATED_REASON_FOR_DELAY);
        assertThat(testFraudKnowledgeManagement.getProjectCreationDate()).isEqualTo(UPDATED_PROJECT_CREATION_DATE);
        assertThat(testFraudKnowledgeManagement.getReportDate()).isEqualTo(UPDATED_REPORT_DATE);
        assertThat(testFraudKnowledgeManagement.getSuspectedFraudster()).isEqualTo(UPDATED_SUSPECTED_FRAUDSTER);
        assertThat(testFraudKnowledgeManagement.getFinancialLossAmount()).isEqualTo(UPDATED_FINANCIAL_LOSS_AMOUNT);
        assertThat(testFraudKnowledgeManagement.getActualFraudAmount()).isEqualTo(UPDATED_ACTUAL_FRAUD_AMOUNT);
        assertThat(testFraudKnowledgeManagement.getDebitAccount()).isEqualTo(UPDATED_DEBIT_ACCOUNT);
        assertThat(testFraudKnowledgeManagement.getCreditAccount()).isEqualTo(UPDATED_CREDIT_ACCOUNT);
        assertThat(testFraudKnowledgeManagement.getTechniquesandTechnologiesUsed()).isEqualTo(UPDATED_TECHNIQUESAND_TECHNOLOGIES_USED);
        assertThat(testFraudKnowledgeManagement.getCauseForAnIncident()).isEqualTo(UPDATED_CAUSE_FOR_AN_INCIDENT);
        assertThat(testFraudKnowledgeManagement.getSystemAndProceduralLoophole()).isEqualTo(UPDATED_SYSTEM_AND_PROCEDURAL_LOOPHOLE);
        assertThat(testFraudKnowledgeManagement.getEffect()).isEqualTo(UPDATED_EFFECT);
        assertThat(testFraudKnowledgeManagement.getRecommendationsDrawn()).isEqualTo(UPDATED_RECOMMENDATIONS_DRAWN);
        assertThat(testFraudKnowledgeManagement.getPositionJG()).isEqualTo(UPDATED_POSITION_JG);
        assertThat(testFraudKnowledgeManagement.getNameIdNo()).isEqualTo(UPDATED_NAME_ID_NO);
        assertThat(testFraudKnowledgeManagement.getActionInvolved()).isEqualTo(UPDATED_ACTION_INVOLVED);
        assertThat(testFraudKnowledgeManagement.getNgScreenerReport()).isEqualTo(UPDATED_NG_SCREENER_REPORT);
        assertThat(testFraudKnowledgeManagement.getCommitteeDecision()).isEqualTo(UPDATED_COMMITTEE_DECISION);
        assertThat(testFraudKnowledgeManagement.getMeasureTaken()).isEqualTo(UPDATED_MEASURE_TAKEN);
        assertThat(testFraudKnowledgeManagement.getFraudAmountRecovered()).isEqualTo(UPDATED_FRAUD_AMOUNT_RECOVERED);
        assertThat(testFraudKnowledgeManagement.getFraudAmountWrittenOff()).isEqualTo(UPDATED_FRAUD_AMOUNT_WRITTEN_OFF);
        assertThat(testFraudKnowledgeManagement.getPreviouslyHeldForFraudOutstanding())
            .isEqualTo(UPDATED_PREVIOUSLY_HELD_FOR_FRAUD_OUTSTANDING);
    }

    @Test
    void putNonExistingFraudKnowledgeManagement() throws Exception {
        int databaseSizeBeforeUpdate = fraudKnowledgeManagementRepository.findAll().size();
        fraudKnowledgeManagement.setId(UUID.randomUUID().toString());

        // Create the FraudKnowledgeManagement
        FraudKnowledgeManagementDTO fraudKnowledgeManagementDTO = fraudKnowledgeManagementMapper.toDto(fraudKnowledgeManagement);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFraudKnowledgeManagementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fraudKnowledgeManagementDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fraudKnowledgeManagementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FraudKnowledgeManagement in the database
        List<FraudKnowledgeManagement> fraudKnowledgeManagementList = fraudKnowledgeManagementRepository.findAll();
        assertThat(fraudKnowledgeManagementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchFraudKnowledgeManagement() throws Exception {
        int databaseSizeBeforeUpdate = fraudKnowledgeManagementRepository.findAll().size();
        fraudKnowledgeManagement.setId(UUID.randomUUID().toString());

        // Create the FraudKnowledgeManagement
        FraudKnowledgeManagementDTO fraudKnowledgeManagementDTO = fraudKnowledgeManagementMapper.toDto(fraudKnowledgeManagement);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFraudKnowledgeManagementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fraudKnowledgeManagementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FraudKnowledgeManagement in the database
        List<FraudKnowledgeManagement> fraudKnowledgeManagementList = fraudKnowledgeManagementRepository.findAll();
        assertThat(fraudKnowledgeManagementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamFraudKnowledgeManagement() throws Exception {
        int databaseSizeBeforeUpdate = fraudKnowledgeManagementRepository.findAll().size();
        fraudKnowledgeManagement.setId(UUID.randomUUID().toString());

        // Create the FraudKnowledgeManagement
        FraudKnowledgeManagementDTO fraudKnowledgeManagementDTO = fraudKnowledgeManagementMapper.toDto(fraudKnowledgeManagement);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFraudKnowledgeManagementMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fraudKnowledgeManagementDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FraudKnowledgeManagement in the database
        List<FraudKnowledgeManagement> fraudKnowledgeManagementList = fraudKnowledgeManagementRepository.findAll();
        assertThat(fraudKnowledgeManagementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateFraudKnowledgeManagementWithPatch() throws Exception {
        // Initialize the database
        fraudKnowledgeManagementRepository.save(fraudKnowledgeManagement);

        int databaseSizeBeforeUpdate = fraudKnowledgeManagementRepository.findAll().size();

        // Update the fraudKnowledgeManagement using partial update
        FraudKnowledgeManagement partialUpdatedFraudKnowledgeManagement = new FraudKnowledgeManagement();
        partialUpdatedFraudKnowledgeManagement.setId(fraudKnowledgeManagement.getId());

        partialUpdatedFraudKnowledgeManagement
            .fraudIncident(UPDATED_FRAUD_INCIDENT)
            .actualIncident(UPDATED_ACTUAL_INCIDENT)
            .attemptIncident(UPDATED_ATTEMPT_INCIDENT)
            .reasonForFailure(UPDATED_REASON_FOR_FAILURE)
            .dateOfDetection(UPDATED_DATE_OF_DETECTION)
            .reasonForDelay(UPDATED_REASON_FOR_DELAY)
            .projectCreationDate(UPDATED_PROJECT_CREATION_DATE)
            .reportDate(UPDATED_REPORT_DATE)
            .actualFraudAmount(UPDATED_ACTUAL_FRAUD_AMOUNT)
            .creditAccount(UPDATED_CREDIT_ACCOUNT)
            .causeForAnIncident(UPDATED_CAUSE_FOR_AN_INCIDENT)
            .systemAndProceduralLoophole(UPDATED_SYSTEM_AND_PROCEDURAL_LOOPHOLE)
            .nameIdNo(UPDATED_NAME_ID_NO)
            .ngScreenerReport(UPDATED_NG_SCREENER_REPORT)
            .committeeDecision(UPDATED_COMMITTEE_DECISION)
            .fraudAmountWrittenOff(UPDATED_FRAUD_AMOUNT_WRITTEN_OFF)
            .previouslyHeldForFraudOutstanding(UPDATED_PREVIOUSLY_HELD_FOR_FRAUD_OUTSTANDING);

        restFraudKnowledgeManagementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFraudKnowledgeManagement.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFraudKnowledgeManagement))
            )
            .andExpect(status().isOk());

        // Validate the FraudKnowledgeManagement in the database
        List<FraudKnowledgeManagement> fraudKnowledgeManagementList = fraudKnowledgeManagementRepository.findAll();
        assertThat(fraudKnowledgeManagementList).hasSize(databaseSizeBeforeUpdate);
        FraudKnowledgeManagement testFraudKnowledgeManagement = fraudKnowledgeManagementList.get(fraudKnowledgeManagementList.size() - 1);
        assertThat(testFraudKnowledgeManagement.getReportNumber()).isEqualTo(DEFAULT_REPORT_NUMBER);
        assertThat(testFraudKnowledgeManagement.getFraudIncident()).isEqualTo(UPDATED_FRAUD_INCIDENT);
        assertThat(testFraudKnowledgeManagement.getActualIncident()).isEqualTo(UPDATED_ACTUAL_INCIDENT);
        assertThat(testFraudKnowledgeManagement.getAttemptIncident()).isEqualTo(UPDATED_ATTEMPT_INCIDENT);
        assertThat(testFraudKnowledgeManagement.getReasonForFailure()).isEqualTo(UPDATED_REASON_FOR_FAILURE);
        assertThat(testFraudKnowledgeManagement.getUnit()).isEqualTo(DEFAULT_UNIT);
        assertThat(testFraudKnowledgeManagement.getIncidentDate()).isEqualTo(DEFAULT_INCIDENT_DATE);
        assertThat(testFraudKnowledgeManagement.getDateOfDetection()).isEqualTo(UPDATED_DATE_OF_DETECTION);
        assertThat(testFraudKnowledgeManagement.getReasonForDelay()).isEqualTo(UPDATED_REASON_FOR_DELAY);
        assertThat(testFraudKnowledgeManagement.getProjectCreationDate()).isEqualTo(UPDATED_PROJECT_CREATION_DATE);
        assertThat(testFraudKnowledgeManagement.getReportDate()).isEqualTo(UPDATED_REPORT_DATE);
        assertThat(testFraudKnowledgeManagement.getSuspectedFraudster()).isEqualTo(DEFAULT_SUSPECTED_FRAUDSTER);
        assertThat(testFraudKnowledgeManagement.getFinancialLossAmount()).isEqualTo(DEFAULT_FINANCIAL_LOSS_AMOUNT);
        assertThat(testFraudKnowledgeManagement.getActualFraudAmount()).isEqualTo(UPDATED_ACTUAL_FRAUD_AMOUNT);
        assertThat(testFraudKnowledgeManagement.getDebitAccount()).isEqualTo(DEFAULT_DEBIT_ACCOUNT);
        assertThat(testFraudKnowledgeManagement.getCreditAccount()).isEqualTo(UPDATED_CREDIT_ACCOUNT);
        assertThat(testFraudKnowledgeManagement.getTechniquesandTechnologiesUsed()).isEqualTo(DEFAULT_TECHNIQUESAND_TECHNOLOGIES_USED);
        assertThat(testFraudKnowledgeManagement.getCauseForAnIncident()).isEqualTo(UPDATED_CAUSE_FOR_AN_INCIDENT);
        assertThat(testFraudKnowledgeManagement.getSystemAndProceduralLoophole()).isEqualTo(UPDATED_SYSTEM_AND_PROCEDURAL_LOOPHOLE);
        assertThat(testFraudKnowledgeManagement.getEffect()).isEqualTo(DEFAULT_EFFECT);
        assertThat(testFraudKnowledgeManagement.getRecommendationsDrawn()).isEqualTo(DEFAULT_RECOMMENDATIONS_DRAWN);
        assertThat(testFraudKnowledgeManagement.getPositionJG()).isEqualTo(DEFAULT_POSITION_JG);
        assertThat(testFraudKnowledgeManagement.getNameIdNo()).isEqualTo(UPDATED_NAME_ID_NO);
        assertThat(testFraudKnowledgeManagement.getActionInvolved()).isEqualTo(DEFAULT_ACTION_INVOLVED);
        assertThat(testFraudKnowledgeManagement.getNgScreenerReport()).isEqualTo(UPDATED_NG_SCREENER_REPORT);
        assertThat(testFraudKnowledgeManagement.getCommitteeDecision()).isEqualTo(UPDATED_COMMITTEE_DECISION);
        assertThat(testFraudKnowledgeManagement.getMeasureTaken()).isEqualTo(DEFAULT_MEASURE_TAKEN);
        assertThat(testFraudKnowledgeManagement.getFraudAmountRecovered()).isEqualTo(DEFAULT_FRAUD_AMOUNT_RECOVERED);
        assertThat(testFraudKnowledgeManagement.getFraudAmountWrittenOff()).isEqualTo(UPDATED_FRAUD_AMOUNT_WRITTEN_OFF);
        assertThat(testFraudKnowledgeManagement.getPreviouslyHeldForFraudOutstanding())
            .isEqualTo(UPDATED_PREVIOUSLY_HELD_FOR_FRAUD_OUTSTANDING);
    }

    @Test
    void fullUpdateFraudKnowledgeManagementWithPatch() throws Exception {
        // Initialize the database
        fraudKnowledgeManagementRepository.save(fraudKnowledgeManagement);

        int databaseSizeBeforeUpdate = fraudKnowledgeManagementRepository.findAll().size();

        // Update the fraudKnowledgeManagement using partial update
        FraudKnowledgeManagement partialUpdatedFraudKnowledgeManagement = new FraudKnowledgeManagement();
        partialUpdatedFraudKnowledgeManagement.setId(fraudKnowledgeManagement.getId());

        partialUpdatedFraudKnowledgeManagement
            .reportNumber(UPDATED_REPORT_NUMBER)
            .fraudIncident(UPDATED_FRAUD_INCIDENT)
            .actualIncident(UPDATED_ACTUAL_INCIDENT)
            .attemptIncident(UPDATED_ATTEMPT_INCIDENT)
            .reasonForFailure(UPDATED_REASON_FOR_FAILURE)
            .unit(UPDATED_UNIT)
            .incidentDate(UPDATED_INCIDENT_DATE)
            .dateOfDetection(UPDATED_DATE_OF_DETECTION)
            .reasonForDelay(UPDATED_REASON_FOR_DELAY)
            .projectCreationDate(UPDATED_PROJECT_CREATION_DATE)
            .reportDate(UPDATED_REPORT_DATE)
            .suspectedFraudster(UPDATED_SUSPECTED_FRAUDSTER)
            .financialLossAmount(UPDATED_FINANCIAL_LOSS_AMOUNT)
            .actualFraudAmount(UPDATED_ACTUAL_FRAUD_AMOUNT)
            .debitAccount(UPDATED_DEBIT_ACCOUNT)
            .creditAccount(UPDATED_CREDIT_ACCOUNT)
            .techniquesandTechnologiesUsed(UPDATED_TECHNIQUESAND_TECHNOLOGIES_USED)
            .causeForAnIncident(UPDATED_CAUSE_FOR_AN_INCIDENT)
            .systemAndProceduralLoophole(UPDATED_SYSTEM_AND_PROCEDURAL_LOOPHOLE)
            .effect(UPDATED_EFFECT)
            .recommendationsDrawn(UPDATED_RECOMMENDATIONS_DRAWN)
            .positionJG(UPDATED_POSITION_JG)
            .nameIdNo(UPDATED_NAME_ID_NO)
            .actionInvolved(UPDATED_ACTION_INVOLVED)
            .ngScreenerReport(UPDATED_NG_SCREENER_REPORT)
            .committeeDecision(UPDATED_COMMITTEE_DECISION)
            .measureTaken(UPDATED_MEASURE_TAKEN)
            .fraudAmountRecovered(UPDATED_FRAUD_AMOUNT_RECOVERED)
            .fraudAmountWrittenOff(UPDATED_FRAUD_AMOUNT_WRITTEN_OFF)
            .previouslyHeldForFraudOutstanding(UPDATED_PREVIOUSLY_HELD_FOR_FRAUD_OUTSTANDING);

        restFraudKnowledgeManagementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFraudKnowledgeManagement.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFraudKnowledgeManagement))
            )
            .andExpect(status().isOk());

        // Validate the FraudKnowledgeManagement in the database
        List<FraudKnowledgeManagement> fraudKnowledgeManagementList = fraudKnowledgeManagementRepository.findAll();
        assertThat(fraudKnowledgeManagementList).hasSize(databaseSizeBeforeUpdate);
        FraudKnowledgeManagement testFraudKnowledgeManagement = fraudKnowledgeManagementList.get(fraudKnowledgeManagementList.size() - 1);
        assertThat(testFraudKnowledgeManagement.getReportNumber()).isEqualTo(UPDATED_REPORT_NUMBER);
        assertThat(testFraudKnowledgeManagement.getFraudIncident()).isEqualTo(UPDATED_FRAUD_INCIDENT);
        assertThat(testFraudKnowledgeManagement.getActualIncident()).isEqualTo(UPDATED_ACTUAL_INCIDENT);
        assertThat(testFraudKnowledgeManagement.getAttemptIncident()).isEqualTo(UPDATED_ATTEMPT_INCIDENT);
        assertThat(testFraudKnowledgeManagement.getReasonForFailure()).isEqualTo(UPDATED_REASON_FOR_FAILURE);
        assertThat(testFraudKnowledgeManagement.getUnit()).isEqualTo(UPDATED_UNIT);
        assertThat(testFraudKnowledgeManagement.getIncidentDate()).isEqualTo(UPDATED_INCIDENT_DATE);
        assertThat(testFraudKnowledgeManagement.getDateOfDetection()).isEqualTo(UPDATED_DATE_OF_DETECTION);
        assertThat(testFraudKnowledgeManagement.getReasonForDelay()).isEqualTo(UPDATED_REASON_FOR_DELAY);
        assertThat(testFraudKnowledgeManagement.getProjectCreationDate()).isEqualTo(UPDATED_PROJECT_CREATION_DATE);
        assertThat(testFraudKnowledgeManagement.getReportDate()).isEqualTo(UPDATED_REPORT_DATE);
        assertThat(testFraudKnowledgeManagement.getSuspectedFraudster()).isEqualTo(UPDATED_SUSPECTED_FRAUDSTER);
        assertThat(testFraudKnowledgeManagement.getFinancialLossAmount()).isEqualTo(UPDATED_FINANCIAL_LOSS_AMOUNT);
        assertThat(testFraudKnowledgeManagement.getActualFraudAmount()).isEqualTo(UPDATED_ACTUAL_FRAUD_AMOUNT);
        assertThat(testFraudKnowledgeManagement.getDebitAccount()).isEqualTo(UPDATED_DEBIT_ACCOUNT);
        assertThat(testFraudKnowledgeManagement.getCreditAccount()).isEqualTo(UPDATED_CREDIT_ACCOUNT);
        assertThat(testFraudKnowledgeManagement.getTechniquesandTechnologiesUsed()).isEqualTo(UPDATED_TECHNIQUESAND_TECHNOLOGIES_USED);
        assertThat(testFraudKnowledgeManagement.getCauseForAnIncident()).isEqualTo(UPDATED_CAUSE_FOR_AN_INCIDENT);
        assertThat(testFraudKnowledgeManagement.getSystemAndProceduralLoophole()).isEqualTo(UPDATED_SYSTEM_AND_PROCEDURAL_LOOPHOLE);
        assertThat(testFraudKnowledgeManagement.getEffect()).isEqualTo(UPDATED_EFFECT);
        assertThat(testFraudKnowledgeManagement.getRecommendationsDrawn()).isEqualTo(UPDATED_RECOMMENDATIONS_DRAWN);
        assertThat(testFraudKnowledgeManagement.getPositionJG()).isEqualTo(UPDATED_POSITION_JG);
        assertThat(testFraudKnowledgeManagement.getNameIdNo()).isEqualTo(UPDATED_NAME_ID_NO);
        assertThat(testFraudKnowledgeManagement.getActionInvolved()).isEqualTo(UPDATED_ACTION_INVOLVED);
        assertThat(testFraudKnowledgeManagement.getNgScreenerReport()).isEqualTo(UPDATED_NG_SCREENER_REPORT);
        assertThat(testFraudKnowledgeManagement.getCommitteeDecision()).isEqualTo(UPDATED_COMMITTEE_DECISION);
        assertThat(testFraudKnowledgeManagement.getMeasureTaken()).isEqualTo(UPDATED_MEASURE_TAKEN);
        assertThat(testFraudKnowledgeManagement.getFraudAmountRecovered()).isEqualTo(UPDATED_FRAUD_AMOUNT_RECOVERED);
        assertThat(testFraudKnowledgeManagement.getFraudAmountWrittenOff()).isEqualTo(UPDATED_FRAUD_AMOUNT_WRITTEN_OFF);
        assertThat(testFraudKnowledgeManagement.getPreviouslyHeldForFraudOutstanding())
            .isEqualTo(UPDATED_PREVIOUSLY_HELD_FOR_FRAUD_OUTSTANDING);
    }

    @Test
    void patchNonExistingFraudKnowledgeManagement() throws Exception {
        int databaseSizeBeforeUpdate = fraudKnowledgeManagementRepository.findAll().size();
        fraudKnowledgeManagement.setId(UUID.randomUUID().toString());

        // Create the FraudKnowledgeManagement
        FraudKnowledgeManagementDTO fraudKnowledgeManagementDTO = fraudKnowledgeManagementMapper.toDto(fraudKnowledgeManagement);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFraudKnowledgeManagementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fraudKnowledgeManagementDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fraudKnowledgeManagementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FraudKnowledgeManagement in the database
        List<FraudKnowledgeManagement> fraudKnowledgeManagementList = fraudKnowledgeManagementRepository.findAll();
        assertThat(fraudKnowledgeManagementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchFraudKnowledgeManagement() throws Exception {
        int databaseSizeBeforeUpdate = fraudKnowledgeManagementRepository.findAll().size();
        fraudKnowledgeManagement.setId(UUID.randomUUID().toString());

        // Create the FraudKnowledgeManagement
        FraudKnowledgeManagementDTO fraudKnowledgeManagementDTO = fraudKnowledgeManagementMapper.toDto(fraudKnowledgeManagement);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFraudKnowledgeManagementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fraudKnowledgeManagementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FraudKnowledgeManagement in the database
        List<FraudKnowledgeManagement> fraudKnowledgeManagementList = fraudKnowledgeManagementRepository.findAll();
        assertThat(fraudKnowledgeManagementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamFraudKnowledgeManagement() throws Exception {
        int databaseSizeBeforeUpdate = fraudKnowledgeManagementRepository.findAll().size();
        fraudKnowledgeManagement.setId(UUID.randomUUID().toString());

        // Create the FraudKnowledgeManagement
        FraudKnowledgeManagementDTO fraudKnowledgeManagementDTO = fraudKnowledgeManagementMapper.toDto(fraudKnowledgeManagement);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFraudKnowledgeManagementMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fraudKnowledgeManagementDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FraudKnowledgeManagement in the database
        List<FraudKnowledgeManagement> fraudKnowledgeManagementList = fraudKnowledgeManagementRepository.findAll();
        assertThat(fraudKnowledgeManagementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteFraudKnowledgeManagement() throws Exception {
        // Initialize the database
        fraudKnowledgeManagementRepository.save(fraudKnowledgeManagement);

        int databaseSizeBeforeDelete = fraudKnowledgeManagementRepository.findAll().size();

        // Delete the fraudKnowledgeManagement
        restFraudKnowledgeManagementMockMvc
            .perform(delete(ENTITY_API_URL_ID, fraudKnowledgeManagement.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FraudKnowledgeManagement> fraudKnowledgeManagementList = fraudKnowledgeManagementRepository.findAll();
        assertThat(fraudKnowledgeManagementList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
