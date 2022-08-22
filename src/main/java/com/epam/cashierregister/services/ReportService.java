package com.epam.cashierregister.services;

import com.epam.cashierregister.services.entities.employee.Employee;
import com.epam.cashierregister.services.entities.report.Report;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

public class ReportService {
    private Report report;

    public ReportService() {
        report = new Report();
    }

    public void addSelling(int checks, BigDecimal sum){
        report.getSelling().setNumberOfSellingChecks(report.getSelling().getNumberOfSellingChecks() + checks);
        report.getSelling().setSellingSum(report.getSelling().getSellingSum().add(sum));
    }

    public void addReturned(int number, BigDecimal sum){
        report.getReturned().setNumberOfReturningGoods(report.getReturned().getNumberOfReturningGoods() + number);
        report.getReturned().setReturnedSum(report.getReturned().getReturnedSum().add(sum));
    }

    public Report generateReport(Employee employee){
        return new Report(employee, report.getCreatedDate(),
                new Timestamp(System.currentTimeMillis()), report.getSelling(),
                report.getReturned(), report.getSelling().getSellingSum().subtract(report.getReturned().getReturnedSum()));
    }

    public void invalidate(){
        report = new Report();
    }

    public Report getReport() {
        return report;
    }
}
