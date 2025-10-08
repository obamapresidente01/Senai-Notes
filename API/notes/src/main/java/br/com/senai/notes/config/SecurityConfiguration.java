package br.com.senai.notes.config;


import com.nimbusds.jose.jwk.source.ImmutableSecret;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.crypto.spec.SecretKeySpec;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private String jwtSecret = "L-32J8gBv_K)Z&_+:MRq=6a11[Cx3_D'W/tU-SO&8bo'_9(C_*853N*YwLNv>V";

    // 1. Gerador de tokens (jwt Encoder)
    @Bean
    public JwtEncoder jwtEncoder() {
        // cria a chave de criptografia
        var secretKey = new SecretKeySpec(jwtSecret.getBytes(), "HmacSHA256");

        return new NimbusJwtEncoder(new ImmutableSecret<>(secretKey));
    }
    // 2. O VERIFICADOR DE TOKENS (Jwt Decoder)
    @Bean
    public JwtDecoder jwtDecoder() {

        var secretKey = new SecretKeySpec(jwtSecret.getBytes(), "HmacSHA256");

        return NimbusJwtDecoder.withSecretKey(secretKey).macAlgorithm(MacAlgorithm.HS256).build();


    }

    // 3. O CRIPTOGRAFADOR DE SENHAS
    @Bean
    public PasswordEncoder passwordEncoder() {
        // Retorna a implementação BCrypt, o padrão recomendado para criptografar senhas.
        return new BCryptPasswordEncoder();
    }

    // 4. O GERENTE DE AUTENTICAÇÃO
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        // Pega o gerenciador de autenticação padrão do Spring, que já sabe como usar nosso
        // UserDetailsService e PasswordEncoder para validar as credenciais de login.
        return config.getAuthenticationManager();
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        // 1. Defina a origem do seu frontend (4200 é o padrão do Angular)
        config.setAllowedOrigins(List.of("http://localhost:4200"));

        // 2. Defina os métodos HTTP permitidos
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // 3. Permita todos os cabeçalhos
        config.setAllowedHeaders(List.of("*"));

        // 4. Permita o envio de credenciais (tokens, cookies)
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // Aplica as configurações a todas as rotas da API ("/**")
        source.registerCorsConfiguration("/**", config);

        return source;
    }


    // --- O FILTRO DE SEGURANÇA ---

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults())
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(
                        auth -> auth
                                .requestMatchers("/api/auth/**").permitAll()
                                .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                                .requestMatchers("/api/usuarios/**").permitAll()

                                .anyRequest().authenticated()
                )

                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));

//
        return http.build();

    }
}
