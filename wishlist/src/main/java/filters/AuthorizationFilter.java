package filters;

import dao.PostgreProvider;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthorizationFilter implements Filter {
    private PostgreProvider dao;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.dao = new PostgreProvider();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();
        if (session != null) {
            String token = (String) session.getAttribute("token");
            if (dao.authorize(token)) {
                chain.doFilter(request, response);
                return;
            }
        }
        ((HttpServletResponse) response).sendError(403);
    }

    @Override
    public void destroy() {

    }
}
