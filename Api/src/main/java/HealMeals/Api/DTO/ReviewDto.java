package HealMeals.Api.DTO;

import HealMeals.Api.model.Recipe;
import HealMeals.Api.model.User;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewDto {
    private UUID review_id;
    private UUID user_id;
    private UUID recipe_id;
    private int rating;
    private String comment;
    private LocalDateTime date;
}
