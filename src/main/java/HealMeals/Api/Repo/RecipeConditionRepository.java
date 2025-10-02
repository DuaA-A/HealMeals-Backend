package HealMeals.Api.Repo;

import HealMeals.Api.model.RecipeCondition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RecipeConditionRepository extends JpaRepository<RecipeCondition, UUID> {
    List<RecipeCondition> findByRecipeRecipeId(UUID recipeId);
}
