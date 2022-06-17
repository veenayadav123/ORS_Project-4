<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@page import="in.co.rays.ctl.StudentCtl"%>
<%@page import="in.co.rays.bean.CollegeBean"%>
<%@page import="in.co.rays.util.DataUtility"%>
<%@page import="in.co.rays.util.HTMLUtility"%>
<%@page import="in.co.rays.ctl.StudentListCtl"%>
<%@page import="in.co.rays.util.ServletUtility"%>
<%@page import="in.co.rays.bean.StudentBean"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<html>
<body>
	<jsp:useBean id="bean" class="in.co.rays.bean.StudentBean"
		scope="request"></jsp:useBean>

	<form action="<%=ORSView.STUDENT_CTL%>" method="post">
		<%@include file="Header.jsp"%>

		<%
			List<CollegeBean> clist = (List<CollegeBean>) request.getAttribute("collegeList");
		%>

		<center>
			<h1>
            	<%
            		if(bean != null && bean.getId()>0){
            	%>
            		<tr><th><font>Update Student</font></th></tr>
            	
            	<% }else{%>
            		
            		<tr><th><font>Add Student</font></th></tr>
            	<% }%>            
            </h1>
			<div>
				<h1>
					<font style="color: green"><%=ServletUtility.getSuccessMessage(request)%></font>
				</h1>
				<h1>
					<font style="color: red"><%=ServletUtility.getErrorMessage(request)%></font>
				</h1>
			</div>

			<input type="hidden" name="id" value="<%=bean.getId()%>"> <input
				type="hidden" name="createdby" value="<%=bean.getCreatedBy()%>">
			<input type="hidden" name="modifiedby"
				value="<%=bean.getModifiedBy()%>"> <input type="hidden"
				name="createddatetime" value="<%=bean.getCreatedDateTime()%>">
			<input type="hidden" name="modifieddatetime"
				value="<%=bean.getModifiedDateTime()%>">

			<table>
				<tr>
					<th align="left">CollegeName<span style="color: red">*</span></th>
					<td><%=HTMLUtility.getList("collegename", String.valueOf(bean.getCollegeID()), clist)%>
					<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("collegename", request)%></font>

					</td>
				</tr>
				<tr>
					<th style="padding: 3px"></th>
				</tr>
				<tr>
					<th align="left">FirstName<span style="color: red">*</span></th>
					<td><input type="text" name="firstname"
						placeholder="Enter First Name" size="24"
						value="<%=DataUtility.getStringData(bean.getFirstName())%>"></td>
					<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("firstname", request)%></font>
					</td>
				</tr>
				<tr>
					<th style="padding: 3px"></th>
				</tr>
				<tr>
					<th align="left">LastName<span style="color: red">*</span></th>
					<td><input type="text" name="lastname"
						placeholder="Enter Last Name" size="24"
						value="<%=DataUtility.getStringData(bean.getLastName())%>"></td>
					<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("lastname", request)%></font>
					</td>
				</tr>
				<tr>
					<th style="padding: 3px"></th>
				</tr>
				<tr>
					<th align="left">Date Of Birth<font color="red">*</font></th>
					<td><input type="text" name="dob" id="datepicker"
						placeholder="Enter Date Of Birth" size="24" readonly="readonly"
						value="<%=DataUtility.getDateString(bean.getDob())%>">
						&nbsp;<font style="position: fixed;" color="red"> <%=ServletUtility.getErrorMessage("dob", request)%></font></td>
				</tr>
				
				<tr>
					<th style="padding: 3px"></th>
				</tr>
				<tr>
					<th align="left">MobileNo<span style="color: red">*</span></th>
					<td><input type="text" name="mobile" maxlength="10"
						placeholder="Enter Mobile No" size="24"
						value="<%=DataUtility.getStringData(bean.getMobileNo())%>"></td>
					<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("mobile", request)%></font>
					</td>
				</tr>
				<tr>
					<th style="padding: 3px"></th>
				</tr>
				<tr>
					<th align="left">Email-Id<span style="color: red">*</span></th>
					<td><input type="text" name="email"
						placeholder="Enter Email_Id" size="24s"
						value="<%=DataUtility.getStringData(bean.getEmail())%>"></td>
					<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("email", request)%></font>
					</td>
				</tr>
				<tr>
					<th style="padding: 3px"></th>
				</tr>


				<tr>
					<th></th>
					<%
						if (bean.getId() > 0) {
					%>
					<td><input type="submit" name="operation"
						value="<%=StudentCtl.OP_UPDATE%>"> <input type="submit"
						name="operation" value="<%=StudentCtl.OP_CANCEL%>"></td>
					<%
						} else {
					%>
					<td><input type="submit" name="operation"
						value="<%=StudentCtl.OP_SAVE%>"> <input type="submit"
						name="operation" value="<%=StudentCtl.OP_RESET%>"></td>

					<%
						}
					%>
				</tr>

			</table>
	</form>
	</center>
	<%@ include file="Footer.jsp"%>
</body>
</html>