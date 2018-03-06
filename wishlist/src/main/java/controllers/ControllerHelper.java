package controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import services.AuthService;
import services.AuthorizationService;
import services.exceptions.DbConnectionException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ControllerHelper {
    static Logger logger = LogManager.getLogger();

    static void forward(
            HttpServlet servlet,
            HttpServletRequest req,
            HttpServletResponse resp,
            String address) throws ServletException, IOException {
        servlet.getServletContext()
                .getRequestDispatcher(address)
                .forward(req, resp);
    }

    public static AuthService initAuthService() {
        try {
            return new AuthorizationService();
        } catch (DbConnectionException e) {
            logger.fatal(e);
            System.exit(0);
        }
        return null;
    }
}
