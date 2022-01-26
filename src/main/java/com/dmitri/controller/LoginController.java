package com.dmitri.controller;

import com.dmitri.controller.base.AbstractHttpController;
import com.dmitri.exeption.ApplicationException;
import com.dmitri.model.User;
import com.dmitri.model.UserCredential;
import com.sun.org.apache.xpath.internal.operations.Bool;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import static com.dmitri.constant.WebConstant.*;


@WebServlet("/login")
public class LoginController extends AbstractHttpController {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String password = req.getParameter("password");
            String username = req.getParameter("username");
            if (password == null || username == null || username.isEmpty() || password.isEmpty()){
                req.setAttribute("error","username or password are empty");
                req.getRequestDispatcher("/login.jsp").forward(req, resp);
                return;
            }
            UserCredential userCredential = userService.findUserByCredentials(username,password);
            if (userCredential != null){
                HttpSession session = req.getSession();
                session.setAttribute(SESSION_USER_NAME, userCredential.getUserName());
                session.setAttribute(SESSION_USER_ROLE, userCredential.getRoleName());
                session.setAttribute(SESSION_USER_ID, userCredential.getId());
                resp.sendRedirect(req.getContextPath());
            }
            else {
                req.setAttribute("error","user not found by username: " + username);
                req.getRequestDispatcher("/login.jsp").forward(req, resp);
            }
        } catch (ApplicationException e) {
            req.setAttribute("error",e.getMessage());
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }
}
