package org.audit.app.repository;

import java.util.List;
import java.util.Optional;
import org.audit.app.domain.FraudKnowledgeManagement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the FraudKnowledgeManagement entity.
 */
@Repository
public interface FraudKnowledgeManagementRepository extends MongoRepository<FraudKnowledgeManagement, String> {
    @Query("{}")
    Page<FraudKnowledgeManagement> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<FraudKnowledgeManagement> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Optional<FraudKnowledgeManagement> findOneWithEagerRelationships(String id);
}
