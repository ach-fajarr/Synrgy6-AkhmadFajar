package id.achfajar.challenge6.service;

import id.achfajar.challenge6.dto.InvoiceReportDTO;
import id.achfajar.challenge6.dto.MerchantDTO;
import id.achfajar.challenge6.dto.request.RevenueRequestDTO;
import id.achfajar.challenge6.model.OrderDetail;
import id.achfajar.challenge6.model.Users;
import id.achfajar.challenge6.repository.OrderDetailRepository;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private MerchantService merchantService;
    private Map<String,Object> parameter =  new HashMap<>();

    @Override
    public byte[] generateReport(UUID merchantId, String username,
                                 RevenueRequestDTO requestDTO,
                                 String format) throws JRException {

        List<OrderDetail> orderDetails = new ArrayList<>();
        Users users = userService.getUsersByUsername(username);
        UUID userId = users.getId();
        String request = requestDTO.getRequestBy();
        String periodeInfo = "";

        if(request.equals("yearly")) {
            orderDetails = orderDetailRepository.findOrderDetailsByYear(
                            merchantId, userId,
                            requestDTO.getYear());
            periodeInfo = "tahun "+ requestDTO.getYear();
        }
        else if (request.equals("monthly")) {
            orderDetails = orderDetailRepository.findOrderDetailsByMonth(
                            merchantId, userId,
                            requestDTO.getMonth(),
                            requestDTO.getYear());
            periodeInfo = "bulan " + Month.of(requestDTO.getMonth()) + requestDTO.getYear();
        }
        else if (request.equals("weekly")) {
            LocalDate startDate = requestDTO.getStartDate().with(DayOfWeek.MONDAY);
            LocalDate endDate = startDate.plusDays(6);
            orderDetails = orderDetailRepository.findOrderDetailsByCustomDate(
                            merchantId, userId,
                            startDate, endDate);
            periodeInfo = getRangeDate(startDate, endDate)+startDate.getMonth()+" "+startDate.getYear();
        }
        else if (request.equals("custom")) {
            orderDetails = orderDetailRepository.findOrderDetailsByCustomDate(
                            merchantId, userId,
                            requestDTO.getStartDate(),
                            requestDTO.getEndDate());
            periodeInfo = requestDTO.getStartDate()+" sampai "+requestDTO.getEndDate();
        }
        MerchantDTO merchant = merchantService.getMerchantById(merchantId);
        parameter.put("merchantName", merchant.getMerchantName());
        parameter.put("merchantLoc", merchant.getMerchantLocation());
        parameter.put("periodeInfo", periodeInfo);
        parameter.put("sellerName", users.getFirstName()+" "+users.getLastName());
        List<InvoiceReportDTO> reportData = convertToReportDTO(orderDetails);
        return generateJasperReport(reportData, format);
    }

    private List<InvoiceReportDTO> convertToReportDTO(List<OrderDetail> orderDetails) {
        List<InvoiceReportDTO> reportData = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyy-MM-dd HH.mm");
        for (OrderDetail orderDetail : orderDetails) {
            InvoiceReportDTO dto = new InvoiceReportDTO();
            dto.setProductName(orderDetail.getProduct().getProduct_name());
            dto.setOrderTime(orderDetail.getOrders().getOrder_time().format(formatter));
            dto.setTotalOrder(orderDetail.getQuantity());
            dto.setTotalPrice(orderDetail.getTotal_price());
            reportData.add(dto);
        }
        return reportData;
    }

    private byte[] generateJasperReport(List<InvoiceReportDTO> reportData, String format) throws JRException {
        JasperPrint jasperPrint = createJasperPrint(reportData);
        return exportReport(jasperPrint, format);
    }

    private JasperPrint createJasperPrint(List<InvoiceReportDTO> reportData) throws JRException {
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(reportData);

        InputStream templateStream = getClass().getResourceAsStream("/templates/Fixx.jrxml");

        JasperReport jasperReport = JasperCompileManager.compileReport(templateStream);

        parameter.put("dataProduct", dataSource);

        return JasperFillManager.fillReport(jasperReport, parameter, new JREmptyDataSource());
    }

    private byte[] exportReport(JasperPrint jasperPrint, String format) {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            switch (format.toLowerCase()) {
                case "pdf":
                    JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
                    break;
                case "html":
                    JasperExportManager.exportReportToHtmlFile(jasperPrint, String.valueOf(outputStream));
                    break;
                case "csv":
                    //csv
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported format: " + format);
            }
        } catch (JRException e) {
            e.printStackTrace();
        }
        return outputStream.toByteArray();
    }

    private String getRangeDate(LocalDate startDate, LocalDate endDate){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd");
        return startDate.format(formatter)+"-"+endDate.format(formatter)+" ";
    }
}
