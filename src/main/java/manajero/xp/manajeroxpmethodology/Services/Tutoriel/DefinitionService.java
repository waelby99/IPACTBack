package manajero.xp.manajeroxpmethodology.Services.Tutoriel;

import manajero.xp.manajeroxpmethodology.Entities.Tutoriel.Definition;
import manajero.xp.manajeroxpmethodology.Repositories.Tutoriel.DefinitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DefinitionService {
    @Autowired
    private DefinitionRepository definitionRepository;

    public List<Definition> getAllDefinitions() {
        return definitionRepository.findAll();
    }


    public Definition getDefinitionById(String id) {
        Optional<Definition> optionalDefinition = definitionRepository.findById(id);
        return optionalDefinition.orElse(null);
    }

    public Definition createDefinition(Definition definition) {
        return definitionRepository.save(definition);
    }

    public Definition updateDefinition(String id, Definition updatedDefinition) {
        Optional<Definition> optionalDefinition = definitionRepository.findById(id);
        if (optionalDefinition.isPresent()) {
            Definition existingDefinition = optionalDefinition.get();
            existingDefinition.setTitre(updatedDefinition.getTitre());
            existingDefinition.setDescription(updatedDefinition.getDescription());
            // Update other fields as needed

            return definitionRepository.save(existingDefinition);
        }
        return null; // Handle error or return appropriate response
    }

    public boolean deleteDefinition(String id) {
        Optional<Definition> optionalDefinition = definitionRepository.findById(id);
        if (optionalDefinition.isPresent()) {
            definitionRepository.delete(optionalDefinition.get());
            return true;
        }
        return false; // Handle error or return appropriate response
    }

    // Define other CRUD operations (update, delete)
}

