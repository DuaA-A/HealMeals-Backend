package HealMeals.Api.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import HealMeals.Api.DTO.DonationDTO;
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

    public DonationDTO create(DonationDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Donation donation = Donation.builder()
                .user(user)
                .amount(dto.getAmount())
                .date(LocalDateTime.now())
                .paymentMethod(dto.getPaymentMethod())
                .transactionId(dto.getTransactionId())
                .build();

        return toDTO(donationRepository.save(donation));
    }

    public List<DonationDTO> getAll() {
        return donationRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public DonationDTO getById(UUID id) {
        return donationRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new RuntimeException("Donation not found"));
    }

    public void delete(UUID id) {
        donationRepository.deleteById(id);
    }

    private DonationDTO toDTO(Donation donation) {
        DonationDTO dto = new DonationDTO();
        dto.setDonationId(donation.getDonationId());
        dto.setUserId(donation.getUser().getUserId());
        dto.setAmount(donation.getAmount());
        dto.setDate(donation.getDate());
        dto.setPaymentMethod(donation.getPaymentMethod());
        dto.setTransactionId(donation.getTransactionId());
        return dto;
    }
}
