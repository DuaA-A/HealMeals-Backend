package HealMeals.Api.Mapper;

import HealMeals.Api.DTO.ProfileConditionDTO;
import HealMeals.Api.model.ProfileCondition;

public class ProfileConditionMapper {
    public static ProfileConditionDTO toDTO(ProfileCondition p) {
        if (p == null) return null;
        return ProfileConditionDTO.builder()
                .conditionId(p.getConditionId())
                .conditionName(p.getConditionName())
                .build();
    }
}
