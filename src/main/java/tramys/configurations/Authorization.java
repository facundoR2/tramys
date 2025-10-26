package tramys.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class Authorization {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http)throws Exception {

        http .csrf(csrf -> csrf.disable()) //Deactivate CSRF(para apis rest)
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()
                )
                .httpBasic(basic -> {}); // habilita Basic

        return http.build();
    }
}
