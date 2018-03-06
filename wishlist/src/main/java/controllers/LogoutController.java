package controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import services.AuthService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "LogoutController", urlPatterns = {"/logout"})
public class LogoutController extends HttpServlet {
    private AuthService authService;
    private Logger logger = LogManager.getLogger(RegisterController.class);

    @Override
    public void init() throws ServletException {
        this.authService = ControllerHelper.initAuthService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        authService.logout((String) req.getSession().getAttribute("token"));
        //TODO process error
        resp.sendRedirect("/authentication.jsp");
    }
}
