package HealMeals.Api.DTO;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.Data;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecipeDTO {
    private UUID recipeId;
    
    private String name;
    
    private String description;
    private String summary;

    private Integer prepTimeMinutes;

    private Double averageRating;

    private List<String> steps;

    private List<RecipeIngredientDTO> recipeIngredients;

    private LocalDateTime dateAdded;
    private LocalDateTime dateUpdated;

    private UUID createdBy;
    private UUID updatedBy;
}
