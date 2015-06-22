<%--
  Created by IntelliJ IDEA.
  User: Li Shunan
  Date: 2015/6/20
  Time: 17:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% room713.IR.initialize();%>
<html>
<head>
    <title>搜索</title>
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
  <div class="container" id="form-search">
      <form class="form-inline form-horizontal" action="search.jsp" method="get">
          <div class="form-group">
              <input type="text" size="45" class="form-control" name="keyword" placeholder="请输入关键字">
          </div>

          <button class="btn btn-primary" type="submit">搜索</button>
      </form>
      <a href="${pageContext.request.contextPath}/buildindex">建立索引</a>
  </div>
  <style scoped>
      #form-search {
          padding-top: 10%;
          width: 35em;
          height: 20em;
          margin: auto;
      }

      .form-group {
          width: auto;
      }
  </style>

  </body>
</html>
