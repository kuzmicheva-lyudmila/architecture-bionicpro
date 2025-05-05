package com.bionicpro.reports.controller;

import com.bionicpro.reports.model.Report;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ReportController {

    @GetMapping("/reports")
    public List<Report> getReports(@AuthenticationPrincipal Jwt jwt) {
        String username = jwt.getClaimAsString("preferred_username");
        // Или, если нужен ID: Object userId = jwt.getClaim("sub");

        List<Report> reports = new ArrayList<>();
        reports.add(new Report(username.hashCode(), "Report data for user " + username));
        reports.add(new Report(username.hashCode(), "Another report entry for user " + username));

        return reports;
    }
}
