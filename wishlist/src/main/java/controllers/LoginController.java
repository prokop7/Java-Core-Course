package controllers;

import dao.DatabaseProvider;
import dao.PostgreProvider;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = LoginController.LOGIN, urlPatterns = {"/login"})
public class LoginController extends HttpServlet {
    static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private DatabaseProvider dao;

    @Override
    public void init() {
        this.dao = new PostgreProvider();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String login = req.getParameter(LOGIN);
        String password = req.getParameter(PASSWORD);
        String token = dao.authenticate(login, password);
        if (token != null) {
            HttpSession session = req.getSession();
            session.setAttribute("token", token);

            req.setAttribute(LOGIN, login);
            req.setAttribute(PASSWORD, password);
            resp.sendRedirect("/");
        }
        req.setAttribute("message", "Wrong combination of login and password");
        getServletContext()
                .getRequestDispatcher("/authentication.jsp")
                .forward(req, resp);
    }
}
