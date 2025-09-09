package HealMeals.Api.Repo;

import HealMeals.Api.model.ProfileCondition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProfileConditionRepository extends JpaRepository<ProfileCondition, UUID> {
}
