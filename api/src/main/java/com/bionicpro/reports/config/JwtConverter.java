package com.bionicpro.reports.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class JwtConverter {

    @Bean
    public Converter<Jwt, Collection<GrantedAuthority>> realmRoleConverter() {
        return new Converter<Jwt, Collection<GrantedAuthority>>() {
            @Override
            public Collection<GrantedAuthority> convert(Jwt jwt) {
                System.out.println(">>> Custom Realm Role Converter invoked");
                Map<String, Object> realmAccess = jwt.getClaim("realm_access");
                if (realmAccess == null || !realmAccess.containsKey("roles")) {
                    return Collections.emptyList();
                }

                @SuppressWarnings("unchecked")
                List<String> roles = (List<String>) realmAccess.get("roles");

                return roles.stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role)) // Добавление префикса ROLE_
                        .collect(Collectors.toList());
            }
        };
    }

    // Бин для JwtAuthenticationConverter с использованием кастомного конвертера
    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter(
            Converter<Jwt, Collection<GrantedAuthority>> realmRoleConverter) {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(realmRoleConverter); // Подключение кастомного конвертера
        return converter;
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        NimbusJwtDecoder jwtDecoder = NimbusJwtDecoder
                .withJwkSetUri("http://keycloak:8080/realms/reports-realm/protocol/openid-connect/certs")
                .build();

        // Удаляем ВСЕ стандартные валидации
        jwtDecoder.setJwtValidator(JwtValidators.createDefault()); // Без проверки issuer

        return jwtDecoder;
    }
}
