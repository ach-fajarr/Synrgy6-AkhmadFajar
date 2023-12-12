package id.achfajar.challenge;

import id.achfajar.challenge6.config.WebSecurityConfig;
import id.achfajar.challenge6.controller.ProductController;
import id.achfajar.challenge6.repository.ProductRepository;
import id.achfajar.challenge6.repository.UsersRepository;
import id.achfajar.challenge6.security.jwt.AuthEntryPointJwt;
import id.achfajar.challenge6.security.jwt.JwtUtils;
import id.achfajar.challenge6.security.service.UserDetailsServiceImpl;
import id.achfajar.challenge6.service.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(ProductController.class)
@Import({WebSecurityConfig.class, JwtUtils.class, ProductServiceImpl.class})
public class WebMvcDemoTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    ProductRepository productRepository;

    @MockBean
    AuthEntryPointJwt authEntryPointJwt;

    @MockBean
    UserDetailsServiceImpl userDetailsService;

    @MockBean
    UsersRepository userRepository;

    @Test
    void productActiveShouldReturnProducts() throws Exception {

        String responseJson = "{\"data\": [{\"merchantID\": \"ccf3bcc2-8e62-4cd4-a8d6-8fdf6c9b1614\",\"merchantName\": \"Resto sukses\"\"productId\": \"0e1061ed-c7fb-4cde-8942-73ba4ce0822f\",\"productName\": \"Matcha\",\"price\": 15000}],\"message\": \"success\",\"status\":\"200\"}";
        mockMvc.perform(get("/api/products/0e1061ed-c7fb-4cde-8942-73ba4ce0822f"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().string(responseJson));
    }
}
