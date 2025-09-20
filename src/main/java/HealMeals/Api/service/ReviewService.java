package HealMeals.Api.service;

<<<<<<< HEAD:Api/src/main/java/HealMeals/Api/service/ReviewService.java
import HealMeals.Api.DTO.ReviewDto;
import HealMeals.Api.Mapper.ReviewMapper;
=======
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import HealMeals.Api.DTO.ReviewDTO;
>>>>>>> 06c475a9a31db0eee644f42dae12b23eaec06de3:src/main/java/HealMeals/Api/service/ReviewService.java
import HealMeals.Api.Repo.RecipeRepository;
import HealMeals.Api.Repo.ReviewRepository;
import HealMeals.Api.Repo.UserRepository;
import HealMeals.Api.model.Recipe;
import HealMeals.Api.model.Review;
import HealMeals.Api.model.User;
<<<<<<< HEAD:Api/src/main/java/HealMeals/Api/service/ReviewService.java
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
=======

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
>>>>>>> 06c475a9a31db0eee644f42dae12b23eaec06de3:src/main/java/HealMeals/Api/service/ReviewService.java

@Service
@RequiredArgsConstructor
public class ReviewService {
<<<<<<< HEAD:Api/src/main/java/HealMeals/Api/service/ReviewService.java
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private final RecipeRepository recipeRepository;

    public ReviewDto createReview (ReviewDto dto) {
        Recipe recipe = recipeRepository.findById(dto.getRecipe_id())
                .orElseThrow(()->new RuntimeException("Recipe Not Found"));

        User user = userRepository.findById(dto.getUser_id())
                .orElseThrow(()->new RuntimeException("User Not Found"));

        Review review = ReviewMapper.toEntity(dto, user, recipe);
        return ReviewMapper.toDto(reviewRepository.save(review));
=======

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;

    public ReviewDTO create(ReviewDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Recipe recipe = recipeRepository.findById(dto.getRecipeId())
                .orElseThrow(() -> new RuntimeException("Recipe not found"));

        Review review = Review.builder()
                .user(user)
                .recipe(recipe)
                .rating(dto.getRating())
                .comment(dto.getComment())
                .date(LocalDateTime.now())
                .build();

        return toDTO(reviewRepository.save(review));
    }

    public List<ReviewDTO> getAll() {
        return reviewRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public ReviewDTO getById(UUID id) {
        return reviewRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new RuntimeException("Review not found"));
    }

    public void delete(UUID id) {
        reviewRepository.deleteById(id);
    }

    private ReviewDTO toDTO(Review review) {
        ReviewDTO dto = new ReviewDTO();
        dto.setReviewId(review.getReviewId());
        dto.setUserId(review.getUser().getUserId());
        dto.setRecipeId(review.getRecipe().getRecipe_id());
        dto.setRating(review.getRating());
        dto.setComment(review.getComment());
        dto.setDate(review.getDate());
        return dto;
>>>>>>> 06c475a9a31db0eee644f42dae12b23eaec06de3:src/main/java/HealMeals/Api/service/ReviewService.java
    }
}
