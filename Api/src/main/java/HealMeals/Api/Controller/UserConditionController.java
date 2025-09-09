package HealMeals.Api.Controller;

import HealMeals.Api.service.UserConditionService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import HealMeals.Api.DTO.UserConditionDTO;

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
}
