package com.amica.mars;

import java.util.ArrayList;
import java.util.List;

public class Recorder implements Telemetry {
    private final List<Report> reports = new ArrayList<>();

    @Override
    public void sendReport(Report report) {
        reports.add(report);
    }

    public List<Report> getReports() {
        return reports;
    }
}
