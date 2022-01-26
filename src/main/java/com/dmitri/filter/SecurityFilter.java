package com.dmitri.filter;

import com.dmitri.constant.RoleEnum;

import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
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
        if (requestURL != null && requestURL.toString().endsWith("/logout")){
            chain.doFilter(req, res);
            return;
        }
        HttpSession session = req.getSession();
        Object userName = session.getAttribute(SESSION_USER_NAME);
        Object userRole = session.getAttribute(SESSION_USER_ROLE);
        Object userId = session.getAttribute(SESSION_USER_ID);
        if (userRole == null) {
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/login");
            requestDispatcher.forward(req, res);
            return;
        }
        req.setAttribute("userName",userName);
        req.setAttribute("userId",userId);
        req.setAttribute("userRole",userRole);
        if (RoleEnum.ADMIN.name().equals((String) userRole)) {
            chain.doFilter(req, res);
        } else {
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/client");
            requestDispatcher.forward(req, res);
        }
    }
}
