package HealMeals.Api.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@Table(name = "recipe_conditions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecipeCondition {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "recipe_condition_id", updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "recipe_id", nullable = false)
    private Recipe recipe;

    @ManyToOne(optional = false)
    @JoinColumn(name = "condition_id", nullable = false)
    private ProfileCondition condition;

    private boolean safe;

}