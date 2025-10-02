package HealMeals.Api.DTO;

import lombok.*;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecipeConditionDTO {
    private UUID id;
    private UUID recipeId;
    private UUID conditionId;
    private String conditionName; 
    private boolean safe;
}
