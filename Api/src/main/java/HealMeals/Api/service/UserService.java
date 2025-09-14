package HealMeals.Api.service;

import HealMeals.Api.DTO.UserDTO;
import HealMeals.Api.Repo.UserRepository;
import HealMeals.Api.model.User;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserDTO createUser(UserDTO dto) {
        User user = User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .password("hashed") 
                .role(dto.getRole())
                .gender(dto.getGender())
                .dob(dto.getDob())
                .address(dto.getAddress())
                .phone(dto.getPhone())
                .build();
        userRepository.save(user);
        return mapToDTO(user);
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream().map(this::mapToDTO).toList();
    }

    public UserDTO getUserById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return mapToDTO(user);
    }

    public UserDTO updateUser(UUID id, UserDTO dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setRole(dto.getRole());
        user.setGender(dto.getGender());
        user.setDob(dto.getDob());
        user.setAddress(dto.getAddress());
        user.setPhone(dto.getPhone());

        userRepository.save(user);
        return mapToDTO(user);
    }

    public void deleteUser(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(id);
    }

    private UserDTO mapToDTO(User user) {
        return UserDTO.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .gender(user.getGender())
                .dob(user.getDob())
                .address(user.getAddress())
                .phone(user.getPhone())
                .build();
    }

    @PostConstruct
    public void initDummyUsers() {
        if (userRepository.count() == 0) {
            User u1 = User.builder()
                    .name("Alice Johnson")
                    .email("alice@example.com")
                    .password("hashedpassword1")
                    .role("USER")
                    .gender("Female")
                    .address("Cairo, Egypt")
                    .phone("01012345678")
                    .build();

            User u2 = User.builder()
                    .name("Bob Smith")
                    .email("bob@example.com")
                    .password("hashedpassword2")
                    .role("ADMIN")
                    .gender("Male")
                    .address("Alexandria, Egypt")
                    .phone("01198765432")
                    .build();

            userRepository.saveAll(List.of(u1, u2));
        }
    }
}