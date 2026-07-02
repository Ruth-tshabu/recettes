package cuisine.com.recettes;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
        throws Exception {
        http
            // Désactive le CSRF pour les endpoints de l'API REST
            .csrf(csrf -> csrf.ignoringRequestMatchers("/api/**"))
            .authorizeHttpRequests(auth ->
                auth
                    // Tout le monde peut voir la liste des avis (UI et API de lecture)
                    .requestMatchers("/reviews", "/api/reviews", "/api/reviews/**")
                    .permitAll()

                    // Seul l'ADMIN peut ajouter, modifier ou supprimer des avis (IHM et API de modification)
                    .requestMatchers("/reviews/new", "/reviews/delete/**")
                    .hasRole("ADMIN")
                    
                    .requestMatchers(HttpMethod.POST, "/api/reviews").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PUT, "/api/reviews/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.DELETE, "/api/reviews/**").hasRole("ADMIN")

                    // Toutes les autres requêtes nécessitent une authentification minimale
                    .anyRequest()
                    .authenticated()
            )
            // Utilise le formulaire de connexion par défaut fourni par Spring Security
            .formLogin(form ->
                form.defaultSuccessUrl("/reviews", true).permitAll()
            )
            // Active l'authentification HTTP Basic pour l'API REST (clients non-navigateurs comme curl, Postman, etc.)
            .httpBasic(Customizer.withDefaults())
            // Configuration de la déconnexion
            .logout(logout -> logout.logoutSuccessUrl("/reviews").permitAll());

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
        // Utilisateur standard
        UserDetails user = User.withUsername("user")
            .password(encoder.encode("user123"))
            .roles("USER")
            .build();

        // Utilisateur Administrateur
        UserDetails admin = User.withUsername("admin")
            .password(encoder.encode("admin123"))
            .roles("ADMIN")
            .build();

        return new InMemoryUserDetailsManager(user, admin);
    }
}
