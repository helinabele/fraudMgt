package org.audit.app.repository;

import java.util.List;
import java.util.Optional;
import org.audit.app.domain.FraudInvestigationReport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the FraudInvestigationReport entity.
 */
@Repository
public interface FraudInvestigationReportRepository extends MongoRepository<FraudInvestigationReport, String> {
    @Query("{}")
    Page<FraudInvestigationReport> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<FraudInvestigationReport> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Optional<FraudInvestigationReport> findOneWithEagerRelationships(String id);
}
