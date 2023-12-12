package id.achfajar.challenge6.security.jwt;

import id.achfajar.challenge6.security.service.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class AuthTokenFilter extends OncePerRequestFilter {

    @Autowired
    UserDetailsServiceImpl userDetailsService;
    @Autowired
    JwtUtils jwtUtils;

    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String jwt = parsetJwt(request);
        if (jwt !=null && jwtUtils.validateJwtToken(jwt, response)) {
            String username = jwtUtils.getUsername(jwt);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
//        try {
//            String jwt = parsetJwt(request);
//            if (jwtUtils.validateJwtToken(jwt, response)) {
//                String username = jwtUtils.getUsername(jwt);
//                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//                UsernamePasswordAuthenticationToken authentication =
//                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//
//                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//            }
//            filterChain.doFilter(request, response);
//        } catch (IllegalArgumentException e) {
//            // Tangani kasus kesalahan
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            response.setContentType("application/json");
//            try (PrintWriter writer = response.getWriter()) {
//                writer.print("{\"error ndul\": \"Unauthorized: " + e.getMessage() + "\"}");
//            } catch (IOException ioException) {
//                logger.error("Kesalahan saat menulis ke respons");
//            }
//        }
    }



    private String parsetJwt(HttpServletRequest request) {
        String headerAuthorization = request.getHeader("Authorization");

        if(StringUtils.hasText(headerAuthorization) && headerAuthorization.startsWith("Bearer ")) {
            return headerAuthorization.substring(7);
        } else {
            logger.error("Invalid or missing Authorization header");
            return null;
        }
    }

}
