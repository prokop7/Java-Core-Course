package controllers;

import services.AuthorizationService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "LoginController", urlPatterns = {"/login"})
public class LoginController extends HttpServlet {
    private AuthorizationService authService;

    @Override
    public void init() {
        this.authService = new AuthorizationService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String token = authService.authenticate(login, password);

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
}
