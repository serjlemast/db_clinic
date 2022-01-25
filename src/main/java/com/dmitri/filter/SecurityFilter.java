package com.dmitri.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")
public class SecurityFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        StringBuffer requestURL = req.getRequestURL();
        if (requestURL != null && requestURL.toString().endsWith("login_script.js")) {
            chain.doFilter(req, res);
        }
        HttpSession session = req.getSession();
        Object userSession = session.getAttribute("user_session");
        if (userSession == null) {
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/login");
            requestDispatcher.forward(req, res);
        }else {
            chain.doFilter(req, res);
        }
    }
}
