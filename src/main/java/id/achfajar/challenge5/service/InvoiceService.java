package id.achfajar.challenge5.service;

import java.util.UUID;

public interface InvoiceService {

    byte[] generateInvoice(UUID orderId, UUID userId);
}