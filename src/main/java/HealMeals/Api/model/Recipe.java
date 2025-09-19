package HealMeals.Api.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "recipe")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "recipe_id", columnDefinition = "BINARY(16)")

    private UUID recipe_id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;


    private LocalTime prepTime;

    @ManyToOne
    @JoinColumn(name = "createdBy")
    @JdbcTypeCode(SqlTypes.BINARY)
    private User createdBy;


    @ManyToOne
    @JoinColumn(name = "updatedBy")
    @JdbcTypeCode(SqlTypes.BINARY)
    private User updatedBy;


    @Column(nullable = false)
    private LocalDateTime dateAdded;


    @Column(nullable = false)
    private LocalDateTime dateUpdated;

    private int stars;


    @ElementCollection
    @CollectionTable(
            name = "recipe_steps",
            joinColumns = @JoinColumn(name = "recipe_id")
    )
    @Column(name = "step", nullable = false)
    private List<String> steps;


    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecipeIngredient> recipeIngredients;
}
