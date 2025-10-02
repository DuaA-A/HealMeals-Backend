package HealMeals.Api.DTO;

import lombok.*;

import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecipeSummaryDto {
    private UUID recipeId;
    private String name;
    private String description;
    private Integer prepTimeMinutes;
    private int stars;
}
