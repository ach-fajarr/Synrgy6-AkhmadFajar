package id.achfajar.challenge5.controller;

import id.achfajar.challenge5.model.dto.MerchantDTO;
import id.achfajar.challenge5.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/merchants")
public class MerchantController {

    private final MerchantService merchantService;

    @Autowired
    public MerchantController(MerchantService merchantService) {
        this.merchantService = merchantService;
    }

    @GetMapping
    public ResponseEntity<List<MerchantDTO>> getAllMerchants() {
        List<MerchantDTO> merchants = merchantService.getAllMerchants();
        return new ResponseEntity<>(merchants, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MerchantDTO> getMerchantById(@PathVariable UUID id) {
        MerchantDTO merchant = merchantService.getMerchantById(id);
        return new ResponseEntity<>(merchant, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<MerchantDTO> createMerchant(@RequestBody MerchantDTO merchantDTO) {
        MerchantDTO createdMerchant = merchantService.createMerchant(merchantDTO);
        return new ResponseEntity<>(createdMerchant, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MerchantDTO> updateMerchant(@PathVariable UUID id, @RequestBody MerchantDTO merchantDTO) {
        MerchantDTO updatedMerchant = merchantService.updateMerchant(id, merchantDTO);
        return new ResponseEntity<>(updatedMerchant, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMerchant(@PathVariable UUID id) {
        merchantService.deleteMerchant(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{id}/status")
    public MerchantDTO changeMerchantStatus(@PathVariable UUID id, @RequestParam boolean open) {
        return merchantService.changeMerchantStatus(id, open);
    }

    @GetMapping("/open")
    public List<MerchantDTO> getOpenMerchants() {
        return merchantService.getOpenMerchants();
    }

    @GetMapping("/filter")
    public ResponseEntity<List<MerchantDTO>> filterMerchants(@RequestParam String filterCriteria) {
        List<MerchantDTO> filteredMerchants = merchantService.filterMerchants(filterCriteria);
        return new ResponseEntity<>(filteredMerchants, HttpStatus.OK);
    }
    @GetMapping("/{merchantId}/total-ordered-products")
    public int getTotalOrderedProducts(@PathVariable UUID merchantId) {
        return merchantService.getTotalOrderedProducts(merchantId);
    }

    @GetMapping("/{merchantId}/total-revenue")
    public double getTotalRevenue(@PathVariable UUID merchantId) {
        return merchantService.getTotalRevenue(merchantId);
    }

    @GetMapping("/{merchantId}/total-revenue-by-date")
    public double getTotalRevenueByDateRange(@PathVariable UUID merchantId,
                                             @RequestParam LocalDate startDate,
                                             @RequestParam LocalDate endDate) {
        return merchantService.getTotalRevenueByDateRange(merchantId, startDate, endDate);
    }

}
