package id.achfajar.challenge4.controller;

import id.achfajar.challenge4.model.*;
import id.achfajar.challenge4.service.MerchantService;
import id.achfajar.challenge4.service.OrderService;
import id.achfajar.challenge4.view.ErrorView;
import id.achfajar.challenge4.view.GeneralView;
import id.achfajar.challenge4.view.OrderView;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class OrderController {

    @Autowired
    OrderService orderService;
    @Autowired
    MerchantService merchantService;

    @Setter
    private Product thisProduct;
    ErrorView viewE = new ErrorView();
    OrderView viewO = new OrderView();
    BinarFudController c = new BinarFudController();
    List<OrderDetail> orderDetailList = new ArrayList<>();
    private Map<Integer, Product> productMap = new HashMap<>();


    public void mapAllProduct(){
        List<Product> productList = merchantService.getAllProduct();
        if (productList.isEmpty()){
            System.out.println("Produk Kosong");
        } else {
            int i = 1;
            for (Product pd : productList){
                productMap.put(i, pd);
                i++;
            }
        }
    }
    public void printProduct(){
        productMap.forEach((i, p) -> {
            Merchant m = p.getMerchant();
            System.out.println("\n"+i+". "+p.getProduct_name()+"\t"+c.setCurrency(p.getPrice())+
                    "\n\t\t"+m.getMerchant_name()+"\t"+m.getMerchant_location()+"\t"+m.getStatus());
        });
    }
    private void findProduct(int input){
        productMap.keySet().stream()
                .filter(key -> key == input)
                .findFirst()
                .ifPresentOrElse(
                        key -> {
                            Product product = productMap.get(key);
                            setThisProduct(product);
                        },
                        viewE::wrongInput
                );
    }
    private void infoProduct(){
        System.out.println(thisProduct.getProduct_name()+" | " +thisProduct.getPrice());
        System.out.println("\t\t"+thisProduct.getMerchant().getMerchant_name()+" "+
                thisProduct.getMerchant().getMerchant_location()+" | "+
                thisProduct.getMerchant().getStatus());
    }
    public void createOrderDetail(int input){
        findProduct(input);
        viewO.headerInfo("Pesan makanan");
        infoProduct();
        viewO.quantity();
        int quantity = c.inputInt();
        if (findOrderListByName(thisProduct.getProduct_name()) == null){
            newOrderDetail(quantity);
        } else {
            updateDetailOrder(quantity);
        }

    }

    private void updateDetailOrder(int quantity) {
        OrderDetail getItem = findOrderListByName(thisProduct.getProduct_name());
        int qty = getItem.getQuantity();
        int updateQty = qty+quantity;
        double totalPrice = updateQty * thisProduct.getPrice();
        getItem.setQuantity(updateQty);
        getItem.setTotal_price(totalPrice);
    }

    private void newOrderDetail(int quantity){
        double totalPrice = quantity * thisProduct.getPrice();
        OrderDetail od = new OrderDetail();
        od.setProduct(thisProduct);
        od.setQuantity(quantity);
        od.setTotal_price(totalPrice);
        orderDetailList.add(od);
    }
    public void printOrderDetail(){
        int totalQuantity = 0;
        double grandPrice = 0;
        if (orderDetailList.isEmpty()){
            System.out.println("Kamu belum memesan makanan");
        } else {
            String fmt = "%-17s %-10d %-17.2f%n";
            String fmt2 = "%-17s %-10s %-17s%n";
            System.out.printf(fmt2, "Produk", "Jumlah", "TotalHarga");
            for (OrderDetail o : orderDetailList){
                System.out.printf(fmt,o.getProduct().getProduct_name(),o.getQuantity(),o.getTotal_price());
                totalQuantity += o.getQuantity();
                grandPrice += o.getTotal_price();
            }
            System.out.println(GeneralView.LINE2+ "\n \n");
            System.out.printf(fmt, "", totalQuantity, grandPrice);
        }
    }

    public OrderDetail findOrderListByName(String name) {
        for (OrderDetail od : orderDetailList) {
            if (od.getProduct().getProduct_name().equals(name)) {
                return od;
            }
        }
        return null;
    }

    public void cancelOrder(Users activeUser) {
        viewO.cancel();
        String input = c.inputLine();
        if (input.equals("y")) {
            Order od = new Order();
            od.setOrder_time(LocalDateTime.now());
            od.setDestination_address("pesanan batal");
            od.setCompleted(false);
            od.setUsers(activeUser);
            od.setOrderDetailList(orderDetailList);
            orderService.saveOrder(od);
            orderDetailList.clear();
        } else cancelOrder(activeUser);
    }

    public void confirmThisOrder(Users activeUser) {
        if (orderDetailList.isEmpty()){
            viewO.errorMsg();
        } else {
            viewO.fieldAddress();
            String address = c.inputLine();

            Order od = new Order();
            od.setDestination_address(address);
            od.setOrder_time(LocalDateTime.now());
            od.setUsers(activeUser);
            od.setCompleted(true);
            od.setOrderDetailList(orderDetailList);
            orderService.saveOrder(od);
            afterOrderHandle(od);
        }
    }

    private void afterOrderHandle(Order order) {
        for (OrderDetail od : orderDetailList){
            od.setOrders(order);
            orderService.saveOrderDetails(od);
        }
        orderDetailList.clear();
    }

    public void historyOrder(Users user) {
        viewO.headerInfo("Daftar riwayat pesanan anda");
        List<Order> orders = orderService.getAllByUser(user);
        String fmt = "%-3d %-15s %-20s %-17s%n";
        String fmt2 = "%-3s %-15s %-20s %-17s%n";
        System.out.printf(fmt2,"", "Waktu", "Alamat pengiriman", "Status");
        int i=1;
        for (Order od : orders){
            System.out.printf(fmt, i, c.formatTime(od.getOrder_time()), od.getDestination_address(), od.getStatus());
            System.out.println("\tProduk yang dipesan :");
            List<OrderDetail> odt = od.getOrderDetailList();
            odt.forEach((o)->
                    System.out.println("\t- "+o.getProduct().getProduct_name()+" | "
                            +o.getQuantity()+" | "+c.setCurrency(o.getTotal_price()))
            );
            i++;
        }
        viewO.backOption();
        c.inputLine();
    }
}
