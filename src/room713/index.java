package room713;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Li Shunan on 2015/6/20.
 *
 */
@WebServlet(name = "index")
public class index extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String s=request.getParameter("keyword");

        //Set<Integer> set = IR.searchEntrance(s);
//        ArrayList<Map.Entry<Integer,Double>> list = IR.searchEntrance(s);
//        String corrected = IR.spellCorrect(s);
//        PrintWriter writer=response.getWriter();
//        for(Map.Entry<Integer,Double> entry: list)
//        {
//            writer.println("<a href='view.jsp?id=" + entry.getKey().intValue() + "'>" + entry.getKey().intValue() + "</a>");
//
//        }

//        ir.buildIndex();
    }
}
