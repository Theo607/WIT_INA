package eu.jpereira.trainings.designpatterns.creational.abstractfactory;
import eu.jpereira.trainings.designpatterns.creational.abstractfactory.json.*;

public class JSONReportFactory implements ReportFactory {
    @Override
    public ReportHeader createHeader() {
        return new JSONReportHeader();
    }

    @Override
    public ReportBody createBody() {
        return new JSONReportBody();
    }

    @Override
    public ReportFooter createFooter() {
        return new JSONReportFooter();
    }
}

