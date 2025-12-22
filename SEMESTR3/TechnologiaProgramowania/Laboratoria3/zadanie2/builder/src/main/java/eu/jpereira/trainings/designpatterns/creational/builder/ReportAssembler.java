package eu.jpereira.trainings.designpatterns.creational.builder;

import eu.jpereira.trainings.designpatterns.creational.builder.model.SaleEntry;
import eu.jpereira.trainings.designpatterns.creational.builder.model.SoldItem;
import eu.jpereira.trainings.designpatterns.creational.builder.model.Report;

public class ReportAssembler {

    private SaleEntry saleEntry;

    public void setSaleEntry(SaleEntry saleEntry) {
        this.saleEntry = saleEntry;
    }

    public Report getReport(ReportBuilder builder) {
        builder.setCustomerInfo(saleEntry.getCustomer().getName(), saleEntry.getCustomer().getPhone());
        for (SoldItem item : saleEntry.getSoldItems()) {
            builder.addItem(item.getName(), item.getQuantity(), item.getUnitPrice());
        }
        return builder.getReport();
    }
}

