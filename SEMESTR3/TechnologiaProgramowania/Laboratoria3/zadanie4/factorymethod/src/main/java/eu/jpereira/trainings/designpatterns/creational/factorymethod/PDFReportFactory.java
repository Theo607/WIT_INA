package eu.jpereira.trainings.designpatterns.creational.factorymethod;

// PDF
public class PDFReportFactory implements ReportFactory {
    @Override
    public Report createReport() {
        return new PDFReport();
    }
}

