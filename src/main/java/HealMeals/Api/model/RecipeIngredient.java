package HealMeals.Api.model;


import jakarta.persistence.*;
import lombok.*;
import lombok.Builder;

import java.util.UUID;

@Entity
@Table(name = "recipe_ingredient")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecipeIngredient {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID recipe_ingredientId;

    @ManyToOne
    @JoinColumn(name = "recipe_id", nullable = false)
    private Recipe recipe;

    @ManyToOne
    @JoinColumn(name = "ingredient_id", nullable = false)
    private Ingredient ingredient;


    @Column(nullable = false)
    private double quantity;

    @Column(nullable = false)
    private String unit;
}
