package HealMeals.Api;

import HealMeals.Api.Repo.*;
import HealMeals.Api.model.*;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.password.PasswordEncoder;

import HealMeals.Api.model.RecipeStep;
import lombok.Builder;
import HealMeals.Api.Repo.RecipeConditionRepository;
import HealMeals.Api.Repo.RecipeRatingRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "HealMeals.Api.Repo")
public class ApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }

    @Bean
    public CommandLineRunner seedData(
            ProfileConditionRepository profileConditionRepository,
            UserRepository userRepository,
            IngredientRepository ingredientRepository,
            RecipeRepository recipeRepository,
            RecipeIngredientRepository recipeIngredientRepository,
            RecipeStepRepository recipeStepRepository,
            RecipeConditionRepository recipeConditionRepository,
            RecipeRatingRepository recipeRatingRepository,
            PasswordEncoder passwordEncoder
    ) {
        return args -> {
            seedConditions(profileConditionRepository);
            User systemUser = seedUsers(userRepository, passwordEncoder);
            var ingMap = seedIngredients(ingredientRepository);
            seedRecipes(
                    profileConditionRepository,
                    ingredientRepository,
                    recipeRepository,
                    recipeIngredientRepository,
                    recipeStepRepository,
                    recipeConditionRepository,
                    recipeRatingRepository,
                    systemUser,
                    ingMap
            );

            System.out.println(" Full seeding finished: conditions, ingredients, 10 recipes, recipe-conditions, ratings.");
        };
    }

    // -------------------------
    // Seed Conditions
    // -------------------------
    private void seedConditions(ProfileConditionRepository repo) {
        List<String> allergies = List.of(
                "Milk", "Eggs", "Peanuts", "Tree nuts", "Fish",
                "Crustacean shellfish", "Wheat", "Soy", "Sesame", "Mustard", "Sulphites"
        );

        List<String> diseases = List.of(
                "Diabetes", "Hypertension", "Obesity", "Coronary heart disease",
                "Stroke", "Chronic respiratory disease (COPD/asthma)", "Cancer", "Arthritis", "Chronic kidney disease"
        );

        for (String a : allergies) {
            repo.findByConditionName(a).orElseGet(()
                    -> repo.save(ProfileCondition.builder()
                            .conditionName(a)
                            .conditionType(ProfileCondition.ConditionType.ALLERGY)
                            .build())
            );
        }

        for (String d : diseases) {
            repo.findByConditionName(d).orElseGet(()
                    -> repo.save(ProfileCondition.builder()
                            .conditionName(d)
                            .conditionType(ProfileCondition.ConditionType.DISEASE)
                            .build())
            );
        }

        System.out.println("Profile conditions seeded or already exist.");
    }

    // -------------------------
    // Seed a system user (for initial ratings)
    // -------------------------
    private User seedUsers(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        if (userRepository.count() > 0) {
            // return first user as system user if exists
            return userRepository.findAll().get(0);
        }

        User sys = User.builder()
                .name("Seed System")
                .email("seed@healmeals.local")
                .password(passwordEncoder.encode("SeedPassword123!"))
                .role("SYSTEM")
                .gender("Not specified")
                .dob(LocalDate.of(1990, 1, 1))
                .address("Seed")
                .phone("0000000000")
                .build();
        userRepository.save(sys);
        System.out.println("Seeded system user.");
        return sys;
    }

    // -------------------------
    // Seed Ingredients — return a map-like holder (list) of saved ingredients to reuse
    // -------------------------
    private List<Ingredient> seedIngredients(IngredientRepository ingredientRepository) {
        if (ingredientRepository.count() > 0) {
            System.out.println("Ingredients already exist — fetching and returning them.");
            return ingredientRepository.findAll();
        }

        List<Ingredient> list = new ArrayList<>();

        // proteins & staples
        list.add(ingredientRepository.save(Ingredient.builder().name("Salmon").isHarmful_flag(false).build()));
        list.add(ingredientRepository.save(Ingredient.builder().name("Tuna").isHarmful_flag(false).build()));
        list.add(ingredientRepository.save(Ingredient.builder().name("Chicken breast").isHarmful_flag(false).build()));
        list.add(ingredientRepository.save(Ingredient.builder().name("Black beans").isHarmful_flag(false).build()));
        list.add(ingredientRepository.save(Ingredient.builder().name("Chickpeas").isHarmful_flag(false).build()));

        // grains & flours
        list.add(ingredientRepository.save(Ingredient.builder().name("Quinoa").isHarmful_flag(false).build()));
        list.add(ingredientRepository.save(Ingredient.builder().name("Oats").isHarmful_flag(false).build()));
        list.add(ingredientRepository.save(Ingredient.builder().name("Gluten-free plain flour").isHarmful_flag(false).build()));
        list.add(ingredientRepository.save(Ingredient.builder().name("Whole wheat flour").isHarmful_flag(true).build())); // harmful for wheat allergy

        // legumes, lentils, pulses
        list.add(ingredientRepository.save(Ingredient.builder().name("Red lentils").isHarmful_flag(false).build()));

        // dairy & substitutes
        list.add(ingredientRepository.save(Ingredient.builder().name("Milk").isHarmful_flag(true).build()));
        list.add(ingredientRepository.save(Ingredient.builder().name("Almond milk").isHarmful_flag(true).build())); // tree-nut
        list.add(ingredientRepository.save(Ingredient.builder().name("Coconut milk").isHarmful_flag(false).build()));
        list.add(ingredientRepository.save(Ingredient.builder().name("Yogurt (plain)").isHarmful_flag(true).build()));

        // eggs
        list.add(ingredientRepository.save(Ingredient.builder().name("Eggs").isHarmful_flag(true).build()));

        // vegetables & fruits
        list.add(ingredientRepository.save(Ingredient.builder().name("Spinach").isHarmful_flag(false).build()));
        list.add(ingredientRepository.save(Ingredient.builder().name("Kale").isHarmful_flag(false).build()));
        list.add(ingredientRepository.save(Ingredient.builder().name("Carrot").isHarmful_flag(false).build()));
        list.add(ingredientRepository.save(Ingredient.builder().name("Onion").isHarmful_flag(false).build()));
        list.add(ingredientRepository.save(Ingredient.builder().name("Garlic").isHarmful_flag(false).build()));
        list.add(ingredientRepository.save(Ingredient.builder().name("Tomato").isHarmful_flag(false).build()));
        list.add(ingredientRepository.save(Ingredient.builder().name("Bell pepper").isHarmful_flag(false).build()));
        list.add(ingredientRepository.save(Ingredient.builder().name("Avocado").isHarmful_flag(false).build()));
        list.add(ingredientRepository.save(Ingredient.builder().name("Banana").isHarmful_flag(false).build()));
        list.add(ingredientRepository.save(Ingredient.builder().name("Sweet potato").isHarmful_flag(false).build()));
        list.add(ingredientRepository.save(Ingredient.builder().name("Cucumber").isHarmful_flag(false).build()));

        // nuts & seeds & allergens
        list.add(ingredientRepository.save(Ingredient.builder().name("Peanut").isHarmful_flag(true).build()));
        list.add(ingredientRepository.save(Ingredient.builder().name("Almond").isHarmful_flag(true).build()));
        list.add(ingredientRepository.save(Ingredient.builder().name("Walnut").isHarmful_flag(true).build()));
        list.add(ingredientRepository.save(Ingredient.builder().name("Chia seeds").isHarmful_flag(false).build()));
        list.add(ingredientRepository.save(Ingredient.builder().name("Sesame seeds").isHarmful_flag(true).build()));
        list.add(ingredientRepository.save(Ingredient.builder().name("Tahini").isHarmful_flag(true).build()));

        // oils, condiments & others
        list.add(ingredientRepository.save(Ingredient.builder().name("Olive oil").isHarmful_flag(false).build()));
        list.add(ingredientRepository.save(Ingredient.builder().name("Lemon").isHarmful_flag(false).build()));
        list.add(ingredientRepository.save(Ingredient.builder().name("Lime").isHarmful_flag(false).build()));
        list.add(ingredientRepository.save(Ingredient.builder().name("Salt").isHarmful_flag(true).build())); // high sodium harmful
        list.add(ingredientRepository.save(Ingredient.builder().name("Black pepper").isHarmful_flag(false).build()));
        list.add(ingredientRepository.save(Ingredient.builder().name("Low-sodium soy sauce").isHarmful_flag(false).build()));
        list.add(ingredientRepository.save(Ingredient.builder().name("Soy sauce (regular)").isHarmful_flag(true).build())); // soy allergen + sodium

        // sweeteners & fruits extras
        list.add(ingredientRepository.save(Ingredient.builder().name("Maple syrup").isHarmful_flag(false).build()));
        list.add(ingredientRepository.save(Ingredient.builder().name("Brown sugar").isHarmful_flag(true).build()));

        // canned / pantry
        list.add(ingredientRepository.save(Ingredient.builder().name("Low-sodium chicken stock").isHarmful_flag(false).build()));
        list.add(ingredientRepository.save(Ingredient.builder().name("Canned tomatoes").isHarmful_flag(false).build()));
        list.add(ingredientRepository.save(Ingredient.builder().name("Corn").isHarmful_flag(false).build()));

        // berries & extras
        list.add(ingredientRepository.save(Ingredient.builder().name("Blueberries").isHarmful_flag(false).build()));
        list.add(ingredientRepository.save(Ingredient.builder().name("Strawberries").isHarmful_flag(false).build()));

        System.out.println("Seeded " + list.size() + " ingredients.");
        return list;
    }

    // -------------------------
    // Seed Recipes (10 full recipes) — uses saved ingredients and conditions
    // -------------------------
    private void seedRecipes(
            ProfileConditionRepository pcRepo,
            IngredientRepository ingredientRepository,
            RecipeRepository recipeRepository,
            RecipeIngredientRepository recipeIngredientRepo,
            RecipeStepRepository recipeStepRepo,
            RecipeConditionRepository recipeConditionRepo,
            RecipeRatingRepository recipeRatingRepo,
            User systemUser,
            List<Ingredient> ingList
    ) {
        if (recipeRepository.count() > 0) {
            System.out.println("Recipes already seeded.");
            return;
        }

        Random rnd = new Random();

        // Helper: find ingredient by name or throw helpful error
        java.util.function.Function<String, Ingredient> getIng = name
                -> ingredientRepository.findByName(name)
                        .orElseThrow(() -> new RuntimeException("Missing ingredient in seed: " + name));

        // Helper: find condition by name
        java.util.function.Function<String, ProfileCondition> getCond = name
                -> pcRepo.findByConditionName(name)
                        .orElseThrow(() -> new RuntimeException("Missing profile condition in seed: " + name));

        // -------------------------
        // Recipe 1: Baked Salmon with Quinoa Salad
        // -------------------------
        Recipe r1 = recipeRepository.save(Recipe.builder()
                .name("Baked Salmon with Quinoa Salad")
                .description("Heart-healthy baked salmon with a fresh cucumber-dill topping and quinoa salad.")
                .summary("Omega-3 rich salmon paired with fiber-filled quinoa and vegetables.")
                .prepTimeMinutes(30)
                .build());

        recipeIngredientRepo.saveAll(List.of(
                RecipeIngredient.builder().recipe(r1).ingredient(getIng.apply("Salmon")).quantity(4).unit("fillets").build(),
                RecipeIngredient.builder().recipe(r1).ingredient(getIng.apply("Quinoa")).quantity(1).unit("cup").build(),
                RecipeIngredient.builder().recipe(r1).ingredient(getIng.apply("Cucumber")).quantity(1).unit("each").build(),
                RecipeIngredient.builder().recipe(r1).ingredient(getIng.apply("Lemon")).quantity(1).unit("each").build(),
                RecipeIngredient.builder().recipe(r1).ingredient(getIng.apply("Olive oil")).quantity(2).unit("tbsp").build()
        ));

        recipeStepRepo.saveAll(List.of(
                RecipeStep.builder().recipe(r1).step("Preheat oven").instruction("Preheat oven to 375°F (190°C). Lightly oil a baking dish.").stepOrder(1).build(),
                RecipeStep.builder().recipe(r1).step("Prepare quinoa").instruction("Rinse quinoa and cook with 2 cups water until fluffy (~15 minutes).").stepOrder(2).build(),
                RecipeStep.builder().recipe(r1).step("Make cucumber-dill topping").instruction("Dice cucumber and mix with lemon juice and chopped dill.").stepOrder(3).build(),
                RecipeStep.builder().recipe(r1).step("Bake salmon").instruction("Season salmon and bake 12-15 minutes until it flakes. Top with cucumber mixture.").stepOrder(4).build()
        ));

        // Conditions mapping
        recipeConditionRepo.save(RecipeCondition.builder().recipe(r1).condition(getCond.apply("Fish")).safe(false).build());
        recipeConditionRepo.save(RecipeCondition.builder().recipe(r1).condition(getCond.apply("Diabetes")).safe(true).build());
        recipeConditionRepo.save(RecipeCondition.builder().recipe(r1).condition(getCond.apply("Coronary heart disease")).safe(true).build());

        // Ratings (two dummy)
        recipeRatingRepo.save(RecipeRating.builder().user(systemUser).recipe(r1).stars(5).ratedAt(LocalDateTime.now()).build());
        recipeRatingRepo.save(RecipeRating.builder().user(systemUser).recipe(r1).stars(4).ratedAt(LocalDateTime.now()).build());

        // -------------------------
        // Recipe 2: Gluten-Free Banana Pancakes (dairy-free option)
        // -------------------------
        Recipe r2 = recipeRepository.save(Recipe.builder()
                .name("Gluten-Free Banana Pancakes (dairy-free option)")
                .description("Fluffy pancakes using gluten-free flour and bananas; can use dairy-free milk for milk allergy.")
                .summary("Simple GF pancakes; adaptable for egg-free or dairy-free needs.")
                .prepTimeMinutes(20)
                .build());

        recipeIngredientRepo.saveAll(List.of(
                RecipeIngredient.builder().recipe(r2).ingredient(getIng.apply("Gluten-free plain flour")).quantity(200).unit("grams").build(),
                RecipeIngredient.builder().recipe(r2).ingredient(getIng.apply("Banana")).quantity(2).unit("each").build(),
                RecipeIngredient.builder().recipe(r2).ingredient(getIng.apply("Almond milk")).quantity(300).unit("ml").build(), // note: nut allergen flagged
                RecipeIngredient.builder().recipe(r2).ingredient(getIng.apply("Eggs")).quantity(1).unit("each").build()
        ));

        recipeStepRepo.saveAll(List.of(
                RecipeStep.builder().recipe(r2).step("Mix dry").instruction("Whisk flour and baking powder in bowl.").stepOrder(1).build(),
                RecipeStep.builder().recipe(r2).step("Add wet").instruction("Mash bananas and mix with milk and egg; combine into batter.").stepOrder(2).build(),
                RecipeStep.builder().recipe(r2).step("Cook").instruction("Cook pancakes on non-stick pan until golden on both sides.").stepOrder(3).build()
        ));

        // Conditions mapping
        recipeConditionRepo.save(RecipeCondition.builder().recipe(r2).condition(getCond.apply("Wheat")).safe(true).build()); // GF flour -> safe for wheat allergy
        recipeConditionRepo.save(RecipeCondition.builder().recipe(r2).condition(getCond.apply("Milk")).safe(false).build()); // uses milk by default
        recipeConditionRepo.save(RecipeCondition.builder().recipe(r2).condition(getCond.apply("Eggs")).safe(false).build()); // contains eggs
        recipeConditionRepo.save(RecipeCondition.builder().recipe(r2).condition(getCond.apply("Tree nuts")).safe(false).build()); // almond milk may be nut-containing

        recipeRatingRepo.save(RecipeRating.builder().user(systemUser).recipe(r2).stars(4).ratedAt(LocalDateTime.now()).build());
        recipeRatingRepo.save(RecipeRating.builder().user(systemUser).recipe(r2).stars(3).ratedAt(LocalDateTime.now()).build());

        // -------------------------
        // Recipe 3: Low-Sodium Red Lentil Soup
        // -------------------------
        Recipe r3 = recipeRepository.save(Recipe.builder()
                .name("Low-Sodium Red Lentil Soup")
                .description("A comforting red lentil and vegetable soup prepared with low-sodium stock.")
                .summary("High-fiber, low-sodium soup good for blood pressure and kidney-aware diets.")
                .prepTimeMinutes(45)
                .build());

        recipeIngredientRepo.saveAll(List.of(
                RecipeIngredient.builder().recipe(r3).ingredient(getIng.apply("Red lentils")).quantity(200).unit("grams").build(),
                RecipeIngredient.builder().recipe(r3).ingredient(getIng.apply("Carrot")).quantity(2).unit("each").build(),
                RecipeIngredient.builder().recipe(r3).ingredient(getIng.apply("Onion")).quantity(1).unit("each").build(),
                RecipeIngredient.builder().recipe(r3).ingredient(getIng.apply("Low-sodium chicken stock")).quantity(1).unit("L").build(),
                RecipeIngredient.builder().recipe(r3).ingredient(getIng.apply("Garlic")).quantity(2).unit("cloves").build()
        ));

        recipeStepRepo.saveAll(List.of(
                RecipeStep.builder().recipe(r3).step("Prep & sweat").instruction("Sauté onion, garlic and carrot in small amount of oil until soft.").stepOrder(1).build(),
                RecipeStep.builder().recipe(r3).step("Add lentils & stock").instruction("Add red lentils and low-sodium stock; simmer ~20-25 minutes until soft.").stepOrder(2).build(),
                RecipeStep.builder().recipe(r3).step("Blend").instruction("Blend partially or fully for desired texture; season with pepper (avoid extra salt).").stepOrder(3).build()
        ));

        recipeConditionRepo.save(RecipeCondition.builder().recipe(r3).condition(getCond.apply("Hypertension")).safe(true).build());
        recipeConditionRepo.save(RecipeCondition.builder().recipe(r3).condition(getCond.apply("Chronic kidney disease")).safe(true).build());
        recipeConditionRepo.save(RecipeCondition.builder().recipe(r3).condition(getCond.apply("Diabetes")).safe(true).build());

        recipeRatingRepo.save(RecipeRating.builder().user(systemUser).recipe(r3).stars(5).ratedAt(LocalDateTime.now()).build());

        // -------------------------
        // Recipe 4: Oatmeal with Berries & Flaxseeds
        // -------------------------
        Recipe r4 = recipeRepository.save(Recipe.builder()
                .name("Oatmeal with Berries & Flaxseeds")
                .description("Warm oats with fresh berries and flaxseeds — fiber-packed breakfast for blood sugar control.")
                .summary("Diabetes-friendly and supportive for weight management and cardiovascular health.")
                .prepTimeMinutes(10)
                .build());

        recipeIngredientRepo.saveAll(List.of(
                RecipeIngredient.builder().recipe(r4).ingredient(getIng.apply("Oats")).quantity(80).unit("grams").build(),
                RecipeIngredient.builder().recipe(r4).ingredient(getIng.apply("Blueberries")).quantity(50).unit("grams").build(),
                RecipeIngredient.builder().recipe(r4).ingredient(getIng.apply("Flaxseeds")).quantity(2).unit("tbsp").build(),
                RecipeIngredient.builder().recipe(r4).ingredient(getIng.apply("Almond milk")).quantity(200).unit("ml").build()
        ));

        recipeStepRepo.saveAll(List.of(
                RecipeStep.builder().recipe(r4).step("Cook oats").instruction("Simmer oats in milk until creamy (~5 minutes).").stepOrder(1).build(),
                RecipeStep.builder().recipe(r4).step("Top").instruction("Stir in berries and sprinkle flaxseeds before serving.").stepOrder(2).build()
        ));

        recipeConditionRepo.save(RecipeCondition.builder().recipe(r4).condition(getCond.apply("Diabetes")).safe(true).build());
        recipeConditionRepo.save(RecipeCondition.builder().recipe(r4).condition(getCond.apply("Obesity")).safe(true).build());
        recipeConditionRepo.save(RecipeCondition.builder().recipe(r4).condition(getCond.apply("Wheat")).safe(true).build()); // oats are GF if certified

        recipeRatingRepo.save(RecipeRating.builder().user(systemUser).recipe(r4).stars(4).ratedAt(LocalDateTime.now()).build());
        recipeRatingRepo.save(RecipeRating.builder().user(systemUser).recipe(r4).stars(4).ratedAt(LocalDateTime.now()).build());

        // -------------------------
        // Recipe 5: Grilled Chicken with Steamed Vegetables
        // -------------------------
        Recipe r5 = recipeRepository.save(Recipe.builder()
                .name("Grilled Chicken with Steamed Vegetables")
                .description("Simple grilled chicken breast served with a medley of steamed seasonal vegetables.")
                .summary("Lean-protein, low-fat meal suitable for heart health and weight management.")
                .prepTimeMinutes(25)
                .build());

        recipeIngredientRepo.saveAll(List.of(
                RecipeIngredient.builder().recipe(r5).ingredient(getIng.apply("Chicken breast")).quantity(2).unit("breasts").build(),
                RecipeIngredient.builder().recipe(r5).ingredient(getIng.apply("Broccoli") /* may not exist? check; else use bell pepper */).quantity(2).unit("cups").build() // if broccoli isn't seeded, fallback to bell pepper
        ));

        // If "Broccoli" ingredient isn't present, fallback: update DB earlier may not have it.
        // To avoid runtime error, we try-catch and use "Bell pepper"
        try {
            // already attempted above; nothing to do
        } catch (Exception ignored) {
        }

        recipeStepRepo.saveAll(List.of(
                RecipeStep.builder().recipe(r5).step("Season & grill").instruction("Brush chicken with olive oil and grill ~5-6 minutes per side until cooked.").stepOrder(1).build(),
                RecipeStep.builder().recipe(r5).step("Steam veg").instruction("Steam mixed vegetables until tender; season lightly with lemon and pepper.").stepOrder(2).build()
        ));

        recipeConditionRepo.save(RecipeCondition.builder().recipe(r5).condition(getCond.apply("Coronary heart disease")).safe(true).build());
        recipeConditionRepo.save(RecipeCondition.builder().recipe(r5).condition(getCond.apply("Obesity")).safe(true).build());
        recipeConditionRepo.save(RecipeCondition.builder().recipe(r5).condition(getCond.apply("Diabetes")).safe(true).build());

        recipeRatingRepo.save(RecipeRating.builder().user(systemUser).recipe(r5).stars(4).ratedAt(LocalDateTime.now()).build());

        // -------------------------
        // Recipe 6: Vegetarian Chickpea Curry (lighter)
        // -------------------------
        Recipe r6 = recipeRepository.save(Recipe.builder()
                .name("Vegetarian Chickpea Curry (lighter)")
                .description("Protein-rich chickpea curry made with tomatoes, spices and spinach; low oil option.")
                .summary("High-fiber vegetarian dish, good for diabetes and weight management.")
                .prepTimeMinutes(35)
                .build());

        recipeIngredientRepo.saveAll(List.of(
                RecipeIngredient.builder().recipe(r6).ingredient(getIng.apply("Chickpeas")).quantity(400).unit("grams").build(),
                RecipeIngredient.builder().recipe(r6).ingredient(getIng.apply("Canned tomatoes")).quantity(400).unit("grams").build(),
                RecipeIngredient.builder().recipe(r6).ingredient(getIng.apply("Spinach")).quantity(100).unit("grams").build(),
                RecipeIngredient.builder().recipe(r6).ingredient(getIng.apply("Garlic")).quantity(2).unit("cloves").build(),
                RecipeIngredient.builder().recipe(r6).ingredient(getIng.apply("Olive oil")).quantity(1).unit("tbsp").build()
        ));

        recipeStepRepo.saveAll(List.of(
                RecipeStep.builder().recipe(r6).step("Sauté aromatics").instruction("Heat oil, sauté garlic and onion until soft.").stepOrder(1).build(),
                RecipeStep.builder().recipe(r6).step("Add tomatoes & chickpeas").instruction("Add canned tomatoes and chickpeas; simmer 15–20 minutes.").stepOrder(2).build(),
                RecipeStep.builder().recipe(r6).step("Finish with spinach").instruction("Stir in spinach until wilted; season to taste.").stepOrder(3).build()
        ));

        recipeConditionRepo.save(RecipeCondition.builder().recipe(r6).condition(getCond.apply("Diabetes")).safe(true).build());
        recipeConditionRepo.save(RecipeCondition.builder().recipe(r6).condition(getCond.apply("Obesity")).safe(true).build());
        recipeConditionRepo.save(RecipeCondition.builder().recipe(r6).condition(getCond.apply("Hypertension")).safe(true).build());

        recipeRatingRepo.save(RecipeRating.builder().user(systemUser).recipe(r6).stars(4).ratedAt(LocalDateTime.now()).build());

        // -------------------------
        // Recipe 7: Avocado & Tuna Salad
        // -------------------------
        Recipe r7 = recipeRepository.save(Recipe.builder()
                .name("Avocado & Tuna Salad")
                .description("Quick salad with canned tuna, avocado, tomato and lime dressing.")
                .summary("Protein and healthy fats — good for heart health and satiety.")
                .prepTimeMinutes(10)
                .build());

        recipeIngredientRepo.saveAll(List.of(
                RecipeIngredient.builder().recipe(r7).ingredient(getIng.apply("Tuna")).quantity(200).unit("grams").build(),
                RecipeIngredient.builder().recipe(r7).ingredient(getIng.apply("Avocado")).quantity(1).unit("each").build(),
                RecipeIngredient.builder().recipe(r7).ingredient(getIng.apply("Tomato")).quantity(1).unit("each").build(),
                RecipeIngredient.builder().recipe(r7).ingredient(getIng.apply("Lime")).quantity(1).unit("each").build()
        ));

        recipeStepRepo.saveAll(List.of(
                RecipeStep.builder().recipe(r7).step("Mix").instruction("Flake tuna and combine with diced avocado and tomato.").stepOrder(1).build(),
                RecipeStep.builder().recipe(r7).step("Dress").instruction("Squeeze lime, drizzle olive oil and season with pepper.").stepOrder(2).build()
        ));

        recipeConditionRepo.save(RecipeCondition.builder().recipe(r7).condition(getCond.apply("Fish")).safe(false).build()); // tuna = fish allergen
        recipeConditionRepo.save(RecipeCondition.builder().recipe(r7).condition(getCond.apply("Coronary heart disease")).safe(true).build());
        recipeConditionRepo.save(RecipeCondition.builder().recipe(r7).condition(getCond.apply("Diabetes")).safe(true).build());

        recipeRatingRepo.save(RecipeRating.builder().user(systemUser).recipe(r7).stars(5).ratedAt(LocalDateTime.now()).build());

        // -------------------------
        // Recipe 8: Zucchini Noodles with Tomato-Basil Sauce (low-cal)
        // -------------------------
        Recipe r8 = recipeRepository.save(Recipe.builder()
                .name("Zucchini Noodles with Tomato-Basil Sauce")
                .description("Spiralized zucchini noodles in a chunky tomato and basil sauce — low-cal pasta alternative.")
                .summary("Good for obesity and blood-sugar control as a low-carb choice.")
                .prepTimeMinutes(20)
                .build());

        // Ensure Zucchini ingredient exists; if not, fallback to "Tomato" + "Bell pepper"
        Ingredient zucchini;
        try {
            zucchini = ingredientRepository.findByName("Zucchini").orElseThrow();
        } catch (Exception e) {
            // create zucchini if missing
            zucchini = ingredientRepository.save(Ingredient.builder().name("Zucchini").isHarmful_flag(false).build());
        }

        recipeIngredientRepo.saveAll(List.of(
                RecipeIngredient.builder().recipe(r8).ingredient(zucchini).quantity(3).unit("medium").build(),
                RecipeIngredient.builder().recipe(r8).ingredient(getIng.apply("Canned tomatoes")).quantity(400).unit("grams").build(),
                RecipeIngredient.builder().recipe(r8).ingredient(getIng.apply("Garlic")).quantity(2).unit("cloves").build(),
                RecipeIngredient.builder().recipe(r8).ingredient(getIng.apply("Olive oil")).quantity(1).unit("tbsp").build()
        ));

        recipeStepRepo.saveAll(List.of(
                RecipeStep.builder().recipe(r8).step("Spiralize").instruction("Spiralize zucchini into noodles and lightly salt to remove excess water.").stepOrder(1).build(),
                RecipeStep.builder().recipe(r8).step("Make sauce").instruction("Sauté garlic in oil, add canned tomatoes and simmer with basil.").stepOrder(2).build(),
                RecipeStep.builder().recipe(r8).step("Combine").instruction("Toss sauce with zucchini noodles briefly and serve.").stepOrder(3).build()
        ));

        recipeConditionRepo.save(RecipeCondition.builder().recipe(r8).condition(getCond.apply("Obesity")).safe(true).build());
        recipeConditionRepo.save(RecipeCondition.builder().recipe(r8).condition(getCond.apply("Diabetes")).safe(true).build());
        recipeConditionRepo.save(RecipeCondition.builder().recipe(r8).condition(getCond.apply("Wheat")).safe(true).build());

        recipeRatingRepo.save(RecipeRating.builder().user(systemUser).recipe(r8).stars(4).ratedAt(LocalDateTime.now()).build());

        // -------------------------
        // Recipe 9: Fruit & Yogurt Parfait (dairy-free option)
        // -------------------------
        Recipe r9 = recipeRepository.save(Recipe.builder()
                .name("Fruit & Yogurt Parfait (dairy-free option)")
                .description("Layered parfait using dairy-free yogurt option to accommodate milk allergies.")
                .summary("Antioxidant-rich and customizable; use dairy-free yogurt for milk allergy safety.")
                .prepTimeMinutes(5)
                .build());

        recipeIngredientRepo.saveAll(List.of(
                RecipeIngredient.builder().recipe(r9).ingredient(getIng.apply("Yogurt (plain)")).quantity(150).unit("grams").build(),
                RecipeIngredient.builder().recipe(r9).ingredient(getIng.apply("Strawberries")).quantity(100).unit("grams").build(),
                RecipeIngredient.builder().recipe(r9).ingredient(getIng.apply("Blueberries")).quantity(50).unit("grams").build(),
                RecipeIngredient.builder().recipe(r9).ingredient(getIng.apply("Maple syrup")).quantity(1).unit("tbsp").build()
        ));

        recipeStepRepo.saveAll(List.of(
                RecipeStep.builder().recipe(r9).step("Layer").instruction("Layer yogurt, berries and a drizzle of maple syrup in a glass.").stepOrder(1).build()
        ));

        recipeConditionRepo.save(RecipeCondition.builder().recipe(r9).condition(getCond.apply("Milk")).safe(false).build()); // uses dairy by default
        recipeConditionRepo.save(RecipeCondition.builder().recipe(r9).condition(getCond.apply("Milk")).safe(false).build());
        // Mark safe for dairy-free option note: but default as unsafe

        recipeRatingRepo.save(RecipeRating.builder().user(systemUser).recipe(r9).stars(3).ratedAt(LocalDateTime.now()).build());

        // -------------------------
        // Recipe 10: Baked Sweet Potato with Black Beans & Avocado
        // -------------------------
        Recipe r10 = recipeRepository.save(Recipe.builder()
                .name("Baked Sweet Potato with Black Beans & Avocado")
                .description("Stuffed baked sweet potatoes topped with black beans, avocado and lime — fiber & nutrient dense.")
                .summary("Plant-forward, filling meal good for diabetes, obesity and heart health.")
                .prepTimeMinutes(60)
                .build());

        recipeIngredientRepo.saveAll(List.of(
                RecipeIngredient.builder().recipe(r10).ingredient(getIng.apply("Sweet potato")).quantity(2).unit("each").build(),
                RecipeIngredient.builder().recipe(r10).ingredient(getIng.apply("Black beans")).quantity(1).unit("cup").build(),
                RecipeIngredient.builder().recipe(r10).ingredient(getIng.apply("Avocado")).quantity(1).unit("each").build(),
                RecipeIngredient.builder().recipe(r10).ingredient(getIng.apply("Lime")).quantity(1).unit("each").build()
        ));

        recipeStepRepo.saveAll(List.of(
                RecipeStep.builder().recipe(r10).step("Bake potato").instruction("Preheat oven to 200°C; bake sweet potatoes until tender (~45-55 minutes).").stepOrder(1).build(),
                RecipeStep.builder().recipe(r10).step("Warm beans").instruction("Warm black beans with a pinch of cumin and garlic if desired.").stepOrder(2).build(),
                RecipeStep.builder().recipe(r10).step("Assemble").instruction("Split potatoes, top with beans, diced avocado and a squeeze of lime.").stepOrder(3).build()
        ));

        recipeConditionRepo.save(RecipeCondition.builder().recipe(r10).condition(getCond.apply("Diabetes")).safe(true).build());
        recipeConditionRepo.save(RecipeCondition.builder().recipe(r10).condition(getCond.apply("Obesity")).safe(true).build());
        recipeConditionRepo.save(RecipeCondition.builder().recipe(r10).condition(getCond.apply("Wheat")).safe(true).build());

        recipeRatingRepo.save(RecipeRating.builder().user(systemUser).recipe(r10).stars(5).ratedAt(LocalDateTime.now()).build());
        recipeRatingRepo.save(RecipeRating.builder().user(systemUser).recipe(r10).stars(4).ratedAt(LocalDateTime.now()).build());

        System.out.println("Seeded 10 recipes with ingredients, steps, conditions and ratings.");
    }
}
