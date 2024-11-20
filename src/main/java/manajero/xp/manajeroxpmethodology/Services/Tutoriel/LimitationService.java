package manajero.xp.manajeroxpmethodology.Services.Tutoriel;

import manajero.xp.manajeroxpmethodology.Entities.Tutoriel.Limitation;
import manajero.xp.manajeroxpmethodology.Repositories.Tutoriel.LimitationRepository;
import manajero.xp.manajeroxpmethodology.Services.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class LimitationService {
    @Autowired
    private LimitationRepository limitationRepository;
    @Autowired
    private FileStorageService fileStorageService;

    public List<Limitation> getAllLimitations() {
        return limitationRepository.findAll();
    }


    public Limitation createLimitation( String description, MultipartFile file) throws IOException {
        String fileUrl = fileStorageService.storeFile(file);
        Limitation limitation = new Limitation();
        limitation.setDescription(description);
        limitation.setImageUrl(fileUrl);
        return limitationRepository.save(limitation);
    }
    public Limitation updateLimitation(String id, String description, MultipartFile file) throws IOException {
        Optional<Limitation> limitationOptional = limitationRepository.findById(id);
        if (limitationOptional.isPresent()) {
            Limitation limitation = limitationOptional.get();
            limitation.setDescription(description);
            if (file != null && !file.isEmpty()) {
                String fileUrl = fileStorageService.storeFile(file);
                limitation.setImageUrl(fileUrl);
            }
            return limitationRepository.save(limitation);
        } else {
            throw new RuntimeException("Benefit not found with id " + id);
        }
    }


    public void deleteLimitation(String id) {
        limitationRepository.deleteById(id);
    }
}
