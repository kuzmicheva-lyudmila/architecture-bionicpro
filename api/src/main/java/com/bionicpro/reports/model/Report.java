package com.bionicpro.reports.model;

public class Report {

    private int userId;
    private String reportData;

    public Report(int userId, String reportData) {
        this.userId = userId;
        this.reportData = reportData;
    }

    // Геттеры и сеттеры
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getReportData() {
        return reportData;
    }

    public void setReportData(String reportData) {
        this.reportData = reportData;
    }
}
