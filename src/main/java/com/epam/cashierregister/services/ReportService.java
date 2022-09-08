package com.epam.cashierregister.services;

import com.epam.cashierregister.services.entities.employee.Employee;
import com.epam.cashierregister.services.entities.report.Report;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Class for managing report
 */
public class ReportService {
    static Logger LOG = LogManager.getLogger(ReportService.class);

    private Report report;

    public ReportService() {
        report = new Report();
    }

    /**
     * Added selling information
     *
     * @param checks count of selling checks
     * @param sum    total sum of selling checks
     */
    public void addSelling(int checks, BigDecimal sum) {
        LOG.info("Added +{} selling checks", checks);
        report.getSelling().setNumberOfSellingChecks(report.getSelling().getNumberOfSellingChecks() + checks);
        report.getSelling().setSellingSum(report.getSelling().getSellingSum().add(sum));
    }

    /**
     * @param number count of returned goods
     * @param sum    total sum of returned products
     */
    public void addReturned(int number, BigDecimal sum) {
        LOG.info("Returned +{} numbers of goods", number);
        report.getReturned().setNumberOfReturningGoods(report.getReturned().getNumberOfReturningGoods() + number);
        report.getReturned().setReturnedSum(report.getReturned().getReturnedSum().add(sum));
    }

    /**
     * @param employee employee who generate report
     * @return generated <code>Report</code>
     */
    public Report generateReport(Employee employee) {
        return new Report(employee, report.getCreatedDate(),
                new Timestamp(System.currentTimeMillis()), report.getSelling(),
                report.getReturned(), report.getSelling().getSellingSum().subtract(report.getReturned().getReturnedSum()));
    }

    /**
     * invalidate current report
     */
    public void invalidate() {
        LOG.info("Report was invalidate");
        report = new Report();
    }

    public Report getReport() {
        return report;
    }
}
