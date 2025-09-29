package HealMeals.Api.Controller;
import HealMeals.Api.DTO.UserConditionDTO;
import HealMeals.Api.service.UserConditionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/user-conditions")
@RequiredArgsConstructor
public class UserConditionController {

    private final UserConditionService userConditionService;

    @PostMapping("/{userId}/{conditionId}")
    public ResponseEntity<UserConditionDTO> addCondition(@PathVariable UUID userId,
                                                            @PathVariable UUID conditionId) {
        return ResponseEntity.ok(userConditionService.addCondition(userId, conditionId));
    }

    @GetMapping
    public ResponseEntity<List<UserConditionDTO>> getAll() {
        return ResponseEntity.ok(userConditionService.getAll());
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<UserConditionDTO>> getAllUserConditions(@PathVariable UUID userId) {
        return ResponseEntity.ok(userConditionService.getAllUserConditions(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserConditionDTO> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(userConditionService.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        userConditionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
