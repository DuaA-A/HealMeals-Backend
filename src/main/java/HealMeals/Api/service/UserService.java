package HealMeals.Api.service;

import HealMeals.Api.DTO.UserDTO;
import HealMeals.Api.Repo.UserRepository;
import HealMeals.Api.model.User;
import HealMeals.Api.Mapper.UserMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserDTO createUser(UserDTO dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }
        User user = User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .password("") // use AuthService.register to set password
                .role(dto.getRole() == null ? "USER" : dto.getRole())
                .gender(dto.getGender())
                .dob(dto.getDob())
                .address(dto.getAddress())
                .phone(dto.getPhone())
                .build();
        user = userRepository.save(user);
        return UserMapper.toDTO(user);
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream().map(UserMapper::toDTO).toList();
    }

    public UserDTO getUserById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return UserMapper.toDTO(user);
    }

    @Transactional
    public UserDTO updateUser(UUID id, UserDTO dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (!user.getEmail().equals(dto.getEmail()) && userRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setRole(dto.getRole());
        user.setGender(dto.getGender());
        user.setDob(dto.getDob());
        user.setAddress(dto.getAddress());
        user.setPhone(dto.getPhone());
        user = userRepository.save(user);
        return HealMeals.Api.Mapper.UserMapper.toDTO(user);
    }

    public void deleteUser(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(id);
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
                    .dob(LocalDate.of(1990,1,1))
                    .build();

            User u2 = User.builder()
                    .name("Bob Smith")
                    .email("bob@example.com")
                    .password("hashedpassword2")
                    .role("ADMIN")
                    .gender("Male")
                    .address("Alexandria, Egypt")
                    .phone("01198765432")
                    .dob(LocalDate.of(1985,5,5))
                    .build();

            userRepository.saveAll(List.of(u1, u2));
        }
    }
}
