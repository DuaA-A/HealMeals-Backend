package HealMeals.Api.Controller;

import HealMeals.Api.model.RecipeCondition;
import HealMeals.Api.service.RecipeConditionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/recipes/{recipeId}/conditions")
@RequiredArgsConstructor
public class RecipeConditionController {

    private final RecipeConditionService service;

    @PostMapping("/{conditionId}")
    public ResponseEntity<RecipeCondition> addConditionToRecipe(
            @PathVariable UUID recipeId,
            @PathVariable UUID conditionId,
            @RequestParam boolean safe
    ) {
        RecipeCondition rc = service.addRecipeCondition(recipeId, conditionId, safe);
        return ResponseEntity.ok(rc);
    }
}
