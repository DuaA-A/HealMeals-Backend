package HealMeals.Api.service;

import HealMeals.Api.model.RecipeStep;
import HealMeals.Api.model.Recipe;
import HealMeals.Api.Repo.RecipeStepRepository;
import HealMeals.Api.Repo.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RecipeStepService {

    private final RecipeStepRepository stepRepository;
    private final RecipeRepository recipeRepository;

    public RecipeStep addStep(UUID recipeId, String instruction) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RuntimeException("Recipe not found"));

        RecipeStep step = RecipeStep.builder()
                .instruction(instruction)
                .recipe(recipe)
                .build();
        return stepRepository.save(step);
    }

    public void deleteStep(UUID stepId) {
        if (!stepRepository.existsById(stepId)) {
            throw new RuntimeException("Step not found");
        }
        stepRepository.deleteById(stepId);
    }
    
}
