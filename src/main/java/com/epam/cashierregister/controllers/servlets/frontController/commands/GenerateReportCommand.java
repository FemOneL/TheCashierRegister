package com.epam.cashierregister.controllers.servlets.frontController.commands;

import com.epam.cashierregister.controllers.servlets.frontController.FrontCommand;
import com.epam.cashierregister.services.DAO.ReportDAO;
import com.epam.cashierregister.services.entities.employee.Employee;
import com.epam.cashierregister.services.ReportService;
import com.epam.cashierregister.services.entities.report.Report;
import com.epam.cashierregister.services.exeptions.DatabaseException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
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
        LOG.info("Generation {} report", req.getParameter("type"));
        HttpSession session = req.getSession();
        Report newReport = report.generateReport((Employee) req.getSession().getAttribute("employee"));
        session.setAttribute("type", req.getParameter("type"));
        try {
            if (req.getParameter("type").equals("Z")){
                newReport.getSelling().setId(reportDAO.writeSelling(newReport.getSelling()));
                newReport.getReturned().setId(reportDAO.writeReturned(newReport.getReturned()));
                reportDAO.writeReport(newReport);
                report.invalidate();
            }
        } catch (DatabaseException e){
            LOG.error("Problem with reports generation");
            req.getSession().setAttribute("javax.servlet.error.status_code", e.getErrorCode());
            redirect("errorPage");
        }
        req.getSession().setAttribute("report", newReport);
        redirect("report");
    }
}
