package eu.jpereira.trainings.designpatterns.creational.factorymethod;
import eu.jpereira.trainings.designpatterns.creational.factorymethod.Report;
import eu.jpereira.trainings.designpatterns.creational.factorymethod.ReportData;


public class HTMLReportFactory implements ReportFactory {
    @Override
    public Report createReport() {
        return new HTMLReport();
    }
}
