package HealMeals.Api.Mapper;

import HealMeals.Api.DTO.UserConditionDTO;
import HealMeals.Api.model.UserCondition;

public class UserConditionMapper {
    public static UserConditionDTO toDTO(UserCondition uc) {
        if (uc == null) return null;
        return UserConditionDTO.builder()
                .id(uc.getId())
                .userId(uc.getUser().getUserId())
                .conditionId(uc.getCondition().getConditionId())
                .conditionName(uc.getCondition().getConditionName())
                .build();
    }
}
