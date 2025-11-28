package com.example.courses_api.security;


import com.example.courses_api.model.User;
import com.example.courses_api.repository.UserRepository;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter implements Filter {
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;

    public JwtAuthenticationFilter(JwtUtils jwtUtils, UserRepository userRepository) {
        this.jwtUtils = jwtUtils;
        this.userRepository = userRepository;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            if (jwtUtils.isTokenValid(token)) {
                String email = jwtUtils.getSubject(token);
                User user = userRepository.findByEmail(email);
                if (user != null) {
                    var authority = new SimpleGrantedAuthority("ROLE_" + user.getRole().name());
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                            user, null, List.of(authority));

                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
        }
        chain.doFilter(req, res);
    }
}
