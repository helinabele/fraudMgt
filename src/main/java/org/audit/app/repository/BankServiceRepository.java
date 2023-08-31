package org.audit.app.repository;

import org.audit.app.domain.BankService;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the BankService entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BankServiceRepository extends MongoRepository<BankService, String> {}
