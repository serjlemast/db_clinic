package com.dmitri.controller.base;

import com.dmitri.exeption.ApplicationException;
import com.dmitri.service.RoleService;
import com.dmitri.service.UserService;
import com.dmitri.service.impl.RoleServiceImpl;
import com.dmitri.service.impl.UserServiceImpl;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import lombok.extern.java.Log;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@Log
public class AbstractHttpController extends HttpServlet {

    private final Gson gson;
    protected final RoleService roleService;
    protected final UserService userService;

    public AbstractHttpController() {
        gson = new Gson();
        roleService = new RoleServiceImpl();
        userService = new UserServiceImpl();
    }

    protected String toJson(Object ob) {
        String json = gson.toJson(ob);
        log.info("object to json: " + json);
        return json;
    }

    protected <T> T jsonToClass(String json, Class<T> clazz) {
        try {
            log.info("parse json to class, json " + json + ", class: " + clazz.getName());
            return gson.fromJson(json, clazz);
        } catch (JsonSyntaxException e) {
            throw new ApplicationException("Json parsing error, json - " + json, e);
        }
    }


    protected String getBody(HttpServletRequest req) {
        try (BufferedReader reader = req.getReader()) {
            return reader.readLine();
        } catch (IOException e) {
            throw new ApplicationException("Error parsing request body", e);
        }
    }


    protected String buildJson(String key, Object value) {
        String jsonValue = (value instanceof Number)
                ? String.valueOf(value)
                : value.toString();
        JsonObject jsonObj = new JsonObject();
        jsonObj.addProperty(key, jsonValue);
        return gson.toJson(jsonObj);
    }

    protected void writeJsonResponseBody(int status, String json, HttpServletResponse resp) {
        try (PrintWriter out = resp.getWriter()) {
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.setStatus(status);
            out.print(json);
            out.flush();
        } catch (IOException ex) {
            throw new ApplicationException("Error creating JSON HTTP response", ex);
        }
    }

    protected void buildErrorResponse(int status, String message, HttpServletResponse resp) {
        String errorJson = buildJson("errorMessage", message);
        writeJsonResponseBody(status, errorJson, resp);
    }

    protected int defineUrlPathParameter(HttpServletRequest req) {
        String roleId = req.getPathInfo();
        roleId = roleId.substring(1);
        return Integer.parseInt(roleId);
    }

    protected String getRoleName(String roleName) {
        roleName = roleName.replaceAll("[\"{}:,]", "");
        roleName = roleName.replaceAll("^.{4}", "");
        return roleName;
    }
}
