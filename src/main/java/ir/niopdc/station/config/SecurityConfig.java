package ir.niopdc.station.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    public static final String LOGIN_PATH = "/login";

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
        http.authorizeHttpRequests(c -> c.requestMatchers("/static/assets/**", "/assets/**").permitAll());
        http.authorizeHttpRequests(c -> c.requestMatchers(LOGIN_PATH, "/logout").permitAll().anyRequest().authenticated());
        http.formLogin(form -> form.loginPage(LOGIN_PATH).successForwardUrl("/").failureUrl("/login-error").permitAll());
        http.logout(c -> c.logoutUrl("/logout").logoutSuccessUrl(LOGIN_PATH));
        return http.build();
    }
}
