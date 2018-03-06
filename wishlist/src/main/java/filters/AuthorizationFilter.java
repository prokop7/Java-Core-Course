package filters;

import controllers.ControllerHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import services.AuthService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthorizationFilter implements Filter {
    private AuthService authService;
    private static Logger logger = LogManager.getLogger();
    private static final String redirectAddress = "/authentication.jsp";

    @Override
    public void init(FilterConfig filterConfig) {
        this.authService = ControllerHelper.initAuthService();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();
        if (session != null) {
            String token = (String) session.getAttribute("token");
            if (token != null)
            {
                String userLogin = authService.authorize(token);
                logger.debug(String.format("Login for token '%s':'%s'", userLogin, token));
                if (userLogin != null) {
                    chain.doFilter(request, response);
                    return;
                }
            }
        }
        logger.debug(String.format("Redirected to '%s'", redirectAddress));
        ((HttpServletResponse) response).sendRedirect(redirectAddress);
    }

    @Override
    public void destroy() {
    }
}
