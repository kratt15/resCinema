package tg.groupedeux.rescinema.web;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = {"/app/*", "/admin/*"})
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);

        String path = req.getRequestURI();
        Long userId = (session != null) ? (Long) session.getAttribute("userId") : null;
        String role = (session != null) ? (String) session.getAttribute("role") : null;

        boolean isAdminPath = path.contains("/admin/");
        if (userId == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        if (isAdminPath && (role == null || !"ADMIN".equals(role))) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        chain.doFilter(request, response);
    }
}
