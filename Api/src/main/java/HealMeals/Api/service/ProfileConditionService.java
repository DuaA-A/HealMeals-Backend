package HealMeals.Api.service;

import HealMeals.Api.DTO.ProfileConditionDTO;
import HealMeals.Api.Repo.ProfileConditionRepository;
import HealMeals.Api.model.ProfileCondition;
import HealMeals.Api.Mapper.ProfileConditionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileConditionService {

    private final ProfileConditionRepository repo;

    public ProfileConditionDTO create(ProfileConditionDTO dto) {
        ProfileCondition p = ProfileCondition.builder()
                .conditionName(dto.getConditionName())
                .build();
        p = repo.save(p);
        return ProfileConditionMapper.toDTO(p);
    }

    public List<ProfileConditionDTO> getAll() {
        return repo.findAll().stream().map(ProfileConditionMapper::toDTO).toList();
    }

    public ProfileConditionDTO getById(UUID id) {
        ProfileCondition p = repo.findById(id).orElseThrow(() -> new RuntimeException("Condition not found"));
        return ProfileConditionMapper.toDTO(p);
    }

    public ProfileConditionDTO update(UUID id, ProfileConditionDTO dto) {
        ProfileCondition p = repo.findById(id).orElseThrow(() -> new RuntimeException("Condition not found"));
        p.setConditionName(dto.getConditionName());
        p = repo.save(p);
        return ProfileConditionMapper.toDTO(p);
    }

    public void delete(UUID id) {
        repo.deleteById(id);
    }
}
