package manajero.xp.manajeroxpmethodology.Services;

import manajero.xp.manajeroxpmethodology.Repositories.Tutoriel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TutorielService {

    @Autowired
    private DefinitionRepository definitionRepository;

    @Autowired
    private BenefitRepository benefitRepository;

    @Autowired
    private LimitationRepository limitationRepository;

    @Autowired
    private KeyFactorRepository keyFactorRepository;

    @Autowired
    private PracticeRepository practiceRepository;

    @Autowired
    private ImplementationRepository implementationRepository;

    @Autowired
    private ImpactRepository impactRepository;

    @Autowired
    private DiagramRepository diagramRepository;

    // Method to delete all documents in related collections
    public void deleteAllDocuments() {definitionRepository.deleteAll();
        benefitRepository.deleteAll();
        limitationRepository.deleteAll();
        keyFactorRepository.deleteAll();
        practiceRepository.deleteAll();
        implementationRepository.deleteAll();
        impactRepository.deleteAll();
        diagramRepository.deleteAll();
    }

    // Other methods as needed
}
