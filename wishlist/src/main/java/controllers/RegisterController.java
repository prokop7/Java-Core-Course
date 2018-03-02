package controllers;

import services.AuthorizationService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "RegisterController", urlPatterns = {"/register"})
public class RegisterController extends HttpServlet {
    private AuthorizationService authService;

    @Override
    public void init() throws ServletException {
        authService = new AuthorizationService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        if (authService.register(login, password)) {
            ControllerHelper.forward(this, req, resp, "/authentication.jsp");
            return;
        }
        req.setAttribute("message", "Wrong combination of login and password");
        ControllerHelper.forward(this, req, resp, "/authentication.jsp");
    }
}
