<%@page import="in.co.rays.util.ServletUtility"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome</title>
</head>
<body>
	<form action="<%=ORSView.WELCOME_CTL%>">
		<%@ include file="Header.jsp"%>
		<h1 align="Center">
			<font size="10px" color="red">Welcome to ORS </font>
		</h1>
		<h1><%=ServletUtility.getSuccessMessage(request)%></h1>

		<%
			UserBean bean = (UserBean) session.getAttribute("user");
			if (bean != null) {
				if (bean.getRoleId() == 2) {
		%>

		<h2 align="Center">
			<a href="<%=ORSView.GET_MARKSHEET_CTL%>">Click here to see your
				Marksheet </a>
		</h2>

		<%
			}
			}
		%>

	</form>

	<%@include file="Footer.jsp"%>
</body>
</html>
