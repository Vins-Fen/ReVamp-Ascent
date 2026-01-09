package filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.*;
import jakarta.servlet.Filter;



@WebFilter("/admin/*") // si applica a tutte le pagine sotto /admin/
public class AdminAuthFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        // Forza UTF-8
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession(false);

        boolean loggedIn = (session != null && session.getAttribute("admin") != null);

        if (!loggedIn) {
            ((HttpServletResponse) response).sendRedirect(req.getContextPath() + "/admin/loginAdmin.jsp?errore=auth");
        } else {
            chain.doFilter(request, response); // utente autenticato → avanti
        }
    }

    public void init(FilterConfig filterConfig) {}
    public void destroy() {}
}
