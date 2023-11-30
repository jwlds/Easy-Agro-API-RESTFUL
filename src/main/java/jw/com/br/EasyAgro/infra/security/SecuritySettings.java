package jw.com.br.EasyAgro.infra.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecuritySettings {

    @Autowired
    private SecurityFilter securityFilter;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(req -> {
                    req.requestMatchers(HttpMethod.POST, "/login").permitAll();
                    req.requestMatchers( "/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**").permitAll();
                    req.requestMatchers(HttpMethod.GET, "/user/**").permitAll();
                    req.requestMatchers(HttpMethod.POST, "/api/**").permitAll();
                    req.requestMatchers(HttpMethod.DELETE, "/api/**").permitAll();
                    req.requestMatchers(HttpMethod.POST, "/user/**").permitAll();
                    req.requestMatchers(HttpMethod.PUT, "/user/**").permitAll();
                    req.requestMatchers(HttpMethod.PATCH, "/user/**").permitAll();
                    req.requestMatchers(HttpMethod.GET, "/api/products/**").permitAll();
                    req.requestMatchers(HttpMethod.POST, "/process_payment/**").permitAll();
                    req.requestMatchers(HttpMethod.GET, "/api/culture/**").permitAll();
                    req.requestMatchers(HttpMethod.POST, "/api/culture/**").permitAll();
                    //req.requestMatchers(HttpMethod.POST, "/api/products/**").permitAll();
                    req.requestMatchers(HttpMethod.GET, "/api/address/**").permitAll();
                    req.requestMatchers(HttpMethod.DELETE, "/user/**").permitAll();
                    req.requestMatchers(HttpMethod.POST, "/user/create").permitAll();
                    req.requestMatchers(HttpMethod.GET, "/api/cepea/**").permitAll();
                    req.anyRequest().authenticated();
                })
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
