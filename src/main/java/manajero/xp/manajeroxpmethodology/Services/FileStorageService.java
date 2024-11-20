package manajero.xp.manajeroxpmethodology.Services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileStorageService {

    private final String uploadDir = "src/main/resources/static/uploads/";

    public FileStorageService() {
        File uploadDirectory = new File(uploadDir);
        if (!uploadDirectory.exists()) {
            uploadDirectory.mkdirs();
        }
    }

    public String storeFile(MultipartFile file) throws IOException {
        String fileExtension = getFileExtension(file.getOriginalFilename());
        String newFileName = UUID.randomUUID().toString() + "." + fileExtension;
        Path targetLocation = Paths.get(uploadDir + newFileName);
        Files.copy(file.getInputStream(), targetLocation);
        return "/uploads/" + newFileName;
    }

    private String getFileExtension(String filename) {
        return filename.substring(filename.lastIndexOf(".") + 1);
    }
}
