package com.epam.cashierregister.controllers.servlets.frontController;

import com.epam.cashierregister.controllers.servlets.frontController.commands.UnknownCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "FrontControllerServlet", value = "/frontController")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 10,      // 10 MB
        maxRequestSize = 1024 * 1024 * 100   // 100 MB
)
public class FrontControllerServlet extends HttpServlet {
    static Logger LOG = LogManager.getLogger(FrontControllerServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        FrontCommand command = getCommand(req);
        command.initCommand(getServletContext(), req, resp);
        if (command.filter()){
            command.process();
        }
    }

    protected FrontCommand getCommand(HttpServletRequest req) {
        try {
            Class type = Class.forName(String.format("com.epam.cashierregister.controllers.servlets.frontController.commands.%sCommand",
                    req.getParameter("cmd")));
            return (FrontCommand) type.asSubclass(FrontCommand.class).getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            return new UnknownCommand();
        }
    }

}
