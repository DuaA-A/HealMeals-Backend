package HealMeals.Api.Repo;

import HealMeals.Api.model.RecipeRating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RecipeRatingRepository extends JpaRepository<RecipeRating, UUID> {
    Optional<RecipeRating> findByUser_UserIdAndRecipe_RecipeId(UUID userId, UUID recipeId);
    List<RecipeRating> findByRecipe_RecipeId(UUID recipeId);
}
