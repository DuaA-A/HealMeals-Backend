package HealMeals.Api.Repo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import HealMeals.Api.model.ProfileCondition;

public interface ProfileConditionRepository extends JpaRepository<ProfileCondition, UUID> {
    boolean existsByConditionName(String conditionName);
    Optional<ProfileCondition> findByConditionName(String conditionName);
    List<ProfileCondition> findByConditionType(ProfileCondition.ConditionType type);


}
