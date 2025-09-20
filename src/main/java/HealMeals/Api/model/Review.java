package HealMeals.Api.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "review")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {

    @Id
    @GeneratedValue
    private UUID reviewId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipeId", nullable = false)
    private Recipe recipe;

    @Column(nullable = false)
    private int rating;  // ideally validate 1-5

    @Column(nullable = false, length = 1000)
    private String comment;

    @Column(nullable = false)
    private LocalDateTime date;
}