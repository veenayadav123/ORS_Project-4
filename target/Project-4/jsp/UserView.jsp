<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.util.HTMLUtility"%>
<%@page import="in.co.rays.util.DataUtility"%>
<%@page import="in.co.rays.util.ServletUtility"%>
<%@page import="in.co.rays.ctl.UserCtl"%>


<html>
<head>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16*16" />
<title>User Register</title>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="/resources/demos/style.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
	/* $( function() {
	  $( "#udatee" ).datepicker({
	    changeMonth: true,
	    changeYear: true,
	  yearRange:'1980:2020',
	  dateFormat:'dd-mm-yy'
	  });
	} ); */
</script>
</head>

<body>
	<form action="<%=ORSView.USER_CTL%>" method="post">
		<%@ include file="Header.jsp"%>
		<jsp:useBean id="bean" class="in.co.rays.bean.UserBean"
			scope="request"></jsp:useBean>


		<center>

			<%
				List list = (List) request.getAttribute("roleList");
				System.out.println("List in view............................" + list);
			%>

			<div align="center">
				<h1>

					<%
						if (bean != null && bean.getId() > 0) {
					%>
					<tr>
						<th><font size="5px"> Update User </font></th>
					</tr>
					<%
						} else {
					%>
					<tr>
						<th><font size="5px"> Add User </font></th>
					</tr>
					<%
						}
					%>
				</h1>

				<h2>
					<font color="red"> <%=ServletUtility.getErrorMessage(request)%></font>
					<font color="green"> <%=ServletUtility.getSuccessMessage(request)%></font>
				</h2>

			</div>
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
					<th align="left">First Name<span style="color: red">*</span></th>
					<td><input type="text" name="firstName"
						placeholder="Enter First Name" size="24"
						value="<%=DataUtility.getStringData(bean.getFirstName())%>">
					</td>
					<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("firstName", request)%></font></td>

				</tr>

				<tr>
					<th style="padding: 3px"></th>
				</tr>

				<tr>
					<th align="left">Last Name<span style="color: red">*</span>
					</th>
					<td><input type="text" name="lastName"
						placeholder="Enter Last Name" size="24"
						value="<%=DataUtility.getStringData(bean.getLastName())%>">
					</td>
					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("lastName", request)%></font></td>
				</tr>
				<tr>
					<th style="padding: 3px"></th>
				</tr>

				<tr>
					<th align="left">LoginId<span style="color: red">*</span></th>
					<td><input type="text" name="login"
						placeholder="Enter Vallid Email-Id" size="24"
						value="<%=DataUtility.getStringData(bean.getLogin())%>"
						<%=(bean.getId() > 0) ? "readonly" : ""%>></td>
					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("login", request)%></font></td>
				</tr>

				<%
					if (bean.getId() > 0 && bean != null) {
				%>
				<tr>

					<td><input type="hidden" name="password"
						value="<%=DataUtility.getStringData(bean.getPassword())%>"></td>
					<td><input type="hidden" name="confirmPassword"
						value="<%=DataUtility.getStringData(bean.getPassword())%>"></td>
				</tr>

				<%
					} else {
				%>
				<tr>
					<th style="padding: 3px"></th>
				</tr>

				<tr>
					<th align="left">Password<span style="color: red">*</span></th>
					<td><input type="password" name="password"
						placeholder="Veena@123" size="24"
						value="<%=DataUtility.getStringData(bean.getPassword())%>">
					</td>
					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("password", request)%></font></td>
				</tr>
				<tr>
					<th style="padding: 3px"></th>
				</tr>

				<tr>
					<th align="left">Confirm Password<span style="color: red">*</span></th>
					<td><input type="password" name="confirmPassword"
						placeholder="Re-Enter Password" size="24"
						value="<%=DataUtility.getStringData(bean.getPassword())%>">
					</td>
					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("confirmPassword", request)%></font></td>
				</tr>
				<%
					}
				%>
				<tr>
					<th style="padding: 3px"></th>
				</tr>

				<tr>
					<th align="left">Gender<span style="color: red">*</span></th>
					<td>
						<%
							HashMap map = new HashMap();
							map.put("Male", "Male");
							map.put("Female", "Female");

							String htmlList = HTMLUtility.getList("gender", bean.getGender(), map);
						%> <%=htmlList%>

					</td>
					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("gender", request)%></font></td>
				</tr>
				<tr>
					<th style="padding: 3px"></th>
				</tr>

				<tr>
					<th align="left">Role<span style="color: red" value="<%=DataUtility.getStringData(bean.getRoleId())%>">*</span></th>
					<td><%=HTMLUtility.getList("roleId", String.valueOf(bean.getRoleId()), list)%>
					</td>
					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("roleId", request)%></font></td>
				</tr>

				<tr>
					<th style="padding: 3px"></th>
				</tr>

				<tr>
					<th align="left">Date Of Birth<span style="color: red">*</span>
					</th>
					<td><input type="text" name="dob" id="datepicker"
						placeholder="Greater then 18 year" size="24" readonly="readonly"
						value="<%=DataUtility.getDateString(bean.getDob())%>"></td>
					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("dob", request)%></font></td>
				</tr>
				<tr>
				<tr>
					<th style="padding: 3px"></th>
				</tr>

				<tr>
					<th align="left">Mobile No<span style="color: red">*</span></th>
					<td><input type="text" name="mobileno" maxlength="10"
						placeholder="Enter Mobile No" size="24"
						value="<%=DataUtility.getStringData(bean.getMobileNo())%>">
					</td>
					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("mobileno", request)%></font></td>
				</tr>
				<tr>
					<th style="padding: 3px"></th>
				</tr>

				<tr>
					<th></th>
					<%
						if (bean.getId() > 0) {
					%>
					<td colspan="2"><input type="submit" name="operation"
						value="<%=UserCtl.OP_UPDATE%>"> <input type="submit"
						name="operation" value="<%=UserCtl.OP_CANCEL%>"></td>

					<%
						} else {
					%>

					<td colspan="2"><input type="submit" name="operation"
						value="<%=UserCtl.OP_SAVE%>"> <input type="submit"
						name="operation" value="<%=UserCtl.OP_RESET%>"></td>

					<%
						}
					%>
				</tr>
			</table>
	</form>
	<br>
	<br>


	<%@ include file="Footer.jsp"%>
</body>
</html>
