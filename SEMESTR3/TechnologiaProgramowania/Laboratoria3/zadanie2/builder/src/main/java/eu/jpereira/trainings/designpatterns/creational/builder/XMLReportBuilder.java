package eu.jpereira.trainings.designpatterns.creational.builder;

import eu.jpereira.trainings.designpatterns.creational.builder.model.Report;
import eu.jpereira.trainings.designpatterns.creational.builder.xml.XMLReportBody;

public class XMLReportBuilder implements ReportBuilder {

    private XMLReportBody reportBody = new XMLReportBody();
    private Report report = new Report();

    @Override
    public void setCustomerInfo(String name, String phone) {
        reportBody.putContent("<sale><customer><name>");
        reportBody.putContent(name);
        reportBody.putContent("</name><phone>");
        reportBody.putContent(phone);
        reportBody.putContent("</phone></customer>");
        reportBody.putContent("<items>");
    }

    @Override
    public void addItem(String name, int quantity, double price) {
        reportBody.putContent("<item><name>");
        reportBody.putContent(name);
        reportBody.putContent("</name><quantity>");
        reportBody.putContent(quantity);
        reportBody.putContent("</quantity><price>");
        reportBody.putContent(price);
        reportBody.putContent("</price></item>");
    }

    @Override
    public Report getReport() {
        reportBody.putContent("</items></sale>");
        report.setReportBody(reportBody);
        return report;
    }
}
