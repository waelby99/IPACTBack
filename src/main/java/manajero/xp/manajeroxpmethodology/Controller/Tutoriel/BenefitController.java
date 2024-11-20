package manajero.xp.manajeroxpmethodology.Controller.Tutoriel;

import manajero.xp.manajeroxpmethodology.Entities.Tutoriel.Benefit;
import manajero.xp.manajeroxpmethodology.Services.Tutoriel.BenefitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/ManajeroBackend/api/benefits")
@CrossOrigin(origins = "http://localhost:4200")
public class BenefitController {
    @Autowired
    private BenefitService benefitService;

    @GetMapping
    public List<Benefit> getAllBenefits() {
        return benefitService.getAllBenefits();
    }

    @PostMapping(consumes = "multipart/form-data")
    public Benefit createBenefit(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestPart("file") MultipartFile file) throws IOException {
        return benefitService.createBenefit(name, description, file);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Benefit> updateBenefit(
            @PathVariable String id,
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        Benefit benefit = benefitService.updateBenefit(id, name, description, file);
        return ResponseEntity.ok(benefit);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBenefit(@PathVariable String id) {
        benefitService.deleteBenefit(id);
        return ResponseEntity.noContent().build();
    }
}
