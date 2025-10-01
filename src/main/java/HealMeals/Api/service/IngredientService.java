package HealMeals.Api.service;

import HealMeals.Api.DTO.IngredientDTO;
import HealMeals.Api.Mapper.IngredientMapper;
import HealMeals.Api.model.Ingredient;
import HealMeals.Api.Repo.IngredientRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IngredientService {

    private final IngredientRepository ingredientRepository;

    public IngredientDTO createIngredient(IngredientDTO dto) {
        Ingredient ingredient = IngredientMapper.toEntity(dto);
        Ingredient saved = ingredientRepository.save(ingredient);
        return IngredientMapper.toDTO(saved);
    }

    public List<IngredientDTO> getAllIngredients() {
        return ingredientRepository.findAll().stream()
                .map(IngredientMapper::toDTO)
                .collect(Collectors.toList());
    }

    public IngredientDTO getIngredientById(UUID id) {
        Ingredient ingredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ingredient not found"));
        return IngredientMapper.toDTO(ingredient);
    }

    public IngredientDTO updateIngredient(UUID id, IngredientDTO dto) {
        Ingredient existing = ingredientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ingredient not found"));

        existing.setName(dto.getName());
        existing.setIsHarmful_flag(dto.getIsHarmful_flag());

        Ingredient updated = ingredientRepository.save(existing);
        return IngredientMapper.toDTO(updated);
    }

    public void deleteIngredient(UUID id) {
        if (!ingredientRepository.existsById(id)) {
            throw new EntityNotFoundException("Ingredient not found");
        }
        ingredientRepository.deleteById(id);
    }
}
