package HealMeals.Api.service;

import HealMeals.Api.model.ProfileCondition;
import HealMeals.Api.model.User;
import HealMeals.Api.model.UserCondition;
import jakarta.annotation.PostConstruct;
import HealMeals.Api.DTO.UserConditionDTO;
import HealMeals.Api.Repo.ProfileConditionRepository;
import HealMeals.Api.Repo.UserConditionRepository;
import HealMeals.Api.Repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserConditionService {

    private final UserConditionRepository userConditionRepository;
    private final UserRepository userRepository;
    private final ProfileConditionRepository profileConditionRepository;

    public UserConditionDTO addCondition(UUID userId, UUID conditionId) {
        User user = userRepository.findById(userId).orElseThrow();
        ProfileCondition condition = profileConditionRepository.findById(conditionId).orElseThrow();

        UserCondition userCondition = UserCondition.builder()
                .user(user)
                .condition(condition)
                .build();
        userConditionRepository.save(userCondition);

        return mapToDTO(userCondition);
    }

    public List<UserConditionDTO> getAll() {
        return userConditionRepository.findAll().stream().map(this::mapToDTO).toList();
    }

    private UserConditionDTO mapToDTO(UserCondition uc) {
        return UserConditionDTO.builder()
                .id(uc.getId())
                .userId(uc.getUser().getUserId())
                .conditionId(uc.getCondition().getConditionId())
                .conditionName(uc.getCondition().getConditionName())
                .build();
    }
    
    @PostConstruct
    public void initDummyConditions() {
        if (profileConditionRepository.count() == 0) {
            profileConditionRepository.save(ProfileCondition.builder()
                    .conditionName("Diabetes")
                    .build());
            profileConditionRepository.save(ProfileCondition.builder()
                    .conditionName("Hypertension")
                    .build());
        }
    }

}