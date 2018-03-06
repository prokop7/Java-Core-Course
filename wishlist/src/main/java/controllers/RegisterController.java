package controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import services.AuthService;
import services.exceptions.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "RegisterController", urlPatterns = {"/register"})
public class RegisterController extends HttpServlet {
    private AuthService authService;
    private Logger logger = LogManager.getLogger(RegisterController.class);

    @Override
    public void init() {
        this.authService = ControllerHelper.initAuthService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        try {
            authService.register(login, password);
            sendMessage("Successfully registered", req, resp);
        } catch (DuplicatedLoginException e) {
            logger.warn("Login is already taken: " + login);
            sendMessage("Login is already taken", req, resp);
        } catch (InternalDbException e) {
            logger.error(String.format("Invalid login or password: %s %s", login, password));
            sendMessage(String.format("Invalid login or password: %s %s", login, password), req, resp);
        } catch (EmptyFieldException e) {
            logger.info("Login or password is empty");
            sendMessage("Login or password is empty", req, resp);
        } catch (NullFieldException e) {
            logger.info("Login or password is null");
            sendMessage("Login or password is null", req, resp);
        } catch (InvalidFieldException e) {
            logger.info(String.format("Invalid login or password: %s %s", login, password));
            sendMessage("Login or password contains invalid character or less than 3 symbols", req, resp);
        }
    }

    private void sendMessage(
            String message,
            HttpServletRequest req,
            HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("message", message);
        ControllerHelper.forward(this, req, resp, "/authentication.jsp");
    }
}
