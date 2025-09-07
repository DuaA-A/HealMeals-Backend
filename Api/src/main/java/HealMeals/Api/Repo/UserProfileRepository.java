package HealMeals.Api.Repo;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import HealMeals.Api.model.UserProfile;

public interface UserProfileRepository extends JpaRepository<UserProfile, UUID> {
}
