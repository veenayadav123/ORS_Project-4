<%@page import="in.co.rays.model.StudentModel"%>
<%@page import="in.co.rays.util.HTMLUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@page import="in.co.rays.ctl.StudentListCtl"%>
<%@page import="in.co.rays.util.ServletUtility"%>
<%@page import="in.co.rays.bean.StudentBean"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<html>
<script src="<%=ORSView.APP_CONTEXT %>/js/jquery.min.js"></script>
<script src="<%=ORSView.APP_CONTEXT %>/js/Checkbox11.js"></script>

<body>
<%List rlist = (List) request.getAttribute("name"); %>
<jsp:useBean id="bean" class="in.co.rays.bean.StudentBean" scope="request" ></jsp:useBean>
    <%@include file="Header.jsp"%>
   <div align="center">
	        <h1>Student List</h1>
            <h2><font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
            <font color="green"><%=ServletUtility.getSuccessMessage(request)%></font></h2>
     </div>
        <form action="<%=ORSView.STUDENT_LIST_CTL%>" method="post">
            <table width="100%">
                <tr>
                    <td align="center"><label> FirstName :</label> 
                    <%= HTMLUtility.getList("fname",String.valueOf(bean.getId()), rlist) %>
                        <label>LastName :</label>
                         <input type="text" name="lastName" placeholder="Enter Your Last Name" value="<%=ServletUtility.getParameter("lastName", request)%>">
                         <label>Email_Id :</label>
                          <input type="text" name="email" placeholder="Enter Your Email Name"  value="<%=ServletUtility.getParameter("email", request)%>">
                        <input type="submit" name="operation" value="<%=StudentListCtl.OP_SEARCH %>">&nbsp;
                        <input type="submit" name="operation" value="<%=StudentListCtl.OP_RESET %>"></td>
                         
                </tr>
            </table>
            <br>
            <table border="1" width="100%">
                <tr>
		<th ><input type="checkbox" id="select_all" name ="Select">Select All.</th>
                    <!-- <th>ID</th> -->
                    <!-- <th>College</th> -->
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th>Date Of Birth</th>
                    <th>Mobile No</th>
                    <th>Email ID</th>
                    <th>Edit</th>
                </tr>
                
                <%
                    int pageNo = ServletUtility.getPageNo(request);
                    int pageSize = ServletUtility.getPageSize(request);
                    int index = ((pageNo - 1) * pageSize) + 1;

                    List list = ServletUtility.getList(request);
                    Iterator<StudentBean> it = list.iterator();

                    while (it.hasNext()) {

                         bean = it.next();
                %>
                <tr>
                   <td align="center"><input type="checkbox" class="checkbox" name="ids" value="<%=bean.getId()%>">
                    <%-- <td><%=bean.getId()%></td> --%>
                    <%-- <td><%=bean.getCollegeID()%></td> --%>
                    <td><%=bean.getFirstName()%></td>
                    <td><%=bean.getLastName()%></td>
                    <td><%=bean.getDob()%></td>
                    <td><%=bean.getMobileNo()%></td>
                    <td><%=bean.getEmail()%></td>
                    <td><a href="StudentCtl?id=<%=bean.getId()%>">Edit</a></td>
                </tr>
                <%
                    }
                %>
            </table>
             <table width="100%">
                <tr>
                <%if(pageNo == 1){ %>
                    <td><input type="submit" name="operation" disabled="disabled" value="<%=StudentListCtl.OP_PREVIOUS%>">
       				<%}else{ %>
       				<td><input type="submit" name="operation"  value="<%=StudentListCtl.OP_PREVIOUS%>"></td>
       				<%} %>		
                     
                     <td><input type="submit" name="operation" value="<%=StudentListCtl.OP_DELETE%>"> </td>
                    <td> <input type="submit" name="operation" value="<%=StudentListCtl.OP_NEW%>"></td>
                    
                  <% StudentModel model = new StudentModel();
                  %>  
                  <% if(list.size()<pageSize || model.nextPK()-1 == bean.getId()){ %>
                  <td align="right"> <input type="submit" name="operation" disabled="disabled" value="<%=StudentListCtl.OP_NEXT%>"></td>
  					<%}else{ %>                   
  				  <td align="right"> <input type="submit" name="operation"  value="<%=StudentListCtl.OP_NEXT%>"></td>
   					<%} %>                 
                    
                </tr>
            </table>
                       <input type="hidden" name="pageNo" value="<%=pageNo%>"><input
                type="hidden" name="pageSize" value="<%=pageSize%>">
<br>
<br>
<br>
<br>
<br>
<br>
<br>
		
        </form>
    </center>
    <%@ include file= "Footer.jsp"%>
</body>
</html>