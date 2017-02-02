package main.java.servlets;

import main.java.templater.PageGenerator;
import main.java.utils.LoginDAO;
import main.java.utils.msgDAO;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 24.01.2017.
 */
public class WebChat extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession();
        String nickName = "";
        String JSESSIONID = "";
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("nickname".equals(cookie.getName())) {
                    nickName = URLDecoder.decode(cookie.getValue(), "UTF-8");
                }
                if ("JSESSIONID".equals(cookie.getName())) {
                    JSESSIONID = cookie.getValue();
                }
            }
        }
        if (LoginDAO.isAuthenticated(JSESSIONID)) {
            genChatPage(resp, nickName);
        } else {
            System.out.println("chat forward to login");
            req.getRequestDispatcher("/login").forward(req,resp);
        }
        resp.setStatus(HttpServletResponse.SC_OK);

    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String msg = req.getParameter("msg");
        String nickname = req.getParameter("nick");
        Date msgDate = new Date();
        if (msg != null & nickname != null) {
            msgDAO.sendMsg(msg, nickname, msgDate);
            genChatPage(resp, nickname);
        } else resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);


    }

    private void genChatPage(HttpServletResponse resp, String nickName) throws IOException {
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("nick", nickName);
        resp.addCookie(new Cookie("nickname", URLEncoder.encode(nickName, "UTF-8")));
        resp.getWriter().println(new String(PageGenerator.instance().getPage("index.html", pageVariables)));
        resp.setStatus(HttpServletResponse.SC_ACCEPTED);
        resp.getWriter().close();
    }

}
