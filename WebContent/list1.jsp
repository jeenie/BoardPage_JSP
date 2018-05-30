<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*, java.net.*, java.text.SimpleDateFormat,p201632003.*"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my"%>
<%
	int currentPage = 1;
	int pageSize = 10;
	
	request.setCharacterEncoding("UTF-8");
	String pg = request.getParameter("pg");
	if (pg != null) currentPage = ParseUtils.parseInt(pg, 1);
	
	String ss = request.getParameter("ss");
	String st = request.getParameter("st");
	if (st == null) st = "";
	if (ss == null) ss = "0";
	String stEncoded = URLEncoder.encode(st, "UTF-8");
	
	int recordCount = ArticleDAO.count(ss, st);
	int lastPage = Math.max(1, (recordCount + pageSize - 1) / pageSize);
	if (currentPage > lastPage) currentPage = lastPage;
	
	String od = request.getParameter("od");
	if (od == null) od = "0";
	List<Article> list = ArticleDAO.findAll(currentPage, pageSize, ss, st, od);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<style>
body {
	font-family: 굴림체;
}

thead th {
	background-color: #eee;
}

tr:hover td {
	background-color: #ffe;
	cursor: pointer;
}

table.table {
	margin-top: 5px;
}

select {
	margin-right: 20px;
}
</style>
</head>
<body>
	<div class="container">
		<h1>게시글 목록</h1>
		<a id="createButton" class="btn btn-primary pull-right"
			href="create1.jsp?pg=<%=currentPage%>&st=<%=stEncoded%>&od=<%=od%>">
			<i class="glyphicon glyphicon-plus"></i> 게시글 등록
		</a>
		<form class="form-inline">
			<div class="form-group">
				<label>정렬</label> 
				<select name="od" class="form-control">
					<option value="0" <%="0".equals(od) ? "selected" : ""%>>등록순서</option>
					<option value="1" <%="1".equals(od) ? "selected" : ""%>>작성자 오름차순</option>
					<option value="2" <%="2".equals(od) ? "selected" : ""%>>작성자 내림차순</option>
					<option value="3" <%="3".equals(od) ? "selected" : ""%>>최신 글 순서</option>
					<option value="4" <%="4".equals(od) ? "selected" : ""%>>제목 오름차순</option>
				</select> 
				<select name="ss" class="form-control">
					<option value="0" <%="0".equals(ss) ? "selected" : ""%>>전체</option>
					<option value="1" <%="1".equals(ss) ? "selected" : ""%>>작성자</option>
					<option value="2" <%="2".equals(ss) ? "selected" : ""%>>제목</option>
				</select>
				<input type="text" class="form-control" name="st" value="<%=st%>" />
			</div>
			<button type="submit" class="btn btn-primary">조회</button>
		</form>
		<table class="table table-bordered table-condensed">
			<thead>
				<tr>
					<th>id</th>
					<th>no</th>
					<th>게시판</th>
					<th>작성자</th>
					<th>시각</th>
					<th>제목</th>
				</tr>
			</thead>
			<tbody>
				<%
					for (Article article : list) {
				%>
				<tr
					data-url="edit1.jsp?id=<%=article.getId()%>&pg=<%=currentPage%>&ss=<%=ss%>&st=<%=stEncoded%>&od=<%=od%>">
					<td><%=article.getId()%></td>
					<td><%=article.getNo()%></td>
					<td><%=article.getBoardName()%></td>
					<td><%=article.getUserName()%></td>
					<td><%=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(article.getWriteTime())%></td>
					<td><%=article.getTitle()%></td>
				</tr>
				<%
					}
				%>
			</tbody>
		</table>
		<my:pagination pageSize="<%=pageSize%>"
			recordCount="<%=recordCount%>" queryStringName="pg" />
	</div>
	<script> $("[data-url]").click(function() { var url = $(this).attr("data-url"); location.href = url; }) </script>
</body>
</html>