package co.edu.uniremington.portal.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    @Value("${app.cors.allowed-origins}")
    private String allowedOrigins;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // 👇 NUEVA LÍNEA TEMPORAL: Deja pasar absolutamente todo en Postman para tus
                        // pruebas
                        .anyRequest().permitAll()

                /*
                 * 👇 INICIO DEL BLOQUE COMENTADO TEMPORALMENTE
                 * // Públicas
                 * .requestMatchers("/api/auth/**").permitAll()
                 * .requestMatchers(HttpMethod.GET, "/api/noticias/**").permitAll()
                 * .requestMatchers(HttpMethod.GET, "/api/foros/**").permitAll()
                 * .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                 * // Protegidas por rol
                 * .requestMatchers("/api/usuarios/**").hasRole("ADMIN")
                 * .requestMatchers("/api/docentes/**").hasAnyRole("ADMIN", "TEACHER")
                 * .requestMatchers("/api/estudiantes/**").hasAnyRole("ADMIN", "TEACHER")
                 * // Resto requiere autenticación
                 * .anyRequest().authenticated()
                 */ // 👈 FIN DEL BLOQUE COMENTADO
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Arrays.asList(allowedOrigins.split(",")));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setExposedHeaders(List.of("Authorization"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}