package HealMeals.Api.DTO;


import HealMeals.Api.model.ProfileCondition;
import lombok.*;

import java.util.UUID;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
public class UserConditionDTO {
    private UUID id;
    private UUID userId;
    private UUID conditionId;
    private String conditionName;
    private ProfileCondition.ConditionType conditionType;
}
