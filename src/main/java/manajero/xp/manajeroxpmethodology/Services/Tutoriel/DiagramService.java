package manajero.xp.manajeroxpmethodology.Services.Tutoriel;


import manajero.xp.manajeroxpmethodology.Entities.Tutoriel.Diagram;
import manajero.xp.manajeroxpmethodology.Repositories.Tutoriel.DiagramRepository;
import manajero.xp.manajeroxpmethodology.Services.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class DiagramService {
    @Autowired
    private DiagramRepository diagramRepository;
    @Autowired
    private FileStorageService fileStorageService;

    public List<Diagram> getAllDiagrams() {
        return diagramRepository.findAll();
    }

    public Optional<Diagram> getDiagramById(String id) {
        return diagramRepository.findById(id);
    }


    public Diagram saveDiagram(String name, MultipartFile file) throws IOException {
        String fileUrl = fileStorageService.storeFile(file);
        Diagram diagram = new Diagram();
        diagram.setName(name);
        diagram.setImageUrl(fileUrl);
        return diagramRepository.save(diagram);
    }
    public Diagram updateDiagram(String id, String name,MultipartFile file) throws IOException {
        Optional<Diagram> diagramOptional = diagramRepository.findById(id);
        if (diagramOptional.isPresent()) {
            Diagram diagram = diagramOptional.get();
            diagram.setName(name);
            if (file != null && !file.isEmpty()) {
                String fileUrl = fileStorageService.storeFile(file);
                diagram.setImageUrl(fileUrl);
            }
            return diagramRepository.save(diagram);
        } else {
            throw new RuntimeException("digram not found with id " + id);
        }
    }
    public void deleteDiagram(String id) {
        diagramRepository.deleteById(id);
    }
}
