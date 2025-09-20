package HealMeals.Api.Mapper;

import HealMeals.Api.DTO.RecipeIngredientDTO;
import HealMeals.Api.model.Ingredient;
import HealMeals.Api.model.RecipeIngredient;

public class RecipeIngredientMapper {
    public static RecipeIngredient toEntity(RecipeIngredientDTO dto, Ingredient ingredient){
        RecipeIngredient recipeIngredient = RecipeIngredient.builder()
                .recipe_ingredientId(dto.getRecipe_ingredientId())
                .ingredient(ingredient)
                .quantity(dto.getQuantity())
                .unit(dto.getUnit())
                .build();
        return recipeIngredient;
    }

    public static RecipeIngredientDTO toDto(RecipeIngredient recipeIngredient){
        RecipeIngredientDTO dto = RecipeIngredientDTO.builder()
                .recipe_id(recipeIngredient.getRecipe().getRecipeId())
                .ingredient_id(recipeIngredient.getIngredient().getIngredient_id())
                .quantity(recipeIngredient.getQuantity())
                .unit(recipeIngredient.getUnit())
                .build();
        return dto;
    }
}
