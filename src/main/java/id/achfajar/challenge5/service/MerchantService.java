package id.achfajar.challenge5.service;

import id.achfajar.challenge5.model.dto.MerchantDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface MerchantService {
    MerchantDTO createMerchant(MerchantDTO merchantDTO);

    MerchantDTO updateMerchant(UUID id, MerchantDTO merchantDTO);

    MerchantDTO getMerchantById(UUID id);

    void deleteMerchant(UUID id);

    MerchantDTO changeMerchantStatus(UUID id, boolean open);

    List<MerchantDTO> getAllMerchants();

    List<MerchantDTO> filterMerchants(String filterCriteria);

    List<MerchantDTO> getOpenMerchants();
    int getTotalOrderedProducts(UUID merchantId);

    double getTotalRevenue(UUID merchantId);

    double getTotalRevenueByDateRange(UUID merchantId, LocalDate startDate, LocalDate endDate);
}
