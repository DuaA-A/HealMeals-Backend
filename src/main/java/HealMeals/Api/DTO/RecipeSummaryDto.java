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
public class RecipeSummaryDto {
    private UUID recipeId;
    private String name;
    private String description;
    private Integer prepTimeMinutes;
    private Double averageRating;
}
