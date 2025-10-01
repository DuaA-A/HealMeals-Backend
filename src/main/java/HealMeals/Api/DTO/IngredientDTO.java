package HealMeals.Api.DTO;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IngredientDTO {
    private UUID ingredient_id;
    private String name;
    private Boolean isHarmful_flag;
}
