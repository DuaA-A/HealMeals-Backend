package HealMeals.Api.Controller;

import HealMeals.Api.DTO.DonationDTO;
import HealMeals.Api.service.DonationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/donations")
@RequiredArgsConstructor
public class DonationController {

    private final DonationService donationService;

    @PostMapping
    public ResponseEntity<DonationDTO> create(@RequestBody DonationDTO dto) {
        return ResponseEntity.ok(donationService.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<DonationDTO>> getAll() {
        return ResponseEntity.ok(donationService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DonationDTO> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(donationService.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        donationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
