package HealMeals.Api.Mapper;

import HealMeals.Api.DTO.IngredientDTO;
import HealMeals.Api.model.Ingredient;

public class IngredientMapper {

    public static IngredientDTO toDTO(Ingredient ingredient) {
        if (ingredient == null) return null;

        IngredientDTO dto = new IngredientDTO();
        dto.setIngredient_id(ingredient.getIngredient_id());
        dto.setName(ingredient.getName());
        dto.setIsHarmful_flag(ingredient.getIsHarmful_flag());
        return dto;
    }

    public static Ingredient toEntity(IngredientDTO dto) {
        if (dto == null) return null;

        Ingredient ingredient = new Ingredient();
        ingredient.setIngredient_id(dto.getIngredient_id());
        ingredient.setName(dto.getName());
        ingredient.setIsHarmful_flag(dto.getIsHarmful_flag());
        return ingredient;
    }
}
