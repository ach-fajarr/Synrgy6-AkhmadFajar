package id.achfajar.challenge5.service;

import id.achfajar.challenge5.model.dto.MerchantDTO;
import id.achfajar.challenge5.exception.ResourceNotFoundException;
import id.achfajar.challenge5.model.Merchant;
import id.achfajar.challenge5.repository.MerchantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class MerchantServiceImpl implements MerchantService {

    private final MerchantRepository merchantRepository;

    @Autowired
    public MerchantServiceImpl(MerchantRepository merchantRepository) {
        this.merchantRepository = merchantRepository;
    }

    @Override
    public List<MerchantDTO> getAllMerchants() {
        List<Merchant> merchants = merchantRepository.findAll();
        List<MerchantDTO> merchantDTOs = new ArrayList<>();

        for (Merchant merchant : merchants) {
            merchantDTOs.add(mapToDTO(merchant));
        }

        return merchantDTOs;
    }

    @Override
    public List<MerchantDTO> filterMerchants(String filterCriteria) {
        List<Merchant> merchants = merchantRepository.findAllByMerchantNameOrLocationContaining(filterCriteria);
        List<MerchantDTO> merchantDTOs = new ArrayList<>();

        for (Merchant merchant : merchants) {
            merchantDTOs.add(mapToDTO(merchant));
        }

        return merchantDTOs;
    }

    @Override
    public MerchantDTO getMerchantById(UUID id) {
        Merchant merchant = merchantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Merchant dengan id : " + id + "tidak ditemukan"));
        return mapToDTO(merchant);
    }

    @Override
    public MerchantDTO createMerchant(MerchantDTO merchantDTO) {
        Merchant merchant = mapToEntity(merchantDTO);
        merchant = merchantRepository.save(merchant);
        return mapToDTO(merchant);
    }

    @Override
    public MerchantDTO updateMerchant(UUID id, MerchantDTO merchantDTO) {
        Merchant existingMerchant = merchantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Merchant dengan id : " + id + "tidak ditemukan"));

        Merchant updatedMerchant = mapToEntity(merchantDTO);
        updatedMerchant.setId(existingMerchant.getId());
        updatedMerchant = merchantRepository.save(updatedMerchant);
        return mapToDTO(updatedMerchant);
    }

    @Override
    public void deleteMerchant(UUID id) {
        Merchant merchant = merchantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Merchant dengan id : " + id + "tidak ditemukan"));
        merchantRepository.delete(merchant);
    }
    @Override
    public MerchantDTO changeMerchantStatus(UUID id, boolean open) {
        Merchant existingMerchant = merchantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Merchant dengan id : " + id + "tidak ditemukan"));

        existingMerchant.setOpen(open);
        existingMerchant = merchantRepository.save(existingMerchant);
        return mapToDTO(existingMerchant);
    }

    @Override
    public List<MerchantDTO> getOpenMerchants() {
        List<Merchant> openMerchants = merchantRepository.findAllByOpen(true);
        List<MerchantDTO> merchantDTOs = new ArrayList<>();

        for (Merchant merchant : openMerchants) {
            merchantDTOs.add(mapToDTO(merchant));
        }

        return merchantDTOs;
    }

    @Override
    public int getTotalOrderedProducts(UUID merchantId) {
        Merchant merchant = merchantRepository.findById(merchantId)
                .orElseThrow(() -> new ResourceNotFoundException("Merchant dengan id : " + merchantId + "tidak ditemukan"));

        return merchant.getTotalOrderedProducts();
    }

    @Override
    public double getTotalRevenue(UUID merchantId) {
        Merchant merchant = merchantRepository.findById(merchantId)
                .orElseThrow(() -> new ResourceNotFoundException("Merchant dengan id : " + merchantId + "tidak ditemukan"));

        return merchant.getTotalRevenue();
    }

    @Override
    public double getTotalRevenueByDateRange(UUID merchantId, LocalDate startDate, LocalDate endDate) {
        Merchant merchant = merchantRepository.findById(merchantId)
                .orElseThrow(() -> new ResourceNotFoundException("Merchant dengan id : " + merchantId + "tidak ditemukan"));

        return merchant.getTotalRevenueByDateRange(startDate, endDate);
    }

    private MerchantDTO mapToDTO(Merchant merchant) {
        MerchantDTO merchantDTO = new MerchantDTO();
        merchantDTO.setId(merchant.getId());
        merchantDTO.setMerchantName(merchant.getMerchant_name());
        merchantDTO.setMerchantLocation(merchant.getMerchant_location());
        merchantDTO.setOpen(merchant.isOpen());
        return merchantDTO;
    }

    private Merchant mapToEntity(MerchantDTO merchantDTO) {
        Merchant merchant = new Merchant();
        merchant.setId(merchantDTO.getId());
        merchant.setMerchant_name(merchantDTO.getMerchantName());
        merchant.setMerchant_location(merchantDTO.getMerchantLocation());
        merchant.setOpen(merchantDTO.isOpen());
        return merchant;
    }
}
