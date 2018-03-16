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
    private static Logger logger = LogManager.getLogger();

    private ControllerHelper() {
    }

    /**
     * Forward request to the address
     * @param servlet   from which servlet it was forwarded
     * @param req       redirected request
     * @param resp      response which will be redirected
     * @param address   destination address to the JSP component
     */
    static void forward(
            HttpServlet servlet,
            HttpServletRequest req,
            HttpServletResponse resp,
            String address) throws ServletException, IOException {

        servlet.getServletContext()
                .getRequestDispatcher(address)
                .forward(req, resp);
    }

    /**
     * Initialize a new AuthService
     * @return a new instance of AuthService
     */
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
