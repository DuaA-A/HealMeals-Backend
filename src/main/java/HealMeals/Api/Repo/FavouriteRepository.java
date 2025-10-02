package HealMeals.Api.Repo;

import HealMeals.Api.model.Favourite;
import HealMeals.Api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface FavouriteRepository extends JpaRepository<Favourite, UUID> {
    List<Favourite> findByUser_UserId(UUID userId);
    boolean existsByUser_UserIdAndRecipe_RecipeId(UUID userId, UUID recipeId);
    void deleteByUser_UserIdAndRecipe_RecipeId(UUID userId, UUID recipeId);
}
