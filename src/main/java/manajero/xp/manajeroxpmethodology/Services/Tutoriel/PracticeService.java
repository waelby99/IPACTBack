package manajero.xp.manajeroxpmethodology.Services.Tutoriel;



import manajero.xp.manajeroxpmethodology.Entities.Tutoriel.Practice;
import manajero.xp.manajeroxpmethodology.Repositories.Tutoriel.PracticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PracticeService {

    @Autowired
    private PracticeRepository practiceRepository;

    public List<Practice> getAllPractices() {
        return practiceRepository.findAll();
    }

    public Practice getPracticeById(String id) {
        Optional<Practice> practice = practiceRepository.findById(id);
        return practice.orElse(null);
    }

    public Practice createPractice(Practice practice) {
        return practiceRepository.save(practice);
    }

    public Practice updatePractice(String id, Practice updatedPractice) {
        Optional<Practice> optionalPractice = practiceRepository.findById(id);
        if (optionalPractice.isPresent()) {
            Practice existingPractice = optionalPractice.get();
            existingPractice.setName(updatedPractice.getName());
            existingPractice.setDefinition(updatedPractice.getDefinition());
            existingPractice.setBenefits(updatedPractice.getBenefits());
            existingPractice.setChallenges(updatedPractice.getChallenges());
            return practiceRepository.save(existingPractice);
        } else {
            return null; // Handle not found case as needed
        }
    }

    public void deletePractice(String id) {
        practiceRepository.deleteById(id);
    }
}

