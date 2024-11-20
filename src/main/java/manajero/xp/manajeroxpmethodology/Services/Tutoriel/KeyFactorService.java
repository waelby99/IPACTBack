package manajero.xp.manajeroxpmethodology.Services.Tutoriel;

import manajero.xp.manajeroxpmethodology.Entities.Tutoriel.KeyFactor;
import manajero.xp.manajeroxpmethodology.Repositories.Tutoriel.KeyFactorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class KeyFactorService {
    @Autowired
    private KeyFactorRepository keyFactorRepository;

    public List<KeyFactor> getAllKeyFactors() {
        return keyFactorRepository.findAll();
    }

    public KeyFactor createKeyFactor(KeyFactor keyFactor) {
        return keyFactorRepository.save(keyFactor);
    }

    public KeyFactor updateKeyfactor(String id, KeyFactor updatedKeyFactor) {
        Optional<KeyFactor> optionalKeyFactor = keyFactorRepository.findById(id);
        if (optionalKeyFactor.isPresent()) {
            KeyFactor existingKeyFactor = optionalKeyFactor.get();
            existingKeyFactor.setTitre(updatedKeyFactor.getTitre());
            existingKeyFactor.setDescription(updatedKeyFactor.getDescription());
            // Update other fields as needed

            return keyFactorRepository.save(existingKeyFactor);
        }
        return null; // Handle error or return appropriate response
    }

    public boolean deleteKeyFactor(String id) {
        Optional<KeyFactor> optionalKeyFactor = keyFactorRepository.findById(id);
        if (optionalKeyFactor.isPresent()) {
            keyFactorRepository.delete(optionalKeyFactor.get());
            return true;
        }
        return false; // Handle error or return appropriate response
    }

}

