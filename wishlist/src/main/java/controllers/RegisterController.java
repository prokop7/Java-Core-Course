package controllers;

import dao.DatabaseProvider;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegisterController extends HttpServlet {
    static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private DatabaseProvider dao;

    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter(LOGIN);
        String password = req.getParameter(PASSWORD);
        if (dao.register(login, password)) {
            redirectLogin(req, resp);
            return;
        }
        req.setAttribute("message", "Wrong combination of login and password");
        getServletContext()
                .getRequestDispatcher("/authentication.jsp")
                .forward(req, resp);
    }

    private void redirectLogin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext()
                .getRequestDispatcher("/login")
                .forward(req, resp);
    }
}
