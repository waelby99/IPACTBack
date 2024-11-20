package manajero.xp.manajeroxpmethodology.Repositories;


import manajero.xp.manajeroxpmethodology.Entities.manajero.Project;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface ProjectRepository extends MongoRepository<Project, String> {
    @Query("{ '_id': ?0 }")
    Project findProjectWithIterations(String projectId);
}
