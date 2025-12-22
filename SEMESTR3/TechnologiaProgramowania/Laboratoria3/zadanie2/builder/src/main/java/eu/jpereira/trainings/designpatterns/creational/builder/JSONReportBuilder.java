package eu.jpereira.trainings.designpatterns.creational.builder;

import eu.jpereira.trainings.designpatterns.creational.builder.json.JSONReportBody;
import eu.jpereira.trainings.designpatterns.creational.builder.model.Report;

public class JSONReportBuilder implements ReportBuilder {

    private JSONReportBody reportBody = new JSONReportBody();
    private Report report = new Report();
    private boolean firstItem = true;

    @Override
    public void setCustomerInfo(String name, String phone) {
        reportBody.addContent("sale:{customer:{");
        reportBody.addContent("name:\"" + name + "\",phone:\"" + phone + "\"}");
        reportBody.addContent(",items:[");
    }

    @Override
    public void addItem(String name, int quantity, double price) {
        if (!firstItem) reportBody.addContent(",");
        reportBody.addContent("{name:\"" + name + "\",quantity:" + quantity + ",price:" + price + "}");
        firstItem = false;
    }

    @Override
    public Report getReport() {
        reportBody.addContent("]}"); // close JSON
        report.setReportBody(reportBody);
        return report;
    }
}

