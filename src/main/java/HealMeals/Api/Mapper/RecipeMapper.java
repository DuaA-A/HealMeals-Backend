package HealMeals.Api.Mapper;

import HealMeals.Api.DTO.RecipeDTO;
import HealMeals.Api.DTO.RecipeSummaryDto;
import HealMeals.Api.DTO.RecipeConditionDTO;
import HealMeals.Api.model.Recipe;
import HealMeals.Api.model.RecipeIngredient;
import HealMeals.Api.model.User;
import HealMeals.Api.model.RecipeCondition;

import java.util.stream.Collectors;

public class RecipeMapper {

    public static Recipe toEntity(RecipeDTO dto, User updatedBy, User createdBy) {
        if (dto == null) return null;

        Recipe recipe = Recipe.builder()
                .recipeId(dto.getRecipeId())
                .name(dto.getName())
                .description(dto.getDescription())
                .summary(dto.getSummary())
                .prepTimeMinutes(dto.getPrepTimeMinutes())
                .createdBy(createdBy)
                .updatedBy(updatedBy)
                .dateAdded(dto.getDateAdded())
                .dateUpdated(dto.getDateUpdated())
                .build();

        if (dto.getRecipeIngredients() != null) {
            recipe.setRecipeIngredients(
                    dto.getRecipeIngredients().stream()
                            .map(riDto -> {
                                RecipeIngredient ri = RecipeIngredientMapper.toEntity(riDto, null);
                                ri.setRecipe(recipe);
                                return ri;
                            })
                            .collect(Collectors.toList())
            );
        }

        if (dto.getRecipeConditions() != null) {
            recipe.setRecipeConditions(
                    dto.getRecipeConditions().stream()
                            .map(rcDto -> {
                                RecipeCondition rc = new RecipeCondition();
                                rc.setId(rcDto.getId());
                                rc.setSafe(rcDto.isSafe());
                                // ⚠️ Only set recipe here. Condition entity should be attached in service layer
                                rc.setRecipe(recipe);
                                return rc;
                            })
                            .collect(Collectors.toList())
            );
        }

        return recipe;
    }

    public static RecipeDTO toDTO(Recipe recipe) {
        if (recipe == null) return null;

        return RecipeDTO.builder()
                .recipeId(recipe.getRecipeId())
                .name(recipe.getName())
                .description(recipe.getDescription())
                .summary(recipe.getSummary())
                .prepTimeMinutes(recipe.getPrepTimeMinutes())
                .averageRating(recipe.getAverageRating())
                .steps(recipe.getSteps() != null
                        ? recipe.getSteps().stream()
                            .map(s -> s.getInstruction())
                            .collect(Collectors.toList())
                        : null)
                .recipeIngredients(recipe.getRecipeIngredients() != null
                        ? recipe.getRecipeIngredients().stream()
                            .map(RecipeIngredientMapper::toDto)
                            .collect(Collectors.toList())
                        : null)
                .recipeConditions(recipe.getRecipeConditions() != null
                        ? recipe.getRecipeConditions().stream()
                            .map(RecipeMapper::toDto)
                            .collect(Collectors.toList())
                        : null)
                .dateAdded(recipe.getDateAdded())
                .dateUpdated(recipe.getDateUpdated())
                .createdBy(recipe.getCreatedBy() != null ? recipe.getCreatedBy().getUserId() : null)
                .updatedBy(recipe.getUpdatedBy() != null ? recipe.getUpdatedBy().getUserId() : null)
                .build();
    }

    public static RecipeConditionDTO toDto(RecipeCondition recipeCondition) {
        if (recipeCondition == null) return null;

        return RecipeConditionDTO.builder()
                .id(recipeCondition.getId())
                .recipeId(recipeCondition.getRecipe() != null ? recipeCondition.getRecipe().getRecipeId() : null)
                .conditionId(recipeCondition.getCondition() != null ? recipeCondition.getCondition().getConditionId() : null)
                .conditionName(recipeCondition.getCondition() != null ? recipeCondition.getCondition().getConditionName() : null)
                .safe(recipeCondition.isSafe())
                .build();
    }

    public static RecipeSummaryDto toSummaryDto(Recipe recipe) {
        if (recipe == null) return null;

        return RecipeSummaryDto.builder()
                .recipeId(recipe.getRecipeId())
                .name(recipe.getName())
                .description(recipe.getDescription())
                .prepTimeMinutes(recipe.getPrepTimeMinutes())
                .averageRating(recipe.getAverageRating())
                .build();
    }
}