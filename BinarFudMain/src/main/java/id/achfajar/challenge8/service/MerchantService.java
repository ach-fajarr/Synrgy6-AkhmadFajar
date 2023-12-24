package id.achfajar.challenge8.service;

import id.achfajar.challenge8.dto.MerchantDTO;

import java.util.List;
import java.util.UUID;

public interface MerchantService {
    MerchantDTO createMerchant(MerchantDTO merchants, String username);

    List<MerchantDTO> getMerchantByUser(String name);

    MerchantDTO getMerchantById(UUID id);

    MerchantDTO updateMerchant(UUID id, MerchantDTO merchant);

    MerchantDTO deleteMerchant(UUID id);

    MerchantDTO changeMerchantStatus(UUID id, boolean open);

    List<MerchantDTO> getAllMerchants();

    List<MerchantDTO> filterMerchants(String filterCriteria);

    List<MerchantDTO> getOpenMerchants();


}
