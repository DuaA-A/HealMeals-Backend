// package HealMeals.Api.Controller;

// import HealMeals.Api.model.*;
// import HealMeals.Api.Repo.*;
// import lombok.RequiredArgsConstructor;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;
// import java.util.UUID;

// @RestController
// @RequestMapping("/api/seed")
// @RequiredArgsConstructor
// public class BulkSeedController {

//     private final ProfileConditionRepository profileConditionRepository;
//     private final IngredientRepository ingredientRepository;
//     private final RecipeRepository recipeRepository;
//     private final RecipeIngredientRepository recipeIngredientRepository;
//     private final RecipeStepRepository recipeStepRepository;

//     @PostMapping
//     public ResponseEntity<String> seed(@RequestBody BulkSeedRequest request) {
//         // 1. Save conditions
//         for (BulkSeedRequest.Condition c : request.getConditions()) {
//             if (!profileConditionRepository.existsByConditionName(c.getConditionName())) {
//                 profileConditionRepository.save(ProfileCondition.builder()
//                         .conditionName(c.getConditionName())
//                         .conditionType(c.getConditionType())
//                         .build());
//             }
//         }

//         // 2. Save ingredients
//         request.getIngredients().forEach(i -> {
//             if (!ingredientRepository.existsByName(i)) {
//                 ingredientRepository.save(
//                         Ingredient.builder()
//                                 .ingredient_id(UUID.randomUUID())
//                                 .name(i)
//                                 .isHarmful_flag(false)
//                                 .build()
//                 );
//             }
//         });

//         // 3. Save recipes (with ingredients & steps)
//         request.getRecipes().forEach(r -> {
//             Recipe recipe = recipeRepository.save(
//                     Recipe.builder()
//                             .name(r.getName())
//                             .description(r.getDescription())
//                             .summary(r.getSummary())
//                             .prepTimeMinutes(r.getPrepTimeMinutes())
//                             .build()
//             );

//             r.getIngredients().forEach(ing -> {
//                 ingredientRepository.findByName(ing.getName()).ifPresent(found ->
//                         recipeIngredientRepository.save(
//                                 RecipeIngredient.builder()
//                                         .recipe(recipe)
//                                         .ingredient(found)
//                                         .quantity(ing.getQuantity())
//                                         .unit(ing.getUnit())
//                                         .build()
//                         )
//                 );
//             });

//             int order = 1;
//             for (String step : r.getSteps()) {
//                 recipeStepRepository.save(
//                         RecipeStep.builder()
//                                 .recipe(recipe)
//                                 .step(step)
//                                 .stepOrder(order++)
//                                 .build()
//                 );
//             }
//         });

//         return ResponseEntity.ok(" Bulk seed completed");
//     }
// }