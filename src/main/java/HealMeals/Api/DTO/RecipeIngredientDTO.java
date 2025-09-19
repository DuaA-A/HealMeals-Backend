package HealMeals.Api.DTO;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecipeIngredientDTO {
    private UUID recipe_ingredientId;
    private UUID recipe_id;
    private UUID ingredient_id;
    private double quantity;
    private String unit;
}
