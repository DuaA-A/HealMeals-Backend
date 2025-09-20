package HealMeals.Api.Repo;

import HealMeals.Api.model.ProfileCondition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProfileConditionRepository extends JpaRepository<ProfileCondition, UUID> {
    boolean existsByConditionName(String conditionName);
    Optional<ProfileCondition> findByConditionName(String conditionName);


}
