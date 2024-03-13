package securityConfig;



import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class securityConfig {

    private final JwtConverter jwtAuthConverter;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeRequests(auth ->
                        auth.anyRequest().authenticated());

        http.oauth2ResourceServer(auth ->
                auth.jwt(jwt ->
                        jwt.jwtAuthenticationConverter(jwtAuthConverter)));

        http.sessionManagement(manager ->
                manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.authorizeRequests(authorizeRequests -> authorizeRequests.dispatcherTypeMatchers().authenticated().anyRequest().anonymous());
        http.oauth2Client(Customizer.withDefaults());
        http.oauth2Login(Customizer.withDefaults());
        return http.build();
    }
}
