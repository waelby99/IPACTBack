package manajero.xp.manajeroxpmethodology.Services.Tutoriel;

import manajero.xp.manajeroxpmethodology.Entities.Tutoriel.Benefit;
import manajero.xp.manajeroxpmethodology.Repositories.Tutoriel.BenefitRepository;
import manajero.xp.manajeroxpmethodology.Services.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class BenefitService {
    @Autowired
    private BenefitRepository benefitRepository;
    @Autowired
    private FileStorageService fileStorageService;

    public List<Benefit> getAllBenefits() {
        return benefitRepository.findAll();
    }

    public Benefit createBenefit(String name, String description, MultipartFile file) throws IOException {
        String fileUrl = fileStorageService.storeFile(file);
        Benefit benefit = new Benefit();
        benefit.setName(name);
        benefit.setDescription(description);
        benefit.setImageUrl(fileUrl);
        return benefitRepository.save(benefit);
    }

    public Benefit updateBenefit(String id, String name, String description, MultipartFile file) throws IOException {
        Optional<Benefit> benefitOptional = benefitRepository.findById(id);
        if (benefitOptional.isPresent()) {
            Benefit benefit = benefitOptional.get();
            benefit.setName(name);
            benefit.setDescription(description);
            if (file != null && !file.isEmpty()) {
                String fileUrl = fileStorageService.storeFile(file);
                benefit.setImageUrl(fileUrl);
            }
            return benefitRepository.save(benefit);
        } else {
            throw new RuntimeException("Benefit not found with id " + id);
        }
    }


    public void deleteBenefit(String id) {
        benefitRepository.deleteById(id);
    }
}
