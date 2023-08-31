package org.audit.app.repository;

import org.audit.app.domain.InternalEmployee;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the InternalEmployee entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InternalEmployeeRepository extends MongoRepository<InternalEmployee, String> {}
