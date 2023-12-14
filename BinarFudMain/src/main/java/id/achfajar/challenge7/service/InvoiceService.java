package id.achfajar.challenge7.service;

import id.achfajar.challenge7.dto.request.RevenueRequestDTO;
import net.sf.jasperreports.engine.JRException;

import java.util.UUID;

public interface InvoiceService {
    byte[] generateReport(UUID merchantId, String username, RevenueRequestDTO requestDTO, String format) throws JRException;
}