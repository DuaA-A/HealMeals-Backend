package HealMeals.Api.DTO;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecipeIngredientDTO {
    private UUID ingredientId;  
    private UUID recipeId;      
    private UUID recipeIngredientId;  
    private String ingredient_name;
    private double quantity;
    private String unit;
}
