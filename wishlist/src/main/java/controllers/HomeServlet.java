package controllers;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Random;

@WebServlet(name = "default", urlPatterns = {"/"})
public class HomeServlet extends HttpServlet {
    private Random random;

    @Override
    public void init() throws ServletException {
        random = new Random();
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String s = req.getAuthType();
        System.out.println(s);
        s = req.getRemoteUser();
        System.out.println(s);

        getServletContext()
                .getRequestDispatcher("/default.jsp")
                .forward(req, resp);
    }
}
