package HealMeals.Api.Controller;

import HealMeals.Api.DTO.DonationRequestDTO;
import HealMeals.Api.DTO.DonationResponseDTO;
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
    public ResponseEntity<DonationResponseDTO> create(@RequestBody DonationRequestDTO dto) {
        return ResponseEntity.ok(donationService.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<DonationResponseDTO>> getAll() {
        return ResponseEntity.ok(donationService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DonationResponseDTO> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(donationService.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        donationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
