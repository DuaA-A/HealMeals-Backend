package HealMeals.Api.Controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import HealMeals.Api.DTO.ProfileConditionDTO;
import HealMeals.Api.service.ProfileConditionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/profile-conditions")
@RequiredArgsConstructor
public class ProfileConditionController {

    private final ProfileConditionService service;

    @PostMapping
    public ResponseEntity<ProfileConditionDTO> create(@Valid @RequestBody ProfileConditionDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<ProfileConditionDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfileConditionDTO> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfileConditionDTO> update(@PathVariable UUID id, @Valid @RequestBody ProfileConditionDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/allergies")
    public List<ProfileConditionDTO> getAllergies() {
        return service.getAllergies();
    }

    @GetMapping("/diseases")
    public List<ProfileConditionDTO> getDiseases() {
        return service.getDiseases();
    }
    
}
