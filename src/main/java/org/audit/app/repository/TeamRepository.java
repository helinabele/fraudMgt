package org.audit.app.repository;

import java.util.List;
import java.util.Optional;
import org.audit.app.domain.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Team entity.
 */
@Repository
public interface TeamRepository extends MongoRepository<Team, String> {
    @Query("{}")
    Page<Team> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<Team> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Optional<Team> findOneWithEagerRelationships(String id);
}
