package com.dmitri.controller;

import com.dmitri.controller.base.AbstractHttpController;
import com.dmitri.exeption.ApplicationException;
import com.dmitri.model.Role;
import lombok.extern.java.Log;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Log
@WebServlet(urlPatterns = {"/roles", "/roles/*"})
public class RoleRestController extends AbstractHttpController {

    /**
     * Get all roles from data base
     * Http response statuses:
     * 200 - successful
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            List<Role> roles = roleService.getAllRoles();
            String json = toJson(roles);
            writeJsonResponseBody(200, json, resp);
        } catch (ApplicationException ex) {
            buildErrorResponse(404, ex.getMessage(), resp);
        }
    }


    /**
     * Create new role
     * Http request:
     * - type: json
     * - body: role name
     * Http response statuses:
     * 201 - successful
     * 400 - error with 'errorMessage' key
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            // 1. Get body from Http request
            String requestBody = getBody(req);
            // 2. Parse request body and get role name
            String roleName = getRoleName(requestBody);
            // 3. Insert new role to DB
            Role role = roleService.createRole(roleName);
            // 4. Upcast role class to json format
            String json = toJson(role);
            // 4. Create successful HTTP response with JSON body
            writeJsonResponseBody(201, json, resp);
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
            // 2. parse body to obtain role class
            Role role = jsonToClass(json, Role.class);
            // 3. update role name by id
            roleService.updateRole(role);
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
            // 1. Get role id from path
            int roleId = defineUrlPathParameter(req);
            // 2. Delete role from data base by role id
            roleService.deleteRole(roleId);
            // 3.Create json object with role id
            String json = buildJson("id", roleId);
            // 4. Create successful HTTP response with JSON body
            writeJsonResponseBody(201, json, resp);
        } catch (ApplicationException ex) {
            // if we have application error than we sent error status code with message
            buildErrorResponse(400, ex.getMessage(), resp);
        }
    }

}