package org.audit.app.repository;

import org.audit.app.domain.ExternalEmployee;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the ExternalEmployee entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExternalEmployeeRepository extends MongoRepository<ExternalEmployee, String> {}
