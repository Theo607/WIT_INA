package eu.jpereira.trainings.designpatterns.creational.abstractfactory;
import eu.jpereira.trainings.designpatterns.creational.abstractfactory.xml.*;

public class XMLReportFactory implements ReportFactory {
    @Override
    public ReportHeader createHeader() {
        return new XMLReportHeader();
    }

    @Override
    public ReportBody createBody() {
        return new XMLReportBody();
    }

    @Override
    public ReportFooter createFooter() {
        return new XMLReportFooter();
    }
}

