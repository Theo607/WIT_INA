package eu.jpereira.trainings.designpatterns.creational.factorymethod;

import java.util.Map;

public class ReportGenerator {

    public Map<String, ReportFactory> reportFactories = Map.of(
        "JSON", new JSONReportFactory(),
        "XML", new XMLReportFactory(),
        "HTML", new HTMLReportFactory(),
        "PDF", new PDFReportFactory()
    );

    public Report generateReport(ReportData data, String type) {
        ReportFactory factory = reportFactories.get(type);
        if (factory == null) return null;

        Report report = factory.createReport();
        report.generateReport(data);

        return report;
    }
}
