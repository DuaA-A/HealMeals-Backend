package HealMeals.Api.service;

import HealMeals.Api.DTO.RecipeDTO;
import HealMeals.Api.DTO.RecipeIngredientDTO;
import HealMeals.Api.DTO.RecipeSummaryDto;
import HealMeals.Api.Mapper.RecipeMapper;
import HealMeals.Api.model.Ingredient;
import HealMeals.Api.model.Recipe;
import HealMeals.Api.model.RecipeIngredient;
import HealMeals.Api.model.User;
import HealMeals.Api.Repo.IngredientRepository;
import HealMeals.Api.Repo.ProfileConditionRepository;
import HealMeals.Api.Repo.RecipeIngredientRepository;
import HealMeals.Api.Repo.RecipeRepository;
import HealMeals.Api.Repo.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import HealMeals.Api.model.RecipeCondition;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeIngredientRepository recipeIngredientRepository;
    private final IngredientRepository ingredientRepository;
    private final UserRepository userRepository;
    private final ProfileConditionRepository profileConditionRepository; 
    
    public RecipeDTO createRecipe(RecipeDTO dto, UUID createsById) {
        User createdBy = userRepository.findById(createsById)
                .orElseThrow(() -> new RuntimeException("User Not Found"));

        Recipe recipe = RecipeMapper.toEntity(dto, createdBy, createdBy);
        recipe.setDateAdded(LocalDateTime.now());
        recipe.setDateUpdated(LocalDateTime.now());

        // Ingredients
        if (dto.getRecipeIngredients() != null) {
            List<RecipeIngredient> recipeIngredients = dto.getRecipeIngredients().stream()
                    .map(riDto -> {
                        Ingredient ingredient = ingredientRepository.findById(riDto.getIngredientId())
                                .orElseThrow(() -> new EntityNotFoundException("Ingredient Not Found"));
                        RecipeIngredient ri = new RecipeIngredient();
                        ri.setRecipe(recipe);
                        ri.setIngredient(ingredient);
                        ri.setQuantity(riDto.getQuantity());
                        ri.setUnit(riDto.getUnit());
                        return ri;
                    }).collect(Collectors.toList());
            recipe.setRecipeIngredients(recipeIngredients);
        }

        if (dto.getRecipeConditions() != null) {
            recipe.setRecipeConditions(dto.getRecipeConditions().stream()
                    .map(rcDto -> {
                        var condition = profileConditionRepository.findById(rcDto.getConditionId())
                                .orElseThrow(() -> new EntityNotFoundException("Condition Not Found"));
                        RecipeCondition rc = new RecipeCondition();
                        rc.setRecipe(recipe);
                        rc.setCondition(condition);
                        rc.setSafe(rcDto.isSafe());
                        return rc;
                    }).collect(Collectors.toList()));
        }

        Recipe saved = recipeRepository.save(recipe);
        return RecipeMapper.toDTO(saved);
    }

    public List<RecipeSummaryDto> getAllRecipes() {
        List<Recipe> recipes = recipeRepository.findAll();

        return recipes.stream()
                .map(RecipeMapper::toSummaryDto)
                .collect(Collectors.toList());
    }

    public RecipeDTO getRecipeById(UUID id) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recipe Not Found"));
        return RecipeMapper.toDTO(recipe);
    }

    // public RecipeDTO updateRecipe(UUID recipeId, RecipeDTO recipeDTO, User updatedBy) {
    //     Recipe recipe = recipeRepository.findById(recipeId)
    //             .orElseThrow(() -> new RuntimeException("Recipe not found"));

    //     // Update simple fields
    //     recipe.setName(recipeDTO.getName());
    //     recipe.setDescription(recipeDTO.getDescription());
    //     recipe.setSummary(recipeDTO.getSummary());
    //     recipe.setPrepTimeMinutes(recipeDTO.getPrepTimeMinutes());
    //     recipe.setUpdatedBy(updatedBy);

    //     // --- Update Steps ---
    //     // Clear old steps (orphanRemoval = true will delete them)
    //     recipe.getSteps().clear();

    //     if (recipeDTO.getSteps() != null) {
    //         int stepNumber = 1;
    //         for (String stepDescription : recipeDTO.getSteps()) {
    //             RecipeStep step = RecipeStep.builder()
    //                     .stepNumber(stepNumber++)
    //                     .instruction(stepDescription)
    //                     .recipe(recipe)
    //                     .build();
    //             recipe.getSteps().add(step);
    //         }
    //     }

    //     // --- Update Ingredients (if provided) ---
    //     if (recipeDTO.getRecipeIngredients() != null) {
    //         recipe.getRecipeIngredients().clear();
    //         for (RecipeIngredientDTO ingDTO : recipeDTO.getRecipeIngredients()) {
    //             RecipeIngredient ingredient = RecipeIngredient.builder()
    //                     .ingredientName(ingDTO.getIngredientName())
    //                     .quantity(ingDTO.getQuantity())
    //                     .unit(ingDTO.getUnit())
    //                     .recipe(recipe)
    //                     .build();
    //             recipe.getRecipeIngredients().add(ingredient);
    //         }
    //     }

    //     Recipe updatedRecipe = recipeRepository.save(recipe);

    //     // Convert back to DTO
    //     return RecipeMapper.toDTO(updatedRecipe);
    // }


    public void deleteRecipe(UUID recipe_id) {
        Recipe recipe = recipeRepository.findById(recipe_id).
                orElseThrow(() -> new RuntimeException("Recipe Not Found"));

        recipeRepository.delete(recipe);
    }
}
