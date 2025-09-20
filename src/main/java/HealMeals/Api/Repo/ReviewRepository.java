package HealMeals.Api.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import HealMeals.Api.model.Review;

import java.util.List;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, UUID> {
    List<Review> findByUser_UserId(UUID userId);
    List<Review> findByRecipe_RecipeId(UUID recipeId);
}
