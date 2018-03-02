package controllers;

import services.AuthorizationService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "LogoutController", urlPatterns = {"/logout"})
public class LogoutController extends HttpServlet {
    private AuthorizationService authService;

    @Override
    public void init() throws ServletException {
        this.authService = new AuthorizationService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        authService.logout((String) req.getSession().getAttribute("token"));
        //TODO process error
        resp.sendRedirect("/authentication.jsp");
    }
}
