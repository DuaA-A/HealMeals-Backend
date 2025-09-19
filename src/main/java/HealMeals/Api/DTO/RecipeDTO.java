package HealMeals.Api.DTO;

import lombok.*;
import org.springframework.cglib.core.Local;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecipeDTO {
    private UUID recipe_id;
    private String title;
    private String description;
    private LocalTime prepTime;
    private int stars;
    private List<String> steps;
    private List<RecipeIngredientDTO> recipeIngredients;
    private LocalDateTime dateAdded;
    private LocalDateTime dateUpdated;
    private UUID updatedBy;
    private UUID createdBy;
}
