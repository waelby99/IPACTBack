package manajero.xp.manajeroxpmethodology.Repositories.Tutoriel;


import manajero.xp.manajeroxpmethodology.Entities.Tutoriel.Diagram;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiagramRepository extends MongoRepository<Diagram, String> {
}

