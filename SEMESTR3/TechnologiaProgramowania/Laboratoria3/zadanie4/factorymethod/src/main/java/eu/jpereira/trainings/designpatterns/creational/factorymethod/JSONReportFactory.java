package eu.jpereira.trainings.designpatterns.creational.factorymethod;
// JSON


public class JSONReportFactory implements ReportFactory {
    @Override
    public Report createReport() {
        return new JSONReport();
    }
}


