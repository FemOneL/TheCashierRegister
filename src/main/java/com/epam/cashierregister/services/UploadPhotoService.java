package com.epam.cashierregister.services;

import com.epam.cashierregister.controllers.servlets.frontController.FrontCommand;
import com.epam.cashierregister.services.entities.employee.Employee;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.Collection;

public class UploadPhotoService {
    protected static Logger LOG = LogManager.getLogger(UploadPhotoService.class);

    public static String uploadPhoto(HttpServletRequest req, String folder) throws ServletException, IOException {
        String name = null;
        Employee emp = (Employee) req.getSession().getAttribute("employee");
        Collection<Part> parts = req.getParts();
        String realPath = req.getServletContext().getRealPath("images/" + folder);
        for (Part part : parts) {
            try {
                if (part.getSubmittedFileName() != null) {
                    LOG.info("try upload image in {}", realPath);
                    part.write(realPath + "/" + emp.getId() + "_" + part.getSubmittedFileName());
                    LOG.info("upload image {}", part.getSubmittedFileName());
                    if (!part.getSubmittedFileName().equals("")) {
                        name = emp.getId() + "_" + part.getSubmittedFileName();
                    }
                }
            } catch (IOException e) {
                LOG.error("Can not upload image {} ", part.getSubmittedFileName());
                return null;
            }
        }
        return name;
    }

}
