package HealMeals.Api.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;


@Entity
@Table(
    name = "recipe_ratings",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "recipe_id"})
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecipeRating {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "rating_id", columnDefinition = "BINARY(16)")
    private UUID ratingId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id", nullable = false)
    private Recipe recipe;

    @Column(nullable = false)
    private int stars; // 1–5

    private LocalDateTime ratedAt;
}
