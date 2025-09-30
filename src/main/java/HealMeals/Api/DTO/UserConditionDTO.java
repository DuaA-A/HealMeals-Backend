package HealMeals.Api.DTO;


import HealMeals.Api.model.ProfileCondition;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserConditionDTO {
    private UUID id;
    private UUID userId;
    private UUID conditionId;
    private String conditionName;
    private ProfileCondition.ConditionType conditionType;
}
