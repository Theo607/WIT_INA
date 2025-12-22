package eu.jpereira.trainings.designpatterns.creational.abstractfactory;
public interface ReportFactory {
    ReportHeader createHeader();
    ReportBody createBody();
    ReportFooter createFooter();
}

