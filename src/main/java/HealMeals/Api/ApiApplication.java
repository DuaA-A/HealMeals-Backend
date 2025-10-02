package HealMeals.Api;

import HealMeals.Api.Repo.*;
import HealMeals.Api.model.*;
import HealMeals.Api.Repo.RecipeStepRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

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
            UserConditionRepository userConditionRepository,
            IngredientRepository ingredientRepository,
            RecipeRepository recipeRepository,
            RecipeIngredientRepository recipeIngredientRepository,
            RecipeStepRepository recipeStepRepository,
            PasswordEncoder passwordEncoder
    ) {
        return args -> {
            // --- 1. Seed Allergies & Diseases ---
            List<String> allergies = List.of("Milk","Eggs","Peanuts","Tree nuts","Fish",
                    "Crustacean shellfish","Wheat","Soy","Sesame","Mustard","Sulphites");

            List<String> diseases = List.of("Diabetes","Hypertension","Obesity","Coronary heart disease",
                    "Stroke","Chronic respiratory disease (COPD/asthma)","Cancer","Arthritis","Chronic kidney disease");

            for (String name : allergies) {
                if (!profileConditionRepository.existsByConditionName(name)) {
                    profileConditionRepository.save(ProfileCondition.builder()
                            .conditionName(name)
                            .conditionType(ProfileCondition.ConditionType.ALLERGY)
                            .build());
                }
            }
            for (String name : diseases) {
                if (!profileConditionRepository.existsByConditionName(name)) {
                    profileConditionRepository.save(ProfileCondition.builder()
                            .conditionName(name)
                            .conditionType(ProfileCondition.ConditionType.DISEASE)
                            .build());
                }
            }

            // --- 2. Seed Users ---
            if (userRepository.count() == 0) {
                User alice = User.builder()
                        .name("Alice Johnson")
                        .email("alice@example.com")
                        .password(passwordEncoder.encode("password123"))
                        .role("USER")
                        .gender("Female")
                        .dob(LocalDate.of(1990, 5, 12))
                        .address("Cairo, Egypt")
                        .phone("01012345678")
                        .build();

                User bob = User.builder()
                        .name("Bob Smith")
                        .email("bob@example.com")
                        .password(passwordEncoder.encode("password456"))
                        .role("ADMIN")
                        .gender("Male")
                        .dob(LocalDate.of(1985, 2, 20))
                        .address("Alexandria, Egypt")
                        .phone("01198765432")
                        .build();

                userRepository.saveAll(List.of(alice, bob));
            }

            // --- 3. Seed Ingredients (example subset, extend as needed) ---
            Ingredient salmon = ingredientRepository.save(Ingredient.builder().ingredient_id(UUID.randomUUID()).name("Salmon").isHarmful_flag(false).build());
            Ingredient quinoa = ingredientRepository.save(Ingredient.builder().ingredient_id(UUID.randomUUID()).name("Quinoa").isHarmful_flag(false).build());
            Ingredient spinach = ingredientRepository.save(Ingredient.builder().ingredient_id(UUID.randomUUID()).name("Spinach").isHarmful_flag(false).build());
            Ingredient lemon = ingredientRepository.save(Ingredient.builder().ingredient_id(UUID.randomUUID()).name("Lemon").isHarmful_flag(false).build());
            Ingredient oliveOil = ingredientRepository.save(Ingredient.builder().ingredient_id(UUID.randomUUID()).name("Olive Oil").isHarmful_flag(false).build());

            // --- 4. Seed Recipes ---
            if (recipeRepository.count() == 0) {
                // 1. Grilled Salmon with Quinoa Salad
                Recipe salmonQuinoa = recipeRepository.save(Recipe.builder()
                        .id(UUID.randomUUID())
                        .name("Grilled Salmon with Quinoa Salad")
                        .description("A heart-healthy recipe rich in omega-3 and fiber.")
                        .summary("Grilled salmon served with quinoa and spinach salad.")
                        .prepTimeMinutes(30)
                        .build());
                recipeIngredientRepository.saveAll(List.of(
                        RecipeIngredient.builder().recipe(salmonQuinoa).ingredient(salmon).quantity(200).unit("g").build(),
                        RecipeIngredient.builder().recipe(salmonQuinoa).ingredient(quinoa).quantity(100).unit("g").build(),
                        RecipeIngredient.builder().recipe(salmonQuinoa).ingredient(spinach).quantity(50).unit("g").build(),
                        RecipeIngredient.builder().recipe(salmonQuinoa).ingredient(lemon).quantity(1).unit("pc").build(),
                        RecipeIngredient.builder().recipe(salmonQuinoa).ingredient(oliveOil).quantity(2).unit("tbsp").build()
                ));
                recipeStepRepository.saveAll(List.of(
                        RecipeStep.builder().recipe(salmonQuinoa).step("Rinse quinoa and cook in boiling water for 15 minutes.").stepOrder(1).build(),
                        RecipeStep.builder().recipe(salmonQuinoa).step("Season salmon with olive oil, salt, and pepper.").stepOrder(2).build(),
                        RecipeStep.builder().recipe(salmonQuinoa).step("Grill salmon for 4–5 minutes per side.").stepOrder(3).build(),
                        RecipeStep.builder().recipe(salmonQuinoa).step("Mix spinach, cooked quinoa, and lemon juice to make the salad.").stepOrder(4).build(),
                        RecipeStep.builder().recipe(salmonQuinoa).step("Serve salmon over quinoa salad.").stepOrder(5).build()
                ));

                // 2. Chickpea & Spinach Stew
                Recipe chickpeaStew = recipeRepository.save(Recipe.builder()
                        .id(UUID.randomUUID())
                        .name("Chickpea & Spinach Stew")
                        .description("A high-fiber vegan stew ideal for diabetes and weight management.")
                        .summary("Protein-rich chickpeas slow-cooked with spinach, garlic, and spices.")
                        .prepTimeMinutes(40)
                        .build());
                recipeIngredientRepository.saveAll(List.of(
                        RecipeIngredient.builder().recipe(chickpeaStew).ingredient(ingredientRepository.findByName("Chickpeas").get()).quantity(200).unit("g").build(),
                        RecipeIngredient.builder().recipe(chickpeaStew).ingredient(spinach).quantity(100).unit("g").build(),
                        RecipeIngredient.builder().recipe(chickpeaStew).ingredient(oliveOil).quantity(2).unit("tbsp").build(),
                        RecipeIngredient.builder().recipe(chickpeaStew).ingredient(ingredientRepository.findByName("Garlic").get()).quantity(3).unit("cloves").build()
                ));
                recipeStepRepository.saveAll(List.of(
                        RecipeStep.builder().recipe(chickpeaStew).step("Soak chickpeas overnight or use canned chickpeas.").stepOrder(1).build(),
                        RecipeStep.builder().recipe(chickpeaStew).step("Sauté garlic in olive oil.").stepOrder(2).build(),
                        RecipeStep.builder().recipe(chickpeaStew).step("Add chickpeas and simmer with water for 20 minutes.").stepOrder(3).build(),
                        RecipeStep.builder().recipe(chickpeaStew).step("Add spinach and cook until wilted.").stepOrder(4).build(),
                        RecipeStep.builder().recipe(chickpeaStew).step("Season with salt, pepper, and cumin.").stepOrder(5).build()
                ));

                // 3. Oatmeal with Berries & Flaxseeds
                Recipe oatmeal = recipeRepository.save(Recipe.builder()
                        .id(UUID.randomUUID())
                        .name("Oatmeal with Berries & Flaxseeds")
                        .description("Diabetes-friendly breakfast high in fiber and antioxidants.")
                        .summary("Creamy oats topped with fresh berries and flaxseeds.")
                        .prepTimeMinutes(15)
                        .build());
                recipeIngredientRepository.saveAll(List.of(
                        RecipeIngredient.builder().recipe(oatmeal).ingredient(ingredientRepository.findByName("Oats").get()).quantity(80).unit("g").build(),
                        RecipeIngredient.builder().recipe(oatmeal).ingredient(ingredientRepository.findByName("Blueberries").get()).quantity(50).unit("g").build(),
                        RecipeIngredient.builder().recipe(oatmeal).ingredient(ingredientRepository.findByName("Flaxseeds").get()).quantity(2).unit("tbsp").build(),
                        RecipeIngredient.builder().recipe(oatmeal).ingredient(ingredientRepository.findByName("Almond milk").get()).quantity(200).unit("ml").build()
                ));
                recipeStepRepository.saveAll(List.of(
                        RecipeStep.builder().recipe(oatmeal).step("Cook oats in almond milk for 5 minutes.").stepOrder(1).build(),
                        RecipeStep.builder().recipe(oatmeal).step("Add blueberries and stir.").stepOrder(2).build(),
                        RecipeStep.builder().recipe(oatmeal).step("Top with flaxseeds before serving.").stepOrder(3).build()
                ));
            }


            System.out.println("^_^ Dummy data seeding complete: Users + Conditions + Recipes ready.");
        };
    }
}