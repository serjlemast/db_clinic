package com.dmitri.listener;

import com.dmitri.utils.MyDataBaseConnection;
import lombok.SneakyThrows;
import lombok.extern.java.Log;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.net.Socket;
import java.sql.Connection;

@Log
@WebListener
public class ApplicationListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.info("start application");
        try {
            Connection connection = MyDataBaseConnection.getInstance();
            //todo implement logic for verification exist more than one table are in database
        } catch (Exception e) {
            log.warning("No connection to database , err massage:" + e.getMessage());
            System.out.println("No connection to database , err massage:" + e.getMessage());
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
