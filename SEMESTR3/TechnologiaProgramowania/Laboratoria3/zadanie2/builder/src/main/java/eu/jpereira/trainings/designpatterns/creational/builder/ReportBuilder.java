package eu.jpereira.trainings.designpatterns.creational.builder;

import eu.jpereira.trainings.designpatterns.creational.builder.model.Report;

public interface ReportBuilder {
    void setCustomerInfo(String name, String phone);
    void addItem(String name, int quantity, double price);
    Report getReport();
}

