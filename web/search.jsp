<%@ page import="room713.IR" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Map" %>
<%--
  Created by IntelliJ IDEA.
  User: Li Shunan
  Date: 2015/6/20
  Time: 23:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>搜索结果</title>

    <!-- 新 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.4/css/bootstrap.min.css">

    <!-- 可选的Bootstrap主题文件（一般不用引入） -->
    <link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.4/css/bootstrap-theme.min.css">

    <!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
    <script src="http://cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>

    <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
    <script src="http://cdn.bootcss.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container">
    <h1>
        <a href="index.jsp">

                <%
                    String keyword = request.getParameter("keyword");
                    String t=request.getParameter("p");
                    ArrayList<Map.Entry<Integer, Double>> list = IR.searchEntrance(keyword);
                    int count=list.size();
                    int p_count=(count%10==0)?count/10:count/10+1;
                    int p=1;
                    if(t!=null)
                    {
                        p=Integer.valueOf(t);
                    }

                    out.print(keyword);

                %>
        </a>
    </h1>

    <div class="panel panel-default">
        <div class="panel-body">
            检索到<% out.print(list.size());%>条结果, 当前显示第<% out.print(10*(p-1)+1);%>~<%out.print((list.size()<10*p+1)?list.size():10*p);%>条结果。
        </div>
    </div>

    <table class="table table-hover table-bordered">
        <%
            //for (Map.Entry<Integer, Double> entry : list) {
            for(int i=10*(p-1);i<10*p&&i<list.size();i++)
            {
                Map.Entry<Integer, Double> entry=list.get(i);
        %>
        <tr>
            <td>
                <a href="view.jsp?keyword=<% out.print(keyword);%>&id=<% out.print(entry.getKey().intValue());%>">
                    <% out.println(room713.ViewFile.getTitle(Integer.toString(entry.getKey().intValue())));%>
                </a>
                <% out.print(entry.getValue().doubleValue());%>
            </td>
        </tr>
        <%
            }
        %>
    </table>

    </div>

    <div class="container">
        <nav>
            <ul class="pager">
                <% if((p-1)>0)
                {

                %>
                <li><a href="search.jsp?keyword=<% out.print(keyword); %>&p=<% out.print(p-1);%>">上一页</a></li>
                <%
                    }

                    if((p+1)<=p_count)
                    {
                %>
                <li><a href="search.jsp?keyword=<% out.print(keyword); %>&p=<% out.print(p+1);%>">下一页</a></li>
                <%
                    }
                %>
            </ul>
        </nav>
    </div>



<script type="text/javascript" src="js/hilitor.js"></script>
<script type="text/javascript">

    var myHilitor = new Hilitor("content");
    myHilitor.setMatchType("open");
    myHilitor.apply("<%
        out.print(keyword);
    %>");


</script>

</body>
</html>
