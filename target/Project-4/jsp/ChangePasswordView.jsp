<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@page import="in.co.rays.ctl.ChangePasswordCtl"%>
<%@page import="in.co.rays.util.DataUtility"%>
<%@page import="in.co.rays.util.ServletUtility"%>
<html>
<body>
	<form action="<%=ORSView.GET_CHANGE_PASSWORD_CTL%>" method="post">

		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean" class="in.co.rays.bean.UserBean" scope="request"></jsp:useBean>

		<center>
			<h1>Change Password</h1>


			<H2>
				<font color="red"> <%=ServletUtility.getErrorMessage(request)%>
				</font>
			</H2>
			<H2>
				<font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
				</font>
			</H2>

			<input type="hidden" name="id" value="<%=bean.getId()%>">
			 <input type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
			<input type="hidden" name="modifiedBy"
				value="<%=bean.getModifiedBy()%>"> <input type="hidden"
				name="createdDatetime"
				value="<%=DataUtility.getTimestamp(bean.getCreatedDateTime())%>">
			<input type="hidden" name="modifiedDatetime"
				value="<%=DataUtility.getTimestamp(bean.getModifiedDateTime())%>">

			<table>



				<tr>
					<th align="left">Old Password<span style="color: red">*</span>
					</th>
					<td><input type="password" name="oldPassword" size=25  placeholder="Enter Your old password" 
						value=<%=DataUtility.getString(request.getParameter("oldPassword") == null ? ""
					: DataUtility.getString(request.getParameter("oldPassword")))%>></td><td style="position: fixed"><font
						color="red"> <%=ServletUtility.getErrorMessage("oldPassword", request)%></font></td>
				</tr>

				<tr>
					<th align="left">New Password<span style="color: red">*</span>
					</th>
					<td><input type="password" name="newPassword" size=25 placeholder="Enter Your new password" 
						value=<%=DataUtility.getString(request.getParameter("newPassword") == null ? ""
								: DataUtility.getString(request.getParameter("newPassword")))%>></td>
						<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("newPassword", request)%></font></td>
				</tr>

				<tr>
					<th align="left">Confirm Password<span style="color: red">*</span>
					</th>
					<td><input type="password" name="confirmPassword" size=25 placeholder="Enter Your new confirm password" 
						value=<%=DataUtility.getString(request.getParameter("confirmPassword") == null ? ""
					: DataUtility.getString(request.getParameter("confirmPassword")))%>></td>
						<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("confirmPassword", request)%></font></td>
				</tr>

				<tr>
					<th></th>
					<td colspan="2"><input type="submit" name="operation"
						value="<%=ChangePasswordCtl.OP_CHANGE_MY_PROFILE%>"> <input
						type="submit" name="operation"
						value="<%=ChangePasswordCtl.OP_SAVE%>"> &nbsp;</td>
				</tr>

			</table>
			
	</form>
	</center>
	<%@ include file="Footer.jsp"%>
</body>
</html>
