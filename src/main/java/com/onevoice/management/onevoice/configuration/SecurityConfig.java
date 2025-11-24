package com.onevoice.management.onevoice.configuration;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/", "/login-choice.html",
                                "/individual-login.html", "/panchayath-login.html",
                                "/individual-register.html", "/panchayath-register.html",
                                "/css/**", "/js/**", "/images/**", "/uploads/**",
                                "/individuals/send-otp", "/individuals/register",
                                "/panchayath-api/**"
                        ).permitAll()
                        .requestMatchers("/individuals/**").hasRole("INDIVIDUAL")
                        .requestMatchers("/panchayath/**").hasRole("PANCHAYATH")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login-choice.html")
                        .loginProcessingUrl("/perform_login")
                        .successHandler(customAuthenticationSuccessHandler())
                        .failureUrl("/login-choice.html?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login-choice.html?logout=true")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                );


        return http.build();
    }

    @Bean
    public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return (HttpServletRequest request, HttpServletResponse response, Authentication authentication) -> {

            boolean isIndividual = authentication.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_INDIVIDUAL"));
            boolean isPanchayath = authentication.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_PANCHAYATH"));

            if (isIndividual) {
                response.sendRedirect("/individuals/dashboard");
            } else if (isPanchayath) {
                response.sendRedirect("/panchayath/dashboard");
            } else {
                request.getSession().invalidate();
                response.sendRedirect("/login-choice.html");
            }
        };
    }
}
