package controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import services.AuthService;
import services.exceptions.EmptyFieldException;
import services.exceptions.NullFieldException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "LoginController", urlPatterns = {"/login"})
public class LoginController extends HttpServlet {
    private AuthService authService;
    private Logger logger = LogManager.getLogger(RegisterController.class);

    @Override
    public void init() {
        this.authService = ControllerHelper.initAuthService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String token;
        try {
            token = authService.authenticate(login, password);
        } catch (NullFieldException e) {
            sendMessage("Login or password is null", req, resp);
            return;
        } catch (EmptyFieldException e) {
            sendMessage("Login or password is empty", req, resp);
            return;
        }

        if (token != null) {
            HttpSession session = req.getSession();
            session.setAttribute("token", token);

            req.setAttribute("login", login);
            req.setAttribute("password", password);
            resp.sendRedirect("/");
            return;
        }
        req.setAttribute("message", "Wrong combination of login and password");
        ControllerHelper.forward(this, req, resp, "/authentication.jsp");
    }

    private void sendMessage(
            String message,
            HttpServletRequest req,
            HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("message", message);
        ControllerHelper.forward(this, req, resp, "/authentication.jsp");
    }
}
