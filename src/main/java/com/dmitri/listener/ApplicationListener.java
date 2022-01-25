package com.dmitri.listener;

import com.dmitri.exeption.ApplicationException;
import com.dmitri.utils.MyDataBaseConnection;
import lombok.SneakyThrows;
import lombok.extern.java.Log;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.dmitri.constant.WebConstant.SHOW_TABLE_SQL_QUERY;

@Log
@WebListener
public class ApplicationListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.info("start application");
        try {
            Connection connection = MyDataBaseConnection.getInstance();
            PreparedStatement preparedStatement = connection.prepareStatement(SHOW_TABLE_SQL_QUERY);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()){
                throw new ApplicationException("No tables in data base");
            }
        } catch (Exception e) {
            log.info("No connection to database , err massage:" + e.getMessage());
            shutDown();
        }
    }

    /**
     * link to information:https://stackoverflow.com/questions/8799991/shutdown-tomcat-using-web-application-deployed-in-it
     * Open TCP connection from some servlet and send "SHUTDOWN" to Tomcat's shutdown port (default: 8005).
     * One Tomcat uses one JVM for all applications.
     * No. Only for the entire JVM.
     */
    @SneakyThrows
    private void shutDown(){
        Socket clientSocket = new Socket("localhost", 8005);
        clientSocket.getOutputStream().write("SHUTDOWN".getBytes());
        clientSocket.getOutputStream().close();
        clientSocket.close();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        log.info("end application");
        MyDataBaseConnection.closeConnection();
    }
}
