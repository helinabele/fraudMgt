package org.audit.app.repository;

import java.util.List;
import java.util.Optional;
import org.audit.app.domain.TeamLead;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the TeamLead entity.
 */
@Repository
public interface TeamLeadRepository extends MongoRepository<TeamLead, String> {
    @Query("{}")
    Page<TeamLead> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<TeamLead> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Optional<TeamLead> findOneWithEagerRelationships(String id);
}
