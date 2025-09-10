package HealMeals.Api.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "ingredient")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID ingredient_id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Boolean isHarmful_flag;
}
