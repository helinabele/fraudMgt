package org.audit.app.repository;

import org.audit.app.domain.Findings;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Findings entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FindingsRepository extends MongoRepository<Findings, String> {}
