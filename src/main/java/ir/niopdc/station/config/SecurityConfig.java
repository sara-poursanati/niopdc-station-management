package ir.niopdc.station.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    UserDetailsService userDetailsService() {
        var user = User.withUsername("admin")
                .password("{noop}123")
                .authorities("read")
                .build();
        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {
        http
                .authorizeHttpRequests(c -> c.requestMatchers("/page/**").permitAll()) // Allow all requests to /page/*
                .csrf().disable(); // Disable CSRF protection for these endpoints
        http.authorizeHttpRequests(c -> c.requestMatchers("/static/assets/**", "/assets/**").permitAll());
        http.authorizeHttpRequests(c -> c.requestMatchers("/login", "/logout").permitAll().anyRequest().authenticated());
        http.formLogin(form -> form.loginPage("/login").successForwardUrl("/").failureUrl("/login-error").permitAll());
        http.logout(c -> c.logoutUrl("/logout").logoutSuccessUrl("/login"));
        return http.build();
    }
}
