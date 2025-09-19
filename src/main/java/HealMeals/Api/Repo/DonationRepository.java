package HealMeals.Api.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import HealMeals.Api.model.Donation;

import java.util.List;
import java.util.UUID;

public interface DonationRepository extends JpaRepository<Donation, UUID> {
    List<Donation> findByUser_UserId(UUID userId);
}
