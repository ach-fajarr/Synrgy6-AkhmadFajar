package id.achfajar.challenge8.service;

import id.achfajar.challenge8.exception.ExistingResourceFoundException;
import id.achfajar.challenge8.exception.ResourceNotFoundException;
import id.achfajar.challenge8.dto.OrderDetailDTO;
import id.achfajar.challenge8.model.OrderDetail;
import id.achfajar.challenge8.model.Product;
import id.achfajar.challenge8.repository.OrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private ProductService productService;


    @Override
    public OrderDetailDTO createOrderDetail(UUID productId, int quantity) {
        Product product = productService.getProductById(productId);
        List<OrderDetail> existingOrderDetail = orderDetailRepository.findByProduct(product);
        if (existingOrderDetail == null || existingOrderDetail.isEmpty()) {
            return newOrderDetailwithReturnDTO(product, quantity);
        }
        for (OrderDetail orderDetail : existingOrderDetail) {
            if (orderDetail.getOrders() == null) {
                return updateOrderDetail(orderDetail.getId(), quantity);
            }
        }
        return newOrderDetailwithReturnDTO(product, quantity);
    }

    private OrderDetailDTO newOrderDetailwithReturnDTO(Product product, int quantity) {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setProduct(product);
        orderDetail.setQuantity(quantity);
        orderDetail.setTotal_price(calculateDiscountedPrice(
                product.getPrice(), product.getDiscount(), quantity)
        );
        orderDetail.setInfo(getInfoProduct(product));
        orderDetailRepository.save(orderDetail);
        return convertToDTO(orderDetail);
    }

    @Override
    public OrderDetailDTO updateOrderDetail(UUID orderDetailId, int newQuantity) {

        OrderDetail orderDetail = orderDetailRepository.findById(orderDetailId)
                .orElseThrow(() -> new ResourceNotFoundException(orderDetailId));
        Product product = orderDetail.getProduct();

        if(orderDetail.getOrders()==null){
            int quantity = orderDetail.getQuantity()+newQuantity;
            orderDetail.setQuantity(quantity);
            orderDetail.setTotal_price(calculateDiscountedPrice(
                    product.getPrice(), product.getDiscount(), quantity)
            );
            orderDetailRepository.save(orderDetail);
            return convertToDTO(orderDetail);
        } else {
            throw new ExistingResourceFoundException("Order detail telah dibayar, tidak dapat diubah");
        }
    }
    private int calculateDiscountedPrice(int originalPrice, Integer discount, int quantity) {
        if (discount != null) {
            double discountPercentage = (double) discount / 100.0;
            double discountedAmount = originalPrice * discountPercentage;
            double discountedPrice = originalPrice - discountedAmount;
            return (int) (discountedPrice * quantity);
        } else {
            return originalPrice * quantity;
        }
    }
    private String getInfoProduct(Product product) {
        if(product.getDiscount()==null){
            return "Diskon produk: tidak ada";
        } else {
            return "Diskon produk: "+product.getDiscount()+"%";
        }
    }

    @Override
    public OrderDetailDTO deleteOrderDetail(UUID orderDetailId) {
        OrderDetail orderDetail = orderDetailRepository.findById(orderDetailId)
                .orElseThrow(()-> new ResourceNotFoundException(orderDetailId));
        orderDetailRepository.delete(orderDetail);
        return convertToDTO(orderDetail);
    }

    @Override
    public List<OrderDetailDTO> getAll() {
        return orderDetailRepository.findAll().stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Override
    public OrderDetailDTO convertToDTO(OrderDetail orderDetail) {
        OrderDetailDTO orderDetailDTO = new OrderDetailDTO();
        orderDetailDTO.setId(orderDetail.getId());
        orderDetailDTO.setQuantity(orderDetail.getQuantity());
        orderDetailDTO.setTotalPrice(orderDetail.getTotal_price());
        orderDetailDTO.setProductId(orderDetail.getProduct().getId());
        orderDetailDTO.setProductName(orderDetail.getProduct().getProduct_name());
        orderDetailDTO.setPrice(orderDetail.getProduct().getPrice());
        orderDetailDTO.setInfo(orderDetail.getInfo());
        return orderDetailDTO;
    }

    @Override
    public OrderDetail findByID(UUID id) {
        return orderDetailRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException(id));
    }

    @Override
    public List<UUID> getAllID() {
        List<UUID> uuidList = new ArrayList<>();
        List<OrderDetail> orderDetailList = new ArrayList<>(orderDetailRepository.findAll());
        for (OrderDetail od : orderDetailList) {
            if (od.getOrders() == null) {
                uuidList.add(od.getId());
            }
        }
        return uuidList;
    }
}
