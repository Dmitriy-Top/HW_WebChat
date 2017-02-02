package main.java.servlets;

import main.java.templater.PageGenerator;
import main.java.utils.LoginDAO;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * Created by admin on 02.02.2017.
 */
public class Login extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String pass = req.getParameter("pass");
        if (login != null & pass != null) {
            boolean isAuth = LoginDAO.auth(login, pass);
            if (isAuth) {
                LoginDAO.addAuthSession(req,pass);
                resp.sendRedirect("./");
            } else genLoginPage(resp);
        } else resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession();
        String JSESSIONID = "";
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("JSESSIONID".equals(cookie.getName())) {
                    JSESSIONID = cookie.getValue();
                }
            }
        }
        if (LoginDAO.isAuthenticated(JSESSIONID)) {
            resp.sendRedirect("./");
        } else {
            genLoginPage(resp);
        }
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    private void genLoginPage(HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html;charset=utf-8");
        resp.getWriter().println(new String(PageGenerator.instance().getPage("login.html", null)));
        resp.setStatus(HttpServletResponse.SC_ACCEPTED);
        resp.getWriter().close();

    }
}
