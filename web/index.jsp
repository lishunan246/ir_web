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
              <input type="text" value="TextSearch" class="hidden" id="type" name="type">
          </div>

          <div class="btn-group">
              <button type="submit" id="search" class="btn btn-primary">搜索</button>
              <button type="button" id="toggle" class="btn btn-primary dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                  <span class="caret"></span>
                  <span class="sr-only">Toggle Dropdown</span>
              </button>
              <ul class="dropdown-menu">
                  <li><a href="#" class="hidden" onclick="setType('TextSearch')" id="TextSearch">常规搜索</a></li>
                  <li><a href="#" onclick="setType('BoolSearch')" id="BoolSearch">布尔搜索</a></li>
                  <li><a href="#" onclick="setType('PhraseSearch')"id="PhraseSearch">短语搜索</a></li>
                  <li role="separator" class="divider"></li>
                  <li><a href="${pageContext.request.contextPath}/buildindex">建立索引</a></li>
              </ul>
          </div>
      </form>
  </div>

  <script>

      $(document).ready(function(){
          setType("TextSearch");
      });

      function setType(t)
      {
          $("#BoolSearch").removeClass("hidden");
          $("#TextSearch").removeClass("hidden");
          $("#PhraseSearch").removeClass("hidden");
          if(t=="BoolSearch")
          {
              $("#BoolSearch").addClass("hidden");
              $("#type").val(t);
              $("#search").html("布尔搜索");
          }
          if(t=="PhraseSearch") {
              $("#PhraseSearch").addClass("hidden");
              $("#type").val(t);
              $("#search").html("短语搜索");
          }
          if(t=="TextSearch")
          {
              $("#TextSearch").addClass("hidden");
              $("#type").val(t);
              $("#search").html("搜索");
          }
      }

  </script>
  <style scoped>
      #form-search {
          padding-top: 10%;
          width: 35em;
          height: 20em;
          margin: auto;
      }

      #toggle{
          height: 34px;
      }

      .form-group {
          width: auto;
      }
  </style>

  </body>
</html>
