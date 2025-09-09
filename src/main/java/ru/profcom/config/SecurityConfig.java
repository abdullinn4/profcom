package ru.profcom.config;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors(cors -> cors.configurationSource(corsConfigurationSource())) // Добавьте это
            .authorizeHttpRequests(auth
                -> auth
                       // .requestMatchers("/", "/index.html", "/favicon.ico", "/assets/**").permitAll()
                       .requestMatchers("/profcom/profile")
                       .authenticated()
                       .anyRequest()
                       .authenticated())
            .oauth2Login(oauth2
                -> oauth2.loginPage("/oauth2/authorization/uenv")
                       .successHandler(oAuth2LoginSuccessHandler)
                       .defaultSuccessUrl("http://localhost:5173/profile", true)
                       .failureHandler((request, response, exception) -> {
                           response.sendRedirect("http://localhost:5173/login-failed");
                       }))

            .logout(logout -> logout.logoutSuccessUrl("http://localhost:5173/home").permitAll())
            .csrf(csrf -> csrf.ignoringRequestMatchers("/profcom/**"));

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5173"));
        configuration.setAllowedMethods(List.of("*"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
