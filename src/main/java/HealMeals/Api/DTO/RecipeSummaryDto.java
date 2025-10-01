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
    private UUID recipe_id;
    private String title;
    private String description;
    private LocalTime prepTime;
    private int stars;
}
