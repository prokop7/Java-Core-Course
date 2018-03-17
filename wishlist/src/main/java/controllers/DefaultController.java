package controllers;

import services.ContentService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "default", urlPatterns = {"/"})
public class DefaultController extends HttpServlet {
    private ContentService contentService;

    @Override
    public void init() {
        contentService = new ContentService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("randomNumber", contentService.getContent());
        ControllerHelper.forward(this, req, resp, "/default.jsp");
    }
}
