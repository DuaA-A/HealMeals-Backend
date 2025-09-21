package HealMeals.Api;

import HealMeals.Api.Repo.ProfileConditionRepository;
import HealMeals.Api.Repo.UserConditionRepository;
import HealMeals.Api.Repo.UserRepository;
import HealMeals.Api.model.ProfileCondition;
import HealMeals.Api.model.User;
import HealMeals.Api.model.UserCondition;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;

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
            PasswordEncoder passwordEncoder
    ) {
        return args -> {
            List<String> allergies = List.of(
                    "Milk","Eggs","Peanuts","Tree nuts","Fish",
                    "Crustacean shellfish","Wheat","Soy","Sesame",
                    "Mustard","Sulphites"
            );
            List<String> diseases = List.of(
                    "Diabetes","Hypertension","Obesity","Coronary heart disease",
                    "Stroke","Chronic respiratory disease (COPD/asthma)",
                    "Cancer","Arthritis","Chronic kidney disease"
            );

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

                User carol = User.builder()
                        .name("Carol Lee")
                        .email("carol@example.com")
                        .password(passwordEncoder.encode("password789"))
                        .role("USER")
                        .gender("Female")
                        .dob(LocalDate.of(1995, 8, 30))
                        .address("Giza, Egypt")
                        .phone("01234567890")
                        .build();

                User david = User.builder()
                        .name("David Brown")
                        .email("david@example.com")
                        .password(passwordEncoder.encode("password321"))
                        .role("USER")
                        .gender("Male")
                        .dob(LocalDate.of(1992, 11, 10))
                        .address("Luxor, Egypt")
                        .phone("01555555555")
                        .build();

                userRepository.saveAll(List.of(alice, bob, carol, david));

                ProfileCondition diabetes = profileConditionRepository.findByConditionName("Diabetes").orElse(null);
                ProfileCondition peanuts = profileConditionRepository.findByConditionName("Peanuts").orElse(null);
                ProfileCondition asthma = profileConditionRepository.findByConditionName("Chronic respiratory disease (COPD/asthma)").orElse(null);
                ProfileCondition milk = profileConditionRepository.findByConditionName("Milk").orElse(null);

                if (diabetes != null) {
                    userConditionRepository.save(UserCondition.builder()
                            .user(alice)
                            .condition(diabetes)
                            .build());
                }
                if (peanuts != null) {
                    userConditionRepository.save(UserCondition.builder()
                            .user(bob)
                            .condition(peanuts)
                            .build());
                }
                if (asthma != null) {
                    userConditionRepository.save(UserCondition.builder()
                            .user(carol)
                            .condition(asthma)
                            .build());
                }
                if (milk != null) {
                    userConditionRepository.save(UserCondition.builder()
                            .user(david)
                            .condition(milk)
                            .build());
                }
            }

            System.out.println("^_^ Dummy data seeding complete: Users + Conditions ready.");
        };
    }
}
