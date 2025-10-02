package HealMeals.Api.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import HealMeals.Api.model.Recipe;
import lombok.Builder;

@Entity
@Table(name = "recipe_steps")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecipeStep {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "recipe_id", nullable = false)
    private Recipe recipe;

    @Column(nullable = false, length = 1000)
    private String step;

    @Column(nullable = false, length = 1000)
    private String instruction;

    private int stepOrder;
}
