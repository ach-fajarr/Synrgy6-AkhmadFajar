package id.achfajar.challenge8.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import id.achfajar.challenge8.dto.producer.EmailProducerDTO;
import id.achfajar.challenge8.dto.request.ResetPasswordDTO;
import id.achfajar.challenge8.exception.ResourceNotFoundException;
import id.achfajar.challenge8.model.Users;
import id.achfajar.challenge8.repository.UsersRepository;
import id.achfajar.challenge8.kafka.EmailProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
public class OTPServiceImpl implements OTPService {

    private final UsersRepository usersRepository;
    private final JavaMailSender javaMailSender;
    private final PasswordEncoder passwordEncoder;
    private final EmailProducer emailProducer;


    @Autowired
    public OTPServiceImpl(UsersRepository usersRepository,
                          JavaMailSender javaMailSender,
                          PasswordEncoder passwordEncoder,
                          EmailProducer emailProducer) {
        this.usersRepository = usersRepository;
        this.javaMailSender =  javaMailSender;
        this.passwordEncoder = passwordEncoder;
        this.emailProducer = emailProducer;
    }

    private LoadingCache<String, Integer> otpCache = CacheBuilder.newBuilder().
            expireAfterWrite(5, TimeUnit.MINUTES) .build(new CacheLoader<String, Integer>() {
                @Override
                public Integer load(String email) throws ExecutionException {
                    return otpCache.getIfPresent(email) != null ? otpCache.get(email) : null;
                }
            });

    @Override
    public String forgotPassword(String email) {

        Optional<Users> users = usersRepository.findByEmail(email);
        if (users.isEmpty()) {
            return "Error : Email tidak ditemukan";
        } else {
            int token = generateToken();
            otpCache.put(email, token);
            EmailProducerDTO emailProducerDTO = new EmailProducerDTO();
            emailProducerDTO.setTo(email);
            emailProducerDTO.setSubject("Kode OTP");
            emailProducerDTO.setMessage("Kode OTP : " + token);
            emailProducer.sendMessage(emailProducerDTO);
            return "Success : kode OTP berhasil dikirim ke email " + email;
        }
    }

    @Override
    public String resetPassword(Integer token, ResetPasswordDTO resetPasswordDTO) {

        String email = resetPasswordDTO.getEmail();
        Integer cachedToken = getOtp(email);

        if(token.equals(cachedToken)){
            Users user = usersRepository.findByEmail(email)
                    .orElseThrow(()-> new ResourceNotFoundException(email));
            user.setPassword(passwordEncoder.encode(resetPasswordDTO.getNewPassword()));
            usersRepository.save(user);
            otpCache.invalidate(email);
            return "Password dari "+ email +" berhasil di ubah";

        } else if (cachedToken==null) {
            return "Token kadaluarsa";

        } else {
            return "Token salah";
        }
    }

    private int generateToken() {
        Random random = new Random();
        return 100000 + random.nextInt(900000);
    }

    public Integer getOtp(String key){
        try{
            return otpCache.get(key);
        }catch (Exception e){
            return null;
        }
    }

}
