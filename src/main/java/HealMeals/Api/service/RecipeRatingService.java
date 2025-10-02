package HealMeals.Api.service;

import HealMeals.Api.Repo.RecipeRatingRepository;
import HealMeals.Api.Repo.RecipeRepository;
import HealMeals.Api.Repo.UserRepository;
import HealMeals.Api.model.Recipe;
import HealMeals.Api.model.RecipeRating;
import HealMeals.Api.model.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RecipeRatingService {

    private final RecipeRatingRepository ratingRepository;
    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;

    public void rateRecipe(UUID userId, UUID recipeId, int stars) {
        if (stars < 1 || stars > 5) {
            throw new IllegalArgumentException("Stars must be between 1 and 5");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new EntityNotFoundException("Recipe not found"));

        RecipeRating rating = ratingRepository.findByUser_UserIdAndRecipe_RecipeId(userId, recipeId)
                .orElse(RecipeRating.builder()
                        .user(user)
                        .recipe(recipe)
                        .build());

        rating.setStars(stars);
        rating.setRatedAt(LocalDateTime.now());
        ratingRepository.save(rating);
    }

    public double getAverageRating(UUID recipeId) {
        List<RecipeRating> ratings = ratingRepository.findByRecipe_RecipeId(recipeId);
        if (ratings.isEmpty()) return 0.0;
        return ratings.stream().mapToInt(RecipeRating::getStars).average().orElse(0.0);
    }
}
