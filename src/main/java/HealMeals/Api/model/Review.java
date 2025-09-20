package HealMeals.Api.model;

import jakarta.persistence.*;
import lombok.*;
<<<<<<< HEAD:Api/src/main/java/HealMeals/Api/model/Review.java

=======
>>>>>>> 06c475a9a31db0eee644f42dae12b23eaec06de3:src/main/java/HealMeals/Api/model/Review.java
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "review")
<<<<<<< HEAD:Api/src/main/java/HealMeals/Api/model/Review.java
@Getter
@Setter
=======
@Data
>>>>>>> 06c475a9a31db0eee644f42dae12b23eaec06de3:src/main/java/HealMeals/Api/model/Review.java
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {
<<<<<<< HEAD:Api/src/main/java/HealMeals/Api/model/Review.java
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID review_id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "recipe_id", nullable = false)
    private Recipe recipe;


    @Column(nullable = false)
    private int rating;


    @Column(nullable = false)
=======

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
>>>>>>> 06c475a9a31db0eee644f42dae12b23eaec06de3:src/main/java/HealMeals/Api/model/Review.java
    private String comment;

    @Column(nullable = false)
    private LocalDateTime date;
<<<<<<< HEAD:Api/src/main/java/HealMeals/Api/model/Review.java

=======
>>>>>>> 06c475a9a31db0eee644f42dae12b23eaec06de3:src/main/java/HealMeals/Api/model/Review.java
}
