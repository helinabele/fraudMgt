package org.audit.app.repository;

import java.util.List;
import java.util.Optional;
import org.audit.app.domain.Director;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Director entity.
 */
@Repository
public interface DirectorRepository extends MongoRepository<Director, String> {
    @Query("{}")
    Page<Director> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<Director> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Optional<Director> findOneWithEagerRelationships(String id);
}
