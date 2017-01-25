package main.java.servlets;

import main.java.templater.PageGenerator;
import main.java.utils.msgDAO;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 24.01.2017.
 */
public class WebChat extends HttpServlet{
    private static String FIRST_PART_OF_DOC = "<html> <head> <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"> <meta charset=\"utf-8\"> <link href=\"https://fonts.googleapis.com/css?family=Roboto\" rel=\"stylesheet\"> <meta http-equiv=\"refresh\" content=\"10; URL=./\"> <title>HW_WebCalc</title> </head> <body> <div id=\"container\"> <div id=\"msg_history_div\"> <ul>";
    private static String TWICE_PART_OF_DOC = "</ul> </div> <form action=\"./\" method=\"post\"> <label for=\"nick\">Ник пользователя</label> <input id=\"nick\" required value=\"";
    private static String THIRD_PART_OF_DOC = "\" type=\"text\" name=\"nick\" maxlength=\"50\"> <label for=\"msg\">Сообщение</label> <textarea id=\"msg\" required rows=\"5\" type=\"textarea\" name=\"msg\" maxlength=\"200\"></textarea> <input type=\"submit\" value=\"Отправить\"> </form> </div> <style> #container{ font-family: 'Roboto', sans-serif; font-size:16px; padding: 8px; border: 1px solid #dfdfdf; color: #3f3f3f; width: 360px; margin: 150px auto; } #msg_history_div{ height: 160px; overflow: auto; border: 1px solid #dfdfdf; } #msg_history_div ul{ list-style: none; margin: 8px; padding: 0; } #container form{ margin: 8px 0 8px 0; } input, textarea { width: 100%; } label { padding-bottom:4px; padding-top:4px; display: block; } input[type=\"submit\"]{ margin-top:8px; } </style> </body> </html>";

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String nickName="";
        Cookie[] cookies = req.getCookies();
        if (cookies!=null){
            for (Cookie cookie:cookies){
                if ("nickname".equals(cookie.getName())){
                    nickName=cookie.getValue();
                    System.out.println(nickName+" is cookie");
                }
            }
        }
        genPage(resp, nickName);
        System.out.println(nickName+" get gen");
        resp.setStatus(HttpServletResponse.SC_OK);

    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String msg = req.getParameter("msg");
        String nickname = req.getParameter("nick");
        Date msgDate = new Date();
        if (msg!=null&nickname!=null) {
            genPage(resp, nickname);
            System.out.println(nickname+" gen post");
            msgDAO.sendMsg(msg,nickname,msgDate);
            resp.setStatus(HttpServletResponse.SC_ACCEPTED);
        } else resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);

    }

    private void genPage(HttpServletResponse resp, String nickName) throws IOException {
        /*Map<String,Object> pageVariables = new HashMap<>();
        pageVariables.put("messages", msgDAO.getMsgLi());
        pageVariables.put("nick",nickName);
         resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=utf-8");
        resp.addCookie(new Cookie("nickname",nickName));
        resp.getWriter().println(new String(PageGenerator.instance().getPage("index.html", pageVariables)));*/
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=utf-8");
        resp.addCookie(new Cookie("nickname", nickName));
        System.out.println(nickName + " set to cookie");
        resp.getWriter().print(FIRST_PART_OF_DOC + msgDAO.getMsgLi() + TWICE_PART_OF_DOC + nickName + THIRD_PART_OF_DOC);
        resp.getWriter().close();
    }

}
