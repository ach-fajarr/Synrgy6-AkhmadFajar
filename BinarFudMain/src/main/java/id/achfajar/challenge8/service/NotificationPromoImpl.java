package id.achfajar.challenge8.service;

import com.google.firebase.messaging.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import id.achfajar.challenge8.model.Product;
import id.achfajar.challenge8.model.Users;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Service
@Component
public class NotificationPromoImpl implements NotificationPromo{

    private final ProductService productService;
    private final UserService userService;
    private final JavaMailSender javaMailSender;
    private Logger logger = LoggerFactory.getLogger(NotificationPromoImpl.class);

    @Autowired
    public NotificationPromoImpl(ProductService productService,
                                 UserService userService,
                                 JavaMailSender javaMailSender) {
        this.productService = productService;
        this.userService = userService;
        this.javaMailSender = javaMailSender;
    }

    @Scheduled(cron = "0 0/5 * * * *")
    public void sendPromo() {
        System.out.println("start");
        List<Product> discountedProducts = productService.getDiscountedProduct();
        StringBuilder result = new StringBuilder();
        for (Product product : discountedProducts) {
            result
                    .append("<br>").append(product.getProduct_name())
                    .append(" dari harga ").append(product.getPrice())
                    .append(", Promo sebesar ").append(product.getDiscount()).append("%")
                    .append(", menjadi ").append(discountedPrice(product)).append("\n");
        }
        String subject = "Promo BinarFud " + today();
        List<String> email = userService.getSubscribedUsers().stream()
                .map(Users::getEmail)
                .toList();
        for (String mail : email) {
            sendToEmail(mail, subject, result.toString());
        }
        System.out.println("promo dikirimkan pada : " + LocalDateTime.now());
    }

    public void sendToEmail(String to, String subject, String message) {
        MimeMessage msg = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(msg, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(message, true);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        javaMailSender.send(msg);
    }

    private int discountedPrice(Product product) {
        double discountPercentage = (double) product.getDiscount() / 100.0;
        double discountedAmount = product.getPrice() * discountPercentage;
        double discountedPrice = product.getPrice() - discountedAmount;
        return (int) discountedPrice;
    }

    private String today() {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE", new Locale("id"));
        return today.format(formatter);
    }

    /**========================== In App Notif ============================*/

    @Value("${app.firebase-configuration-token}")
    private String token;

    @Override
    public void sendMessageToToken(UUID productID, Integer amount)
            throws InterruptedException, ExecutionException {
        if(amount!=null && amount>0){
            Message message = getPreconfiguredMessageBuilder(productID, amount).build();
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String jsonOutput = gson.toJson(message);
            String response = sendAndGetResponse(message);
            logger.info("Pesan notifikasi terkirim : "+token+", "+response+" msg "+jsonOutput);
        }
    }


    private String sendAndGetResponse(Message message) throws InterruptedException, ExecutionException {
        return FirebaseMessaging.getInstance().sendAsync(message).get();
    }

    private Message.Builder getPreconfiguredMessageBuilder(UUID productID, Integer amount) {
        Notification notification = Notification.builder()
                .setTitle("Promo Terbaru")
                .setBody(bodyNotification(productID, amount))
                .build();
        return Message.builder()
                .setNotification(notification).setToken(token);
    }

    private String bodyNotification(UUID productID, Integer amount){
        Product product = productService.getProductById(productID);
        return product.getProduct_name()+" diskon sebesar "+amount+"%";
    }

}
