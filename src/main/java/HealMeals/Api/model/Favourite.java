package HealMeals.Api.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

import HealMeals.Api.model.Recipe;
import HealMeals.Api.model.User;

@Entity
@Table(name = "favourites",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "recipe_id"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Favourite {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "favourite_id", columnDefinition = "BINARY(16)")
    private UUID favouriteId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id", nullable = false)
    private Recipe recipe;

    private LocalDateTime addedAt;
}
