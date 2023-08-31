package org.audit.app.repository;

import org.audit.app.domain.FraudType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the FraudType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FraudTypeRepository extends MongoRepository<FraudType, String> {}
