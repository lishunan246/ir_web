<%@ page import="room713.IR" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Map" %>
<%@ page import="room713.Tokenizer" %>
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
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/bootstrap-theme.min.css">
    <script src="js/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
</head>
<body>
<div class="container">
    <h1>
        <a href="index.jsp">

                <%
                    String keyword = request.getParameter("keyword");
                    String t=request.getParameter("p");
                    //Set<Integer> set = IR.searchEntrance(keyword);
                    String type = request.getParameter("type");
                    ArrayList<Map.Entry<Integer,Double>> list = IR.searchEntrance(keyword, type);
                    ArrayList<String> queryList = IR.tokenizeWithStopwordNoStem(keyword);
                    ArrayList<String> corrected = IR.spellCorrect(keyword);
                    //for (int k : set) {
//            for (int k : list) {
                    String correctString = "";
                    int k = 0;
                    Boolean wrong = false;
                    for(String query: queryList){
                        if(Tokenizer.stopwords.contains(query)){
                            correctString += " " + query;
                        }else{
                            if(!query.equalsIgnoreCase(corrected.get(k))){
                                wrong = true;
                            }
                            correctString += " " + corrected.get(k++);
                        }
                    }

                    //ArrayList<Map.Entry<Integer, Double>> list = IR.searchEntrance(keyword);
                    int count= list != null ? list.size() : 0;
                    int p_count = (count%10==0)?count/10:count/10+1;
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
            <% if(wrong){%>
            <p><a href="search.jsp?keyword=<% out.print(correctString.substring(1));%>&type=<% out.print(type); %>">
                你是否要搜索
                <%

                        out.print(correctString.substring(1));


                %>？</a>
            </p>
            <%}
                assert list != null;
                if(list.size()!=0)
                {
            %>

            检索到<% out.print(list.size());%>条结果, 当前显示第<% out.print(10*(p-1)+1);%>~<%out.print((list.size()<10*p+1)?list.size():10*p);%>条结果。
            <%
                }
                else
                {
            %>
            未找到任何结果。
            <%}%>
        </div>
    </div>

    <table id="result_table" class="table table-hover table-bordered">
        <%

            for(int i=10*(p-1);i<10*p&&i<list.size();i++)
            {
                Map.Entry<Integer, Double> entry=list.get(i);

        %>
        <tr>
            <td>
                <a href="view.jsp?keyword=<% out.print(keyword);%>&id=<% out.print(entry.getKey().intValue());%>">
                    <% out.println(room713.ViewFile.getTitle(Integer.toString(entry.getKey().intValue())));%>
                </a>
                <%--<p>--%>
                    <%--<% out.print(entry.getValue().doubleValue());%>--%>
                <%--</p>--%>

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
                <li><a href="search.jsp?keyword=<% out.print(keyword); %>&p=<% out.print(p-1);%>&type=<% out.print(type);%>">上一页</a></li>
                <%
                    }

                    if((p+1)<=p_count)
                    {
                %>
                <li><a href="search.jsp?keyword=<% out.print(keyword); %>&p=<% out.print(p+1);%>&type=<% out.print(type);%>">下一页</a></li>
                <%
                    }
                %>
            </ul>
        </nav>
    </div>



<script type="text/javascript" src="js/hilitor.js"></script>
<script type="text/javascript">

    var myHilitor = new Hilitor("result_table");
    myHilitor.setMatchType("open");
    myHilitor.apply("<%
        ArrayList<String> noStopwordList = Tokenizer.tokenize(keyword, false);
        String outputKeyword = "";
        for(String word: noStopwordList){
            outputKeyword += " " + word;
        }
        if(outputKeyword.length() != 0){
            out.print(outputKeyword.substring(1));
        }
    %>");


</script>

</body>
</html>
