package HealMeals.Api.DTO;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ReviewDTO {
    private UUID reviewId;
    private UUID userId;
    private UUID recipeId;
    private int rating;
    private String comment;
    private LocalDateTime date;
}