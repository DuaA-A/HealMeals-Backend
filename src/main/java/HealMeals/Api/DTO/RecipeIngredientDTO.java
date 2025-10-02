package HealMeals.Api.DTO;

import lombok.*;

import java.util.UUID;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Data;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
// @Data
public class RecipeIngredientDTO {
    private UUID ingredientId;  
    private UUID recipeId;      
    private UUID recipeIngredientId;  
    private String ingredient_name;
    private double quantity;
    private String unit;
}
