package com.dmitri.controller;

import com.dmitri.controller.base.AbstractHttpController;
import com.dmitri.exeption.ApplicationException;
import com.dmitri.model.User;
import lombok.extern.java.Log;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static com.dmitri.constant.WebConstant.DEFAULT_COUNT_OF_USERS_ON_PAGE;

@Log
@WebServlet(urlPatterns = {"/users", "/users/*"})
public class UserRestController extends AbstractHttpController {

    /**
     * Get all roles from data base
     * Http response statuses:
     * 200 - successful
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        String pathInfo = req.getPathInfo();
        if (pathInfo != null && pathInfo.equals("/count")) {
            try {
                int total = userService.getCountOfUser();
                String json = buildJson("total", total, "limit", DEFAULT_COUNT_OF_USERS_ON_PAGE);
                writeJsonResponseBody(200, json, resp);
            } catch (ApplicationException ex) {
                buildErrorResponse(404, ex.getMessage(), resp);
            }
        }
        try {
            String pageNumber = req.getParameter("page_number");
            int offset = defineOffset(pageNumber);

            List<User> users = userService.getUsers(offset);
            String json = toJson(users);
            writeJsonResponseBody(200, json, resp);
        } catch (ApplicationException ex) {
            buildErrorResponse(404, ex.getMessage(), resp);
        }
    }

    int defineOffset(String pageNumber) {
        if (pageNumber == null || pageNumber.equals("1") || pageNumber.equals("0")) {
            return 1;
        }
        try {
            return Integer.parseInt(pageNumber);
        } catch (NumberFormatException e) {
            return 1;
        }
    }


    /**
     * Create new usr
     * Http request:
     * - type: json
     * - body: User user
     * Http response statuses:
     * 201 - successful
     * 400 - error with 'errorMessage' key
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            // 1. Get body from Http request
            String json = getBody(req);
            // 2. parse body to obtain user class
            User user = jsonToClass(json, User.class);
            // 3. create user
            User userResponse = userService.createUser(user);
            String jsonResponse = toJson(userResponse);
            // 4. Create successful HTTP response with JSON body
            writeJsonResponseBody(201, jsonResponse, resp);
        } catch (ApplicationException e) {
            // if we have application error than we sent error status code with message
            buildErrorResponse(400, e.getMessage(), resp);
        }
    }


    /**
     * Update role name by id
     * Http response statuses:
     * 201 - successful
     * 400 - error with 'errorMessage' key
     */
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        try {
            // 1. Get body from Http request
            String json = getBody(req);
            // 2. parse body to obtain user class
            User user = jsonToClass(json, User.class);
            // 3. update user
            userService.updateUser(user);
            // 4. Create successful HTTP response with JSON body
            writeJsonResponseBody(201, json, resp);
        } catch (ApplicationException e) {
            buildErrorResponse(400, e.getMessage(), resp);
        }
    }


    /**
     * Delete role by id
     * Http response statuses:
     * 201 - successful
     * 400 - error with 'errorMessage' key
     */
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        try {
            // 1. Get user id from path
            int userId = defineUrlPathParameter(req);
            // 2. Delete user from data base by user id
            userService.deleteUser(userId);
            // 3.Create json object with user id
            String json = buildJson("id", userId);
            // 4. Create successful HTTP response with JSON body
            writeJsonResponseBody(201, json, resp);
        } catch (ApplicationException ex) {
            // if we have application error than we sent error status code with message
            buildErrorResponse(400, ex.getMessage(), resp);
        }
    }
}
