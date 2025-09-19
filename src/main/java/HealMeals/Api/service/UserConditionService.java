package HealMeals.Api.service;


import HealMeals.Api.DTO.UserConditionDTO;
import HealMeals.Api.Mapper.UserConditionMapper;
import HealMeals.Api.Repo.ProfileConditionRepository;
import HealMeals.Api.Repo.UserConditionRepository;
import HealMeals.Api.Repo.UserRepository;
import HealMeals.Api.model.ProfileCondition;
import HealMeals.Api.model.User;
import HealMeals.Api.model.UserCondition;
import jakarta.annotation.PostConstruct;
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
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        ProfileCondition condition = profileConditionRepository.findById(conditionId)
                .orElseThrow(() -> new RuntimeException("Condition not found"));

        // avoid duplicates
        boolean exists = userConditionRepository.findAll().stream()
                .anyMatch(uc -> uc.getUser().getUserId().equals(userId)
                        && uc.getCondition().getConditionId().equals(conditionId));
        if (exists) {
            throw new IllegalArgumentException("User already has this condition");
        }

        UserCondition userCondition = UserCondition.builder()
                .user(user)
                .condition(condition)
                .build();
        userConditionRepository.save(userCondition);
        return UserConditionMapper.toDTO(userCondition);
    }

    public List<UserConditionDTO> getAll() {
        return userConditionRepository.findAll().stream().map(UserConditionMapper::toDTO).toList();
    }

    public UserConditionDTO getById(UUID id) {
        UserCondition uc = userConditionRepository.findById(id).orElseThrow(() -> new RuntimeException("UserCondition not found"));
        return UserConditionMapper.toDTO(uc);
    }

    public void delete(UUID id) {
        if (!userConditionRepository.existsById(id)) {
            throw new RuntimeException("UserCondition not found");
        }
        userConditionRepository.deleteById(id);
    }

    @PostConstruct
    public void initDummyConditions() {
        if (profileConditionRepository.count() == 0) {
            profileConditionRepository.save(ProfileCondition.builder().conditionName("Diabetes").build());
            profileConditionRepository.save(ProfileCondition.builder().conditionName("Hypertension").build());
        }
    }
}
