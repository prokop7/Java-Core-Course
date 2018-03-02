package controllers;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ControllerHelper {
    static void forward(
            HttpServlet servlet,
            HttpServletRequest req,
            HttpServletResponse resp,
            String address) throws ServletException, IOException {
        servlet.getServletContext()
                .getRequestDispatcher(address)
                .forward(req, resp);
    }
}
