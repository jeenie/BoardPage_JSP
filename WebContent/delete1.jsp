<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*, java.net.*, p201632003.*"%>
<%
	String s1 = request.getParameter("id");
	int id = Integer.parseInt(s1);
	String pg = request.getParameter("pg");
	String st = request.getParameter("st");
	String ss = request.getParameter("ss");
	if (ss == null) ss = "0";
	if (st == null) st = "";
	String stEncoded = URLEncoder.encode(st, "UTF-8");
	String od = request.getParameter("od");
	
	ArticleDAO.delete(id);
	response.sendRedirect("list1.jsp?pg=" + pg + "&ss=" + ss + "&st=" + stEncoded + "&od=" + od);
%>