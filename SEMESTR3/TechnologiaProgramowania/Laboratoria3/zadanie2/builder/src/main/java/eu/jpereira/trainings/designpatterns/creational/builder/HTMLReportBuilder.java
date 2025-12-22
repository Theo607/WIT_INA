package eu.jpereira.trainings.designpatterns.creational.builder;

import eu.jpereira.trainings.designpatterns.creational.builder.model.Report;
import eu.jpereira.trainings.designpatterns.creational.builder.HTMLReportBody;

public class HTMLReportBuilder implements ReportBuilder {

    private HTMLReportBody reportBody = new HTMLReportBody();
    private Report report = new Report();

    @Override
    public void setCustomerInfo(String name, String phone) {
        reportBody.putContent("<span class=\"customerName\">");
        reportBody.putContent(name);
        reportBody.putContent("</span><span class=\"customerPhone\">");
        reportBody.putContent(phone);
        reportBody.putContent("</span>");
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
        reportBody.putContent("</items>");
        report.setReportBody(reportBody);
        return report;
    }
}

