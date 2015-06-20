package room713;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Set;

/**
 * Created by Li Shunan on 2015/6/20.
 */
@WebServlet(name = "index")
public class index extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String s=request.getParameter("keyword");
        IR ir=new IR();
        Set<Integer> set=ir.entry(s);

        PrintWriter writer=response.getWriter();
        for(int i: set)
        {
            writer.println(i);

        }
    }
}
