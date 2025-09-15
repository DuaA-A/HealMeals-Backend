package HealMeals.Api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.util.UUID;

@Entity
@Table(name = "profile_conditions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileCondition {

    @Id
    @GeneratedValue
    private UUID conditionId;

    private String conditionName;

    @Enumerated(EnumType.STRING)
    private ConditionType conditionType; 

    public enum ConditionType {
        DISEASE,
        ALLERGY
    }
}