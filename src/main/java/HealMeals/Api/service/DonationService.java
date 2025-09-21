package HealMeals.Api.service;

import HealMeals.Api.DTO.DonationResponseDTO;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import HealMeals.Api.DTO.DonationRequestDTO;
import HealMeals.Api.Repo.DonationRepository;
import HealMeals.Api.Repo.UserRepository;
import HealMeals.Api.model.Donation;
import HealMeals.Api.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DonationService {

    private final DonationRepository donationRepository;
    private final UserRepository userRepository;

    public DonationResponseDTO create(DonationRequestDTO dto) {
        /*User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Donation donation = Donation.builder()
                .user(user)
                .amount(dto.getAmount())
                .date(LocalDateTime.now())
                .paymentMethod(dto.getPaymentMethod())
                .transactionId(dto.getTransactionId())
                .build();

        return toDTO(donationRepository.save(donation));*/


        User user = userRepository.findByEmail(dto.getEmail()).orElse(null);
        Donation donation = Donation.builder()
                .firstName(dto.getFirstName())
                .secondName(dto.getSecondName())
                .email(dto.getEmail())
                .amount(dto.getAmount())
                .message(dto.getMessage())
                .cardNumber(dto.getCardNumber())
                .phoneNumber(dto.getPhoneNumber())
                .expiryDate(dto.getExpiryDate())
                .cvv(dto.getCvv())
                .date(LocalDateTime.now())
                .paymentMethod(dto.getPaymentMethod())
                .transactionId(UUID.randomUUID().toString())
                .user(user)
                .build();

        Donation saved = donationRepository.save(donation);

        return toDTO(saved);
    }

    public List<DonationResponseDTO> getAll() {
        return donationRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public DonationResponseDTO getById(UUID id) {
        return donationRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new RuntimeException("Donation not found"));
    }

    public void delete(UUID id) {
        donationRepository.deleteById(id);
    }

    private DonationResponseDTO toDTO(Donation donation) {
        DonationResponseDTO dto = new DonationResponseDTO();
        dto.setDonationId(donation.getDonationId());
        dto.setFirstName(donation.getFirstName());
        dto.setSecondName(donation.getSecondName());
        dto.setAmount(donation.getAmount());
        dto.setEmail(donation.getEmail());
        dto.setMessage(donation.getMessage());
        dto.setPhoneNumber(donation.getPhoneNumber());
        dto.setPaymentMethod(donation.getPaymentMethod());
        return dto;
    }
}
