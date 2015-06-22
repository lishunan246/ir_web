<%@ page import="room713.IR" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Map" %>
<%@ page import="room713.Tokenizer" %>
<%@ page import="javax.swing.text.StyledEditorKit" %>
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
        <%
            String keyword = request.getParameter("keyword");

            out.print(keyword);
        %>
    </h1>


    <p>
    <ol>
        <%
            //Set<Integer> set = IR.searchEntrance(keyword);
            ArrayList<Map.Entry<Integer,Double>> list = IR.searchEntrance(keyword);
            ArrayList<String> queryList = IR.tokenizeWithStopwordNoStem(keyword);
            ArrayList<String> corrected = IR.spellCorrect(keyword);
            //for (int i : set) {
//            for (int i : list) {
            String correctString = "";
            int i = 0;
            Boolean wrong = false;
            for(String query: queryList){
                if(Tokenizer.stopwords.contains(query)){
                    correctString += " " + query;
                }else{
                    if(!query.equals(corrected.get(i))){
                        wrong = true;
                    }
                    correctString += " " + corrected.get(i++);
                }
            }
            if(wrong){
                out.println(correctString.substring(1));
            }
            for(Map.Entry<Integer,Double> entry:list){
        %>
        <li>
            <a href="view.jsp?keyword=<% out.print(keyword);%>&id=<% out.print(entry.getKey().intValue());%>"><%
                out.println(room713.ViewFile.getTitle(Integer.toString(entry.getKey().intValue())));%></a></li>
            <% out.print(entry.getValue().doubleValue());%>
        <%
            }
        %>
    </ol>
    </p>
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
