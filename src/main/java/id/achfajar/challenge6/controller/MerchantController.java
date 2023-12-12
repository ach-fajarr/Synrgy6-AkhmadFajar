package id.achfajar.challenge6.controller;

import id.achfajar.challenge6.dto.MerchantDTO;
import id.achfajar.challenge6.dto.request.RevenueRequestDTO;
import id.achfajar.challenge6.dto.response.ResponseHandler;
import id.achfajar.challenge6.service.InvoiceService;
import id.achfajar.challenge6.service.MerchantService;
import id.achfajar.challenge6.service.ProductService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/merchant")
public class MerchantController {

    private final MerchantService merchantService;
    private final ProductService productService;
    private final InvoiceService invoiceService;

    @Autowired
    public MerchantController(
            MerchantService merchantService,
            ProductService productService,
            InvoiceService invoiceService) {
        this.merchantService = merchantService;
        this.productService = productService;
        this.invoiceService = invoiceService;
    }

    @PostMapping("/add")
    public ResponseEntity<Object> addMerchant(@RequestBody MerchantDTO merchants, Principal principal) {
        MerchantDTO merchant = merchantService.createMerchant(merchants, principal.getName());
        return ResponseHandler.generateResponseSuccess(merchant);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getMerchantByUsers(Principal principal) {
        List<MerchantDTO> merchant = merchantService.getMerchantByUser(principal.getName());
        return ResponseHandler.generateResponseSuccess(merchant);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getMerchantById(@PathVariable UUID id) {
        MerchantDTO merchant = merchantService.getMerchantById(id);
        return ResponseHandler.generateResponseSuccess(merchant);
    }

    @PatchMapping("/update")
    public ResponseEntity<Object> updateMerchant(@RequestParam UUID id, @RequestBody MerchantDTO merchant) {
        MerchantDTO updatedMerchant = merchantService.updateMerchant(id, merchant);
        return ResponseHandler.generateResponseSuccess(updatedMerchant);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Object> deleteMerchant(@RequestParam UUID id) {
        MerchantDTO deletedMerchant = merchantService.deleteMerchant(id);
        return ResponseHandler.generateResponse("data berhasil dihapus", HttpStatus.ACCEPTED, deletedMerchant);
    }

    @PatchMapping("/set")
    public ResponseEntity<Object> changeMerchantStatusOpen(@RequestParam UUID id, @RequestParam boolean open) {
        MerchantDTO updatedMerchant = merchantService.changeMerchantStatus(id, open);
        return ResponseHandler.generateResponseSuccess(updatedMerchant);
    }

    @GetMapping("/filter")
    public ResponseEntity<Object> filterMerchants(@RequestParam String by) {
        List<MerchantDTO> filteredMerchants = merchantService.filterMerchants(by);
        return ResponseHandler.generateResponseSuccess(filteredMerchants);
    }

    @GetMapping("/open")
    public ResponseEntity<Object> getOpenMerchants() {
        List<MerchantDTO> merchantsOpen = merchantService.getOpenMerchants();
        return ResponseHandler.generateResponseSuccess(merchantsOpen);
    }

    @GetMapping("/report")
    public ResponseEntity<byte[]> generateReport(
            @RequestParam UUID id,
            @RequestParam String format,
            @RequestBody RevenueRequestDTO revenueRequestDTO,
            Principal principal) throws JRException {

        byte[] reportBytes = invoiceService
                .generateReport(id, principal.getName(), revenueRequestDTO, format);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "report." + format);

        return new ResponseEntity<>(reportBytes, headers, HttpStatus.OK);
    }

}
