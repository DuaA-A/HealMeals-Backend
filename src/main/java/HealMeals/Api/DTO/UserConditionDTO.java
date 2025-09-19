package HealMeals.Api.DTO;


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
}
