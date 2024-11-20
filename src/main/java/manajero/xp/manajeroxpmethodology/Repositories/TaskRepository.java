package manajero.xp.manajeroxpmethodology.Repositories;


import manajero.xp.manajeroxpmethodology.Entities.manajero.Status;
import manajero.xp.manajeroxpmethodology.Entities.manajero.Task;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface TaskRepository extends MongoRepository<Task, String> {
    @Query("{ '_id': ?0 }")
    Task findTaskWithBugs(String taskId);

    @Query("{ 'status': 'DONE' }")
    List<Task> findAllCompletedTasks();

    List<Task> findByStatusNot(Status status);

    long countByStatus(Status status);

    default long countTotalTasks() {
        return count();
    }

}
