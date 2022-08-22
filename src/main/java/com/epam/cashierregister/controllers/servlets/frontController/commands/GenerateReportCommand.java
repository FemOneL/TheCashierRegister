package com.epam.cashierregister.controllers.servlets.frontController.commands;

import com.epam.cashierregister.controllers.servlets.frontController.FrontCommand;
import com.epam.cashierregister.services.DAO.ReportDAO;
import com.epam.cashierregister.services.entities.employee.Employee;
import com.epam.cashierregister.services.ReportService;
import com.epam.cashierregister.services.entities.report.Report;

import javax.servlet.ServletException;
import java.io.IOException;

public class GenerateReportCommand extends FrontCommand {
    private ReportService report;
    private ReportDAO reportDAO;

    @Override
    public void initContext() throws ServletException {
        report = (ReportService) context.getAttribute("report");
        reportDAO = (ReportDAO) context.getAttribute("ReportDAO");
    }

    @Override
    public boolean filter() throws ServletException, IOException {
        return true;
    }

    @Override
    public void process() throws ServletException, IOException {
        Report newReport = report.generateReport((Employee) req.getSession().getAttribute("employee"));
        if (req.getParameter("type").equals("Z")){
            newReport.getSelling().setId(reportDAO.writeSelling(newReport.getSelling()  ));
            newReport.getReturned().setId(reportDAO.writeReturned(newReport.getReturned()));
            reportDAO.writeReport(newReport);
            report.invalidate();
        }
        req.getSession().setAttribute("report", newReport);
        redirect("report");
    }
}
