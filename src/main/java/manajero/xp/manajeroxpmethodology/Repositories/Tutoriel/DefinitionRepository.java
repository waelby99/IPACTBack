package manajero.xp.manajeroxpmethodology.Repositories.Tutoriel;

import manajero.xp.manajeroxpmethodology.Entities.Tutoriel.Definition;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DefinitionRepository extends MongoRepository<Definition, String> {}
