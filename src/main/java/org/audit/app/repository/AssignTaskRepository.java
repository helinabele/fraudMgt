package org.audit.app.repository;

import java.util.List;
import java.util.Optional;
import org.audit.app.domain.AssignTask;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the AssignTask entity.
 */
@Repository
public interface AssignTaskRepository extends MongoRepository<AssignTask, String> {
    @Query("{}")
    Page<AssignTask> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<AssignTask> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Optional<AssignTask> findOneWithEagerRelationships(String id);
}
