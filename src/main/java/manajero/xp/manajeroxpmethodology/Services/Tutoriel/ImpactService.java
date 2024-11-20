package manajero.xp.manajeroxpmethodology.Services.Tutoriel;

import manajero.xp.manajeroxpmethodology.Entities.Tutoriel.Impact;
import manajero.xp.manajeroxpmethodology.Repositories.Tutoriel.ImpactRepository;
import manajero.xp.manajeroxpmethodology.Services.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ImpactService {
    @Autowired
    private ImpactRepository impactRepository;
    @Autowired
    private FileStorageService fileStorageService;
    public List<Impact> getAllImpacts() {
        return impactRepository.findAll();
    }




    public Impact createImpact(String name, String description, MultipartFile file) throws IOException {
        String fileUrl = fileStorageService.storeFile(file);
        Impact impact = new Impact();
        impact.setName(name);
        impact.setDescription(description);
        impact.setImageUrl(fileUrl);
        return impactRepository.save(impact);
    }
    public Impact updateImpact(String id, String name, String description, MultipartFile file) throws IOException {
        Optional<Impact> impactOptional = impactRepository.findById(id);
        if (impactOptional.isPresent()) {
            Impact impact = impactOptional.get();
            impact.setName(name);
            impact.setDescription(description);
            if (file != null && !file.isEmpty()) {
                String fileUrl = fileStorageService.storeFile(file);
                impact.setImageUrl(fileUrl);
            }
            return impactRepository.save(impact);
        } else {
            throw new RuntimeException("Benefit not found with id " + id);
        }
    }


    public void deleteImpact(String id) {
        impactRepository.deleteById(id);
    }
}
