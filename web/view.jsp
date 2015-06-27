<%@ page import="room713.Tokenizer" %>
<%@ page import="java.util.ArrayList" %>
<%--
  Created by IntelliJ IDEA.
  User: Li Shunan
  Date: 2015/6/20
  Time: 22:20
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
    <title>结果</title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/bootstrap-theme.min.css">
    <script src="js/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
</head>
<body>
<div class="container">
    <%
        String id = request.getParameter("id");

    %>
    <h1>
        <a href="${pageContext.request.contextPath}/view?id=<% out.println(id);%>"><% out.println(room713.ViewFile.getTitle(id));%></a>

    </h1>

    <div class="panel panel-default" id="content">
        <div class="panel-body">
            <%
                out.println(room713.ViewFile.getContent(id));
            %>
        </div>
    </div>


        <button onclick="window.history.back();" class="btn btn-default">
            返回
        </button>
</div>
<script type="text/javascript" src="js/hilitor.js"></script>
<script type="text/javascript">

    var myHilitor = new Hilitor("content");
    myHilitor.setMatchType("open");
    myHilitor.apply("<%
        String keyword=request.getParameter("keyword");
        ArrayList<String> noStopwordList = Tokenizer.tokenize(keyword, false);
        String outputKeyword = "";
        for(String word: noStopwordList){
            outputKeyword += " " + word;
        }
        if(outputKeyword.length() != 0){
            out.print(outputKeyword.substring(1));
        }
//  out.print(keyword);
    %>");


</script>


</body>
</html>
