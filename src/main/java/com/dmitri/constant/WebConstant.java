package com.dmitri.constant;

public class WebConstant {
    /**
     *
     */
    public final static int DEFAULT_COUNT_OF_USERS_ON_PAGE = 5;

    /**
     * date format for json
     */
    public static final String DATE_FORMAT_JSON = "yyyy-MM-dd";

    /**
     *
     */
    public final static String SHOW_TABLE_SQL_QUERY = "SHOW TABLES";

    /**
     * Objects that linked to http session
     */
    public static final String SESSION_USER_NAME = "sessionUserName";
    public static final String SESSION_USER_ROLE = "sessionUserRole";
    public static final String SESSION_USER_ID = "sessionUserId";

    /**
     * SQL queries for user table
     */
    public static String SELECT_USERS_SQL_QUERY = "SELECT u.id, first_name, second_name, phone, birthday, username," +
            " password, r.name AS role_name FROM users AS u LEFT JOIN role AS r ON role_id=r.id LIMIT 5 OFFSET ";
    public static final String INSERT_NEW_USER_SQL_QUERY = "INSERT INTO users (second_name,birthday,first_name,phone," +
            "room_number,password,username,role_id) VALUES (?,?,?,?,?,?,?,(SELECT id FROM role WHERE name = ?))";
    public static final String DELETE_USER_BY_ID_SQL_QUERY = "DELETE FROM users WHERE id = ?";
    public static final String SELECT_LAST_USER_ID_SQL_QUERY = "SELECT MAX(id) AS id FROM users";
    public static final String SELECT_COUNT_OF_USERS_SQL_QUERY = "SELECT COUNT(id) AS count_id FROM users";
    public static final String UPDATE_USER_TEMPLATE_SQL_QUERY = "UPDATE users SET %s WHERE id = %s";
    public static final String SELECT_USER_BY_CREDENTIAL_SQL_QUERY = "SELECT u.id AS id, username, r.name AS name FROM users AS u" +
            " LEFT JOIN role AS r ON r.id = u.role_id WHERE username = ? AND password = ?";


    /**
     * SQL queries for role table
     */
    public static final String SELECT_ALL_ROLES_SQL_QUERY = "SELECT id, name FROM role";
    public static final String INSERT_NEW_ROLE_SQL_QUERY = "INSERT INTO role SET name = ?";
    public static final String DELETE_ROLE_BY_ID_SQL_QUERY = "DELETE FROM role WHERE id = ?";
    public static final String SELECT_LAST_ROLE_ID_SQL_QUERY = "SELECT MAX(id) AS id FROM role";
    public static final String UPDATE_ROLE_NAME_BY_ROLE_ID_SQL_QUERY = "UPDATE role SET name = ? WHERE id = ?";
}
