package HealMeals.Api.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import HealMeals.Api.DTO.ProfileConditionDTO;
import HealMeals.Api.Mapper.ProfileConditionMapper;
import HealMeals.Api.Repo.ProfileConditionRepository;
import HealMeals.Api.model.ProfileCondition;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfileConditionService {

    private final ProfileConditionRepository repo;

    public ProfileConditionDTO create(ProfileConditionDTO dto) {
        ProfileCondition p = ProfileCondition.builder()
                .conditionName(dto.getConditionName())
                .conditionType(dto.getConditionType()) 
                .build();
        p = repo.save(p);
        return ProfileConditionMapper.toDTO(p);
    }

    public ProfileConditionDTO update(UUID id, ProfileConditionDTO dto) {
        ProfileCondition p = repo.findById(id).orElseThrow(() -> new RuntimeException("Condition not found"));
        p.setConditionName(dto.getConditionName());
        p.setConditionType(dto.getConditionType()); 
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

    public List<ProfileConditionDTO> getByType(ProfileCondition.ConditionType type) {
        return repo.findByConditionType(type).stream()
                .map(ProfileConditionMapper::toDTO)
                .toList();
    }

    public List<ProfileConditionDTO> getAllergies() {
        return repo.findByConditionType(ProfileCondition.ConditionType.ALLERGY)
                .stream()
                .map(ProfileConditionMapper::toDTO)
                .toList();
    }

    public List<ProfileConditionDTO> getDiseases() {
        return repo.findByConditionType(ProfileCondition.ConditionType.DISEASE)
                .stream()
                .map(ProfileConditionMapper::toDTO)
                .toList();
    }

    public void delete(UUID id) {
        repo.deleteById(id);
    }
}
