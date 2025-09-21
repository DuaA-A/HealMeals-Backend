package HealMeals.Api.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {
    private UUID reviewId;
    private UUID userId;
    private UUID recipeId;
    private int rating;
    private String comment;
    private LocalDateTime date;
}
