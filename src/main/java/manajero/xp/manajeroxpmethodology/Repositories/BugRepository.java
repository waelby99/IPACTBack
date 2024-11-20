package manajero.xp.manajeroxpmethodology.Repositories;


import manajero.xp.manajeroxpmethodology.Entities.manajero.Bug;
import manajero.xp.manajeroxpmethodology.Entities.manajero.BugStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface BugRepository extends MongoRepository<Bug, String> {
    List<Bug> findByTaskId(String taskId);

    long countByStatus(BugStatus status);

    default long countTotalBug() {
        return count();
    }

    @Query("{}")
    List<Bug> findAll();
}
