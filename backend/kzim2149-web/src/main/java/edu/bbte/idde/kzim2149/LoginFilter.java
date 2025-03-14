package edu.bbte.idde.kzim2149;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter(urlPatterns = {"/index", "/", "", "/index.jsp"})
public class LoginFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        String user = (String) req.getSession().getAttribute("user");
        if (user != null) {
            chain.doFilter(req, res);
            return;
        }
        res.sendRedirect("login.jsp");
    }
}
