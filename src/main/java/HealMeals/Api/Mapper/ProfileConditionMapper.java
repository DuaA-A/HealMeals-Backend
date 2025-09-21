package HealMeals.Api.Mapper;

import HealMeals.Api.DTO.ProfileConditionDTO;
import HealMeals.Api.model.ProfileCondition;

public class ProfileConditionMapper {
    public static ProfileConditionDTO toDTO(ProfileCondition p) {
        return ProfileConditionDTO.builder()
                .conditionId(p.getConditionId())
                .conditionName(p.getConditionName())
                .conditionType(p.getConditionType())
                .build();
    }

    public static ProfileCondition toEntity(ProfileConditionDTO dto) {
        return ProfileCondition.builder()
                .conditionId(dto.getConditionId())
                .conditionName(dto.getConditionName())
                .conditionType(dto.getConditionType())
                .build();
    }
}
