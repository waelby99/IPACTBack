package manajero.xp.manajeroxpmethodology.Repositories;

import manajero.xp.manajeroxpmethodology.Entities.manajero.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface UserRepository extends MongoRepository<User, String> {

    @Query("{ 'id': ?0 }")
    User findUserWithTasks(String userId);
}
