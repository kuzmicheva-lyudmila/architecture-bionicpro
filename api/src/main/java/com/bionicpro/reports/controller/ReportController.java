package com.bionicpro.reports.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@Slf4j
public class ReportController {

    @GetMapping("/reports")
    @PreAuthorize("hasAuthority('ROLE_prothetic_user')")
    public List<Map<String, Object>> getReports() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("Authorities: " + authentication.getAuthorities());
        log.info("Principal: " + authentication.getPrincipal());

        return List.of(
                Map.of(
                        "id", UUID.randomUUID().toString(),
                        "timestamp", System.currentTimeMillis(),
                        "status", "OK",
                        "summary", "Система функционирует корректно"
                ),
                Map.of(
                        "id", UUID.randomUUID().toString(),
                        "timestamp", System.currentTimeMillis(),
                        "status", "WARNING",
                        "summary", "Обнаружены нерегулярные сигналы"
                )
        );
    }
}
