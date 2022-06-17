<%@page import="in.co.rays.util.HTMLUtility"%>
<%@page import="in.co.rays.ctl.RoleListCtl"%>
<%@page import="in.co.rays.ctl.BaseCtl"%>
<%@page import="in.co.rays.bean.RoleBean"%>
<%@page import="in.co.rays.util.ServletUtility"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>

<html>
<script src="<%=ORSView.APP_CONTEXT%>/js/jquery.min.js""></script>
<script src="<%=ORSView.APP_CONTEXT%>/js/Checkbox11.js"></script>

<body>
	<jsp:useBean id="bean" class="in.co.rays.bean.RoleBean" scope="request"></jsp:useBean>
	<%@include file="Header.jsp"%>
	<%
		List rrlist = (List) request.getAttribute("name");
	%>
	<center>
		<h1>Role List</h1>

		<form action="<%=ORSView.ROLE_LIST_CTL%>" method="post">
			<table width="100%">
				<tr align="center">
					<td><label>Name :</label> <%=HTMLUtility.getList("fname", String.valueOf(bean.getId()), rrlist)%>
						&nbsp; <input type="submit" name="operation"
						value="<%=RoleListCtl.OP_SEARCH%>"> <input type="submit"
						name="operation" value="<%=RoleListCtl.OP_RESET%>"></td>
				</tr>
			</table>
			<table border="1" width="100%">
				<tr>
					<th><input type="checkbox" id="select_all" name="select" >Select
						All.</th>
					<th>S.No.</th>
					<!-- <th>Id</th> -->
					<th>Name</th>
					<th>Description</th>
					<th>Edit</th>
				</tr>
				<tr>
					<%--   <td colspan="8"><font color="red"><%=ServletUtility.getErrorMessage(request)%></font></td> --%>
				</tr>

				<%
					int pageNo = ServletUtility.getPageNo(request);
					int pageSize = ServletUtility.getPageSize(request);
					int index = ((pageNo - 1) * pageSize) + 1;

					List list = ServletUtility.getList(request);
					Iterator<RoleBean> it = list.iterator();

					while (it.hasNext()) {
						bean = it.next();
				%>
				<tr align="center">
					<td><input type="checkbox" class="checkbox" name="ids"
						value="<%=bean.getId()%>" <%if (bean.getId() == 1) {%>
						disabled="disabled" <%}%>></td>
					<td><%=index++%></td>
					<%-- <td><%=bean.getId()%></td> --%>
					<td><%=bean.getName()%></td>
					<td><%=bean.getDescription()%></td>


					<%
						if (bean.getId() == 1) {
					%>
					<td><a href="RoleCtl?id=<%=bean.getId()%>"
						onclick="return false;">Edit</a></td>
					<%
						} else {
					%><td><a href="RoleCtl?id=<%=bean.getId()%>">Edit</a></td>
					<%
						}
					%>


				</tr>
				<%
					}
				%>
			</table>
			<table width="100%">
				<tr>
					<td><input type="submit" name="operation" disabled="disabled"
						value="<%=RoleListCtl.OP_PREVIOUS%>"></td>
					<td></td>
					<td align="right"><input type="submit" name="operation"
						disabled="disabled" value="<%=RoleListCtl.OP_NEXT%>"></td>
				</tr>
			</table>
			<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
				type="hidden" name="pageSize" value="<%=pageSize%>">
		</form>
	</center>
	<%@include file="Footer.jsp"%>
</body>
</html>





