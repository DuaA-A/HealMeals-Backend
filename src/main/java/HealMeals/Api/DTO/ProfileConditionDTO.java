package HealMeals.Api.DTO;

import java.util.UUID;

import HealMeals.Api.model.ProfileCondition;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Data;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ProfileConditionDTO {
    private UUID conditionId;
    private String conditionName;
    private ProfileCondition.ConditionType conditionType;
}
