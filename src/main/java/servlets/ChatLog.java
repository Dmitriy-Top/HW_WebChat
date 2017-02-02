package main.java.servlets;

import main.java.templater.PageGenerator;
import main.java.utils.msgDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 02.02.2017.
 */
public class ChatLog extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        genChatLog(resp);
    }
    private void genChatLog(HttpServletResponse resp) throws IOException {
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("messages", msgDAO.getMsgLi());
        resp.getWriter().println(new String(PageGenerator.instance().getPage("chatlog.html", pageVariables)));
        resp.setStatus(HttpServletResponse.SC_ACCEPTED);
        resp.getWriter().close();
    }
}
