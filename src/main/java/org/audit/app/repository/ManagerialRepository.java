package org.audit.app.repository;

import java.util.List;
import java.util.Optional;
import org.audit.app.domain.Managerial;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Managerial entity.
 */
@Repository
public interface ManagerialRepository extends MongoRepository<Managerial, String> {
    @Query("{}")
    Page<Managerial> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<Managerial> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Optional<Managerial> findOneWithEagerRelationships(String id);
}
