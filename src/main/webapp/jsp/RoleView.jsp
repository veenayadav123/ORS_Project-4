<%@page import="in.co.rays.ctl.RoleCtl"%>
<%@page import="in.co.rays.util.DataUtility"%>
<%@page import="in.co.rays.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<body>
	<form action="<%=ORSView.ROLE_CTL%>" method="post">
		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean" class="in.co.rays.bean.RoleBean"
			scope="request"></jsp:useBean>

		<center>
			<h1>Add Role</h1>
			<H2>
				<font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
				</font>
			</H2>
			<H2>
				<font color="red"> <%=ServletUtility.getErrorMessage(request)%>
				</font>
			</H2>

			<input type="hidden" name="id" value="<%=bean.getId()%>"> <input
				type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
			<input type="hidden" name="modifiedBy"
				value="<%=bean.getModifiedBy()%>"> <input type="hidden"
				name="createdDatetime"
				value="<%=DataUtility.getTimestamp(bean.getCreatedDateTime())%>">
			<input type="hidden" name="modifiedDatetime"
				value="<%=DataUtility.getTimestamp(bean.getModifiedDateTime())%>">


			<table>
				<tr>
					<th align="left">Name<span style="color: red">*</span></th>
					<td><input type="text" name="name" size="23"
						placeholder="Enter Name"
						value="<%=DataUtility.getStringData(bean.getName())%>"></td>
					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("name", request)%></font></td>
				</tr>
				<tr>
					<th align="left">Description<span style="color: red">*</span></th>
					<td><input type="text" name="description" size="23"
						placeholder="Description"
						value="<%=DataUtility.getStringData(bean.getDescription())%>"></td>
					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("description", request)%></font></td>
				</tr>
				<tr>
					<th></th>
					<td colspan="2"><input type="submit" name="operation"
						value="<%=RoleCtl.OP_SAVE%>">&emsp; <input type="submit"
						name="operation" value="<%=RoleCtl.OP_CANCEL%>"></td>
				</tr>
			</table>
	</form>
	</center>
	<%@ include file="Footer.jsp"%>
</body>
</html>


