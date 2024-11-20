package manajero.xp.manajeroxpmethodology.Repositories.Tutoriel;

import manajero.xp.manajeroxpmethodology.Entities.Tutoriel.Practice;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PracticeRepository extends MongoRepository<Practice, String> {}
