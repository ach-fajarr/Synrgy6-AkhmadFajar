package id.achfajar.challenge5.service;

import id.achfajar.challenge5.model.Order;
import id.achfajar.challenge5.model.Users;
import id.achfajar.challenge5.model.dto.OrderDTO;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Override
    public byte[] generateInvoice(UUID orderId, UUID userId) {
        Order order = orderService.getOrderById(orderId);
        Users user = userService.getUsersById(userId);

        try {
            InputStream templateStream = getClass().getResourceAsStream("/templates/invoice_template.jasper");
            JasperReport jasperReport = JasperCompileManager.compileReport(templateStream);

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("order", order);
            parameters.put("user", user);

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(byteArrayOutputStream));
            exporter.exportReport();

            return byteArrayOutputStream.toByteArray();
        } catch (JRException | IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Gagal membuat invoice");
        }
    }
}
