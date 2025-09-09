package HealMeals.Api.Repo;

import HealMeals.Api.model.UserCondition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserConditionRepository extends JpaRepository<UserCondition, UUID> {
}
