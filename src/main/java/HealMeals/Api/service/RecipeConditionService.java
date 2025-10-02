package HealMeals.Api.service;

import HealMeals.Api.Repo.ProfileConditionRepository;
import HealMeals.Api.Repo.RecipeConditionRepository;
import HealMeals.Api.Repo.RecipeRepository;
import HealMeals.Api.model.ProfileCondition;
import HealMeals.Api.model.Recipe;
import HealMeals.Api.model.RecipeCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RecipeConditionService {

    private final RecipeConditionRepository recipeConditionRepository;
    private final RecipeRepository recipeRepository;
    private final ProfileConditionRepository profileConditionRepository;

    public RecipeCondition addRecipeCondition(UUID recipeId, UUID conditionId, boolean safe) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RuntimeException("Recipe not found"));
        ProfileCondition condition = profileConditionRepository.findById(conditionId)
                .orElseThrow(() -> new RuntimeException("Condition not found"));

        RecipeCondition rc = RecipeCondition.builder()
                .recipe(recipe)
                .condition(condition)
                .safe(safe)
                .build();

        return recipeConditionRepository.save(rc);
    }
}
