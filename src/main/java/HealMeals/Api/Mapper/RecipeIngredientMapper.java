package HealMeals.Api.Mapper;

import HealMeals.Api.DTO.RecipeIngredientDTO;
import HealMeals.Api.model.Ingredient;
import HealMeals.Api.model.RecipeIngredient;

public class RecipeIngredientMapper {
    public static RecipeIngredient toEntity(RecipeIngredientDTO dto, Ingredient ingredient){
        RecipeIngredient recipeIngredient = RecipeIngredient.builder()
                .recipe_ingredientId(dto.getRecipeIngredientId())
                .ingredient(ingredient)
                .quantity(dto.getQuantity())
                .unit(dto.getUnit())
                .build();
        return recipeIngredient;
    }

    public static RecipeIngredientDTO toDto(RecipeIngredient recipeIngredient){
        RecipeIngredientDTO dto = RecipeIngredientDTO.builder()
                .recipeId(recipeIngredient.getRecipe().getRecipeId())
                .ingredientId(recipeIngredient.getIngredient().getIngredient_id())
                .ingredient_name(recipeIngredient.getIngredient().getName())
                .quantity(recipeIngredient.getQuantity())
                .unit(recipeIngredient.getUnit())
                .build();
        return dto;
    }
}
