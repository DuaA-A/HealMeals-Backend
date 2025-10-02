package HealMeals.Api.Controller;

import HealMeals.Api.service.RecipeRatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/recipes/{recipeId}/ratings")
@RequiredArgsConstructor
public class RecipeRatingController {

    private final RecipeRatingService ratingService;

    @PostMapping
    public ResponseEntity<String> rateRecipe(
            @PathVariable UUID recipeId,
            @RequestParam UUID userId,
            @RequestParam int stars
    ) {
        ratingService.rateRecipe(userId, recipeId, stars);
        return ResponseEntity.ok("Rating submitted/updated");
    }

    @GetMapping("/average")
    public ResponseEntity<Double> getAverageRating(@PathVariable UUID recipeId) {
        return ResponseEntity.ok(ratingService.getAverageRating(recipeId));
    }
}
