package HealMeals.Api.Repo;

import HealMeals.Api.model.RecipeStep;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RecipeStepRepository extends JpaRepository<RecipeStep, UUID> {
}
