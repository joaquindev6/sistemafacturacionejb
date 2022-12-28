package com.jfarro.app.filters;

import com.jfarro.app.entities.Role;
import com.jfarro.app.entities.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter({"/inventario/*", "/inicio"})
public class LoginFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        User user = (User) req.getSession().getAttribute("userLogin");
        if (user != null) {
            for (Role r: user.getRoles()) {
                if ("ROLE_ADMIN".equals(r.getName()) || "ROLE_USER".equals(r.getName())) {
                    chain.doFilter(request, response);
                } else {
                    resp.sendRedirect(req.getServletContext() + "/inicio");
                }
            }
        } else {
            resp.sendRedirect(req.getServletContext() + "/login");
        }
    }
}
