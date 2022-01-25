package com.dmitri.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.dmitri.constant.WebConstant.*;

@WebFilter("/*")
public class SecurityFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        StringBuffer requestURL = req.getRequestURL();
        if (requestURL != null && requestURL.toString().endsWith("login_script.js")) {
            chain.doFilter(req, res);
        }
        HttpSession session = req.getSession();
        Object userName = session.getAttribute(SESSION_USER_NAME);
        Object userRole = session.getAttribute(SESSION_USER_ROLE);
        Object userId = session.getAttribute(SESSION_USER_ID);
        if (userRole == null) {
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/login");
            requestDispatcher.forward(req, res);
        } else {
//            userRole
            //1 GET USER ROLE FROM SESSION
            //2 IF ROLE ADMIN REDIRECT "/"
            //3 IF ROLE !ADMIN RED "CLIENT.JSP"
            chain.doFilter(req, res);
        }
    }
}
