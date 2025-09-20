package HealMeals.Api.Repo;

<<<<<<< HEAD:Api/src/main/java/HealMeals/Api/Repo/ReviewRepository.java
import HealMeals.Api.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReviewRepository extends JpaRepository<Review, UUID> {
=======
import org.springframework.data.jpa.repository.JpaRepository;

import HealMeals.Api.model.Review;

import java.util.List;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, UUID> {
    List<Review> findByUser_UserId(UUID userId);
    // List<Review> findByRecipe_RecipeId(UUID recipeId);
>>>>>>> 06c475a9a31db0eee644f42dae12b23eaec06de3:src/main/java/HealMeals/Api/Repo/ReviewRepository.java
}
