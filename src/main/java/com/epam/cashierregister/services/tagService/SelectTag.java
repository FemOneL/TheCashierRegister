package com.epam.cashierregister.services.tagService;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;


/**
 * Tag descriptor for selected current employee role
 */
public class SelectTag extends TagSupport {
    private String role;
    private String emp;

    @Override
    public int doStartTag() {
        JspWriter out = pageContext.getOut();
        try {
            if (role.equals(emp)) {
                out.print(String.format("<option selected>%s</option>", role));
            } else {
                out.print(String.format("<option>%s</option>", role));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return SKIP_BODY;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmp() {
        return emp;
    }

    public void setEmp(String emp) {
        this.emp = emp;
    }
}
