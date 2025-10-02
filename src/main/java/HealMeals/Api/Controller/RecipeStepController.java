package HealMeals.Api.Controller;

import HealMeals.Api.model.RecipeStep;
import HealMeals.Api.service.RecipeStepService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/recipes/{recipeId}/steps")
@RequiredArgsConstructor
public class RecipeStepController {

    private final RecipeStepService service;

    @PostMapping
    public ResponseEntity<RecipeStep> addStep(
            @PathVariable UUID recipeId,
            @RequestParam String instruction
    ) {
        return ResponseEntity.ok(service.addStep(recipeId, instruction));
    }

    @DeleteMapping("/{stepId}")
    public ResponseEntity<String> deleteStep(@PathVariable UUID stepId) {
        service.deleteStep(stepId);
        return ResponseEntity.ok("Step deleted successfully");
    }
}
