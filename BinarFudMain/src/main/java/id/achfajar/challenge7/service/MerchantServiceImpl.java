package id.achfajar.challenge7.service;

import id.achfajar.challenge7.dto.MerchantDTO;
import id.achfajar.challenge7.exception.ExistingResourceFoundException;
import id.achfajar.challenge7.exception.ResourceEmptyException;
import id.achfajar.challenge7.exception.ResourceNotFoundException;
import id.achfajar.challenge7.model.Merchant;
import id.achfajar.challenge7.model.Users;
import id.achfajar.challenge7.repository.MerchantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MerchantServiceImpl implements MerchantService {

    private final MerchantRepository merchantRepository;
    private final UserService userService;

    @Autowired
    public MerchantServiceImpl(MerchantRepository merchantRepository, UserService userService) {
        this.merchantRepository = merchantRepository;
        this.userService = userService;
    }

    @Override
    public MerchantDTO createMerchant(MerchantDTO merchants, String username) {
        Optional<Merchant> merchant = merchantRepository
                .findByMerchantName(merchants.getMerchantName());
        if(merchant.isEmpty()){
            Merchant newMerchant = mapToEntity(merchants);
            newMerchant.setUsers(getUserByUsername(username));
            merchantRepository.save(newMerchant);
            return mapToDTO(newMerchant);
        } else {
            throw new ExistingResourceFoundException(merchants.getMerchantName(), mapToDTO(merchant.get()));
        }
    }

    @Override
    public List<MerchantDTO> getMerchantByUser(String name) {
        List<MerchantDTO> merchantDTOS = new ArrayList<>();
        List<Merchant> merchants = merchantRepository.findAllByUsers(getUserByUsername(name));
        if(merchants.isEmpty())
            throw new ResourceEmptyException("Data merchant kosong");
        for(Merchant merchant : merchants){
            merchantDTOS.add(mapToDTO(merchant));
        }
        return merchantDTOS;
    }

    @Override
    public MerchantDTO getMerchantById(UUID id) {
        Merchant merchant = merchantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
        return mapToDTO(merchant);
    }

    @Override
    public MerchantDTO updateMerchant(UUID id, MerchantDTO merchant) {
        Merchant existingMerchant = merchantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
        if(merchant.getMerchantName()!=null){
            existingMerchant.setMerchant_name(merchant.getMerchantName());
        } else if (merchant.getMerchantLocation()!=null) {
            existingMerchant.setMerchant_location(merchant.getMerchantLocation());
        } else if (merchant.getOpen()!=null) {
            existingMerchant.setOpen(merchant.getOpen());
        }
        merchantRepository.save(existingMerchant);
        return mapToDTO(existingMerchant);
    }

    @Override
    public MerchantDTO deleteMerchant(UUID id) {
        Merchant merchant = merchantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
        try{
            merchantRepository.delete(merchant);
            return mapToDTO(merchant);
        } catch (Exception e){
            throw new RuntimeException("Invalid");
        }
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
    public MerchantDTO changeMerchantStatus(UUID id, boolean open) {
        Merchant existingMerchant = merchantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
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
    public List<MerchantDTO> getAllMerchants() {
        List<MerchantDTO> merchantDTOs = new ArrayList<>();
        List<Merchant> merchants = merchantRepository.findAll();
        for (Merchant merchant : merchants) {
            merchantDTOs.add(mapToDTO(merchant));
        }
        return merchantDTOs;
    }

    private Users getUserByUsername(String username){
        return userService.getUsersByUsername(username);
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
        merchant.setOpen(merchantDTO.getOpen());
        return merchant;
    }

}
