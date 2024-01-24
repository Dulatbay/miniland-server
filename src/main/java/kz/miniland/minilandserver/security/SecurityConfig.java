package kz.miniland.minilandserver.security;

import lombok.RequiredArgsConstructor;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.internal.ResteasyClientBuilderImpl;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.oauth2.server.resource.authentication.DelegatingJwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static kz.miniland.minilandserver.constants.ValueConstants.KEYCLOAK_REALM;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final KeycloakJwtRolesConverter keycloakJwtRolesConverter;

    @Bean
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        DelegatingJwtGrantedAuthoritiesConverter authoritiesConverter =
                new DelegatingJwtGrantedAuthoritiesConverter(
                        new JwtGrantedAuthoritiesConverter(),
                        keycloakJwtRolesConverter);

        http.oauth2ResourceServer()
                .jwt()
                .jwtAuthenticationConverter(jwt -> new JwtAuthenticationToken(jwt, authoritiesConverter.convert(jwt)));

        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .httpBasic().disable()
                .cors(Customizer.withDefaults())
                .csrf().disable()
                .authorizeHttpRequests((authz) -> authz
//                        .requestMatchers(HttpMethod.GET, "/**")
//                        .authenticated()
//                        .requestMatchers(HttpMethod.POST, "/**")
//                        .authenticated()
//                        .requestMatchers(HttpMethod.DELETE, "/**")
//                        .authenticated()
//                        .requestMatchers(HttpMethod.PUT, "/**")
//                        .authenticated()
//                        .requestMatchers(HttpMethod.PATCH, "/**")
//                        .authenticated()
                        .anyRequest()
                        .permitAll()
                );

        http.exceptionHandling().accessDeniedHandler(accessDeniedHandler());
        http.addFilterBefore(new CustomCorsFilter(), ChannelProcessingFilter.class);

        return http.build();
    }


    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowedMethods(List.of("GET", "POST", "DELETE", "PATCH", "PUT"));
        configuration.setAllowedOriginPatterns(List.of("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


}
