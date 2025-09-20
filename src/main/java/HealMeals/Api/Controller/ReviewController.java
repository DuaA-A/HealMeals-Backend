package HealMeals.Api.Controller;

<<<<<<< HEAD:Api/src/main/java/HealMeals/Api/Controller/ReviewController.java
import HealMeals.Api.DTO.ReviewDto;
import HealMeals.Api.service.RecipeService;
import HealMeals.Api.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/review")
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ReviewDto> createReview(@RequestBody ReviewDto reviewDto){
        return ResponseEntity.ok(reviewService.createReview(reviewDto));
=======

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import HealMeals.Api.DTO.ReviewDTO;
import HealMeals.Api.service.ReviewService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ReviewDTO> create(@RequestBody ReviewDTO dto) {
        return ResponseEntity.ok(reviewService.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<ReviewDTO>> getAll() {
        return ResponseEntity.ok(reviewService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewDTO> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(reviewService.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        reviewService.delete(id);
        return ResponseEntity.noContent().build();
>>>>>>> 06c475a9a31db0eee644f42dae12b23eaec06de3:src/main/java/HealMeals/Api/Controller/ReviewController.java
    }
}
