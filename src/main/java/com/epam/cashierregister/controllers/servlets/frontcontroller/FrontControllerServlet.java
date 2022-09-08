package com.epam.cashierregister.controllers.servlets.frontcontroller;

import com.epam.cashierregister.controllers.servlets.frontcontroller.commands.UnknownCommand;
import com.epam.cashierregister.services.exeptions.DatabaseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Main controller for choose command by parameter and execute it
 */
@WebServlet(name = "FrontControllerServlet", value = "/frontController")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1 MB
        maxFileSize = 1024 * 1024 * 10,      // 10 MB
        maxRequestSize = 1024 * 1024 * 100   // 100 MB
)
public class FrontControllerServlet extends HttpServlet {
    static Logger LOG = LogManager.getLogger(FrontControllerServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        FrontCommand command = getCommand(req);
        LOG.debug("Get command: {}", command.getClass());
        try {
            command.initCommand(getServletContext(), req, resp);
        } catch (UnsupportedOperationException e){
            LOG.info("Empty initCommand");
        }
        try {
            if (command.filter()) {
                command.process();
            }
        } catch (DatabaseException e) {
            LOG.error("Problem with filters");
            req.getSession().setAttribute("javax.servlet.error.status_code", e.getErrorCode());
            resp.sendRedirect("errorPage");
        }
    }

    /**
     * choose command by request parameter
     *
     * @param req with command
     * @return object of target command
     */
    protected FrontCommand getCommand(HttpServletRequest req) {
        try {
            Class<?> type = Class.forName(String.format("com.epam.cashierregister.controllers.servlets.frontcontroller.commands.%sCommand",
                    req.getParameter("cmd")));
            return type.asSubclass(FrontCommand.class).getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            return new UnknownCommand();
        }
    }

}
