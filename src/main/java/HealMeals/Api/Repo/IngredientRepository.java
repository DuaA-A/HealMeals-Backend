package HealMeals.Api.Repo;

import HealMeals.Api.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, UUID> {
        Optional<Ingredient> findByName(String name);
        // optional<Ingredient> existsByName(String name);
}
