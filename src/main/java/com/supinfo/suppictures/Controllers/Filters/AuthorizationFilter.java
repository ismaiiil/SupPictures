package com.supinfo.suppictures.Controllers.Filters;

import com.supinfo.suppictures.Model.Database.ValueObjects.User;

import javax.faces.application.ResourceHandler;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.supinfo.suppictures.Controllers.Filters.AAUtil.redirectOnCheck;

@WebFilter("/admin/*")
public class AuthorizationFilter implements Filter {

    private static final String AJAX_REDIRECT_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
            + "<partial-response><redirect url=\"%s\"></redirect></partial-response>";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession(false);
        String loginURL = request.getContextPath() + "/public/index.xhtml";

        boolean isAdmin = (session != null) && (((User)session.getAttribute("user")).getAdministrator());
        redirectOnCheck(chain, request, response, loginURL, isAdmin, AJAX_REDIRECT_XML);
    }

    @Override
    public void destroy() {

    }

}
