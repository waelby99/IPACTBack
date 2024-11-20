package manajero.xp.manajeroxpmethodology.Services.Tutoriel;

import manajero.xp.manajeroxpmethodology.Entities.Tutoriel.Implementation;
import manajero.xp.manajeroxpmethodology.Repositories.Tutoriel.ImplementationRepository;
import manajero.xp.manajeroxpmethodology.Services.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ImplementationService {
    @Autowired
    private ImplementationRepository implementationRepository;
    @Autowired
    private FileStorageService fileStorageService;
    public List<Implementation> getAllImplementations() {
        return implementationRepository.findAll();
    }



    public Implementation createImplementation(String name, String description, MultipartFile file) throws IOException {
        String fileUrl = fileStorageService.storeFile(file);
        Implementation implementation = new Implementation();
        implementation.setName(name);
        implementation.setDescription(description);
        implementation.setImageUrl(fileUrl);
        return implementationRepository.save(implementation);
    }
    public Implementation updateImplementation(String id, String name, String description, MultipartFile file) throws IOException {
        Optional<Implementation> implementationOptional = implementationRepository.findById(id);
        if (implementationOptional.isPresent()) {
            Implementation implementation = implementationOptional.get();
            implementation.setName(name);
            implementation.setDescription(description);
            if (file != null && !file.isEmpty()) {
                String fileUrl = fileStorageService.storeFile(file);
                implementation.setImageUrl(fileUrl);
            }
            return implementationRepository.save(implementation);
        } else {
            throw new RuntimeException("Benefit not found with id " + id);
        }
    }


    public void deleteImplementation(String id) {
        implementationRepository.deleteById(id);
    }
}
