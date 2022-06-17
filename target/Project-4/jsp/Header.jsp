<!DOCTYPE html>
<%-- <%@page import="in.co.rays.ors.controller.LoginCtl"%> --%>
<%@page import="in.co.rays.ctl.ORSView"%>
<%@ page import ="in.co.rays.ctl.LoginCtl" %>
<%@page import="in.co.rays.bean.RoleBean"%>
<%@page import="in.co.rays.bean.UserBean"%>
<%@page import="in.co.rays.ctl.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<html>
<HEAD>
	<style>
	form{ height:85%!important;}
	
	</style>

<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="/resources/demos/style.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
	$(function() {
		$("#datepicker").datepicker({
			dateFormat : 'mm/dd/yy',
			//defaultDate : "06/06/2001",
			changeMonth : true,
			changeYear : true,
			//yearRange: "c-20:c+0",
			//yearRange : "1980:2025",
		//maxDate:'0',
		// minDate:0
		yearRange : "-40:-18"
		});
	});
</script>
</HEAD>
<body >
	<%
    UserBean userBean = (UserBean)session.getAttribute("user");
    boolean userLoggedIn = userBean != null;
    String welcomeMsg = "Hi, ";
    if (userLoggedIn) {
        String role = (String)session.getAttribute("role");
        welcomeMsg += userBean.getFirstName() + " (" + role + ")";
    } else {
        welcomeMsg += "Guest";
    }
	%>

<table >
    <tr ><th></th>
       <td width="90%" >
       <a href="<%=ORSView.WELCOME_CTL%>">Welcome</b></a> |
            	<%
            		if (userLoggedIn) {
      		 	 %> 
       <a href=" <%=ORSView.LOGIN_CTL%>?operation=<%=LoginCtl.OP_LOG_OUT%>">Logout</b></a>

            <%
                } else {
            %> 
            <a href="<%=ORSView.LOGIN_CTL%>">Login</b></a> 
            <%
  			   }
			 %>
 		</td>
        <td rowspan="2" >
            <h1 align="right" >
                <img src= "<%=ORSView.APP_CONTEXT %>/img/Logo.jpg"  width="250" height="50">
            </h1>
        </td>
    </tr>

    <tr><th></th>
      <td>
        <h3><%=welcomeMsg%></h3>
      </td>
    </tr>
   
    <%
        if (userLoggedIn) {
    %>

    <tr><th></th>
        <td colspan="2" >     
        <font style="font-size: 18px">
       	 
        <a href="<%=ORSView.MY_PROFILE_CTL%>">MyProfile</b></a> |       
         <a href="<%=ORSView.GET_CHANGE_PASSWORD_CTL%>">Change Password</b></a> |
       	 <a href="<%=ORSView.GET_MARKSHEET_CTL%>">Get Marksheet</b></a> |              
       	 <a href="<%=ORSView.MARKSHEET_MERIT_LIST_CTL%>">Marksheet MeritList</b></a> |
        
        <%
            if (userBean.getRoleId() == RoleBean.ADMIN) {
        %> 
       
        <a href="<%=ORSView.MARKSHEET_CTL%>">Add Marksheet</b></a> |       
        <a href="<%=ORSView.MARKSHEET_LIST_CTL%>">Marksheet List</b></a> |
        <a href="<%=ORSView.USER_CTL%>">Add User</b></a> | 
        <a href="<%=ORSView.USER_LIST_CTL%>">User List</b></a> |         
        <a href="<%=ORSView.COLLEGE_CTL%>">Add College</b></a> |        
        <a href="<%=ORSView.COLLEGE_LIST_CTL%>">College List</b></a> |
        <a href="<%=ORSView.ROLE_CTL%>">Add Role</b></a> |        
        <a href="<%=ORSView.ROLE_LIST_CTL%>">Role List</b></a> |
        <br>
        <a href="<%=ORSView.STUDENT_CTL%>">Add Student</b></a> |
        <a href="<%=ORSView.STUDENT_LIST_CTL%>">Student List</b></a> |
        <a href="<%=ORSView.COURSE_CTL %>" >Add Course</b></a> |       
        <a href="<%=ORSView.COURSE_LIST_CTL %>">Course List</b></a> |       
        <a href="<%=ORSView.SUBJECT_CTL %>" >Add Subject</b></a> |       
        <a href="<%=ORSView.SUBJECT_LIST_CTL %>">Subject List</b></a> |       
        <a href="<%=ORSView.FACULTY_CTL %>" >Add Faculty</b></a> |       
        <a href="<%=ORSView.FACULTY_LIST_CTL %>">Faculty List</b></a> |
        <a href="<%=ORSView.TIMETABLE_CTL %>" >Add TimeTable</b></a> |       
        <a href="<%=ORSView.TIMETABLE_LIST_CTL %>">TimeTable List</b></a> |
        <a target="blank"  href="<%=ORSView.JAVA_DOC_VIEW%>">Java Doc</b></a> |
       <%
     		}
 		%>
 		 <%
            if (userBean.getRoleId() == RoleBean.STUDENT) {
        %> 
       
        <a href="<%=ORSView.COLLEGE_LIST_CTL%>">College List</b></a> |
        <a href="<%=ORSView.STUDENT_LIST_CTL%>">Student List</b></a> |
        <a href="<%=ORSView.COURSE_LIST_CTL %>">Course List</b></a> |       
        <a href="<%=ORSView.SUBJECT_LIST_CTL %>">Subject List</b></a> |       
        <a href="<%=ORSView.FACULTY_LIST_CTL %>">Faculty List</b></a> |
        <a href="<%=ORSView.TIMETABLE_LIST_CTL %>">TimeTable List</b></a> |
       <%
     		}
 		%>
		
 		<%
            if (userBean.getRoleId() == RoleBean.KIOSK) {
        %> 
        <a href="<%=ORSView.COLLEGE_LIST_CTL%>">College List</b></a> |
        <a href="<%=ORSView.TIMETABLE_LIST_CTL %>">TimeTable List</b></a> |
        <a href="<%=ORSView.COURSE_LIST_CTL %>">Course List</b></a> |       
       <%
     		}
 		%>
 		 <%
            if (userBean.getRoleId() == RoleBean.FACULTY) {
            System.out.println("======>><><>"+userBean.getRoleId());	
        %> 
       
        <a href="<%=ORSView.MARKSHEET_CTL%>">Add Marksheet</b></a> |       
        <a href="<%=ORSView.MARKSHEET_LIST_CTL%>">Marksheet List</b></a> |
        <a href="<%=ORSView.COLLEGE_LIST_CTL%>">College List</b></a> |
        <a href="<%=ORSView.STUDENT_CTL%>">Add Student</b></a> |
        <a href="<%=ORSView.STUDENT_LIST_CTL%>">Student List</b></a> |
        <a href="<%=ORSView.COURSE_LIST_CTL %>">Course List</b></a> |       
        <a href="<%=ORSView.SUBJECT_CTL %>" >Add Subject</b></a> |   
        <br>    
        <a href="<%=ORSView.SUBJECT_LIST_CTL %>">Subject List</b></a> |       
        <a href="<%=ORSView.TIMETABLE_CTL %>" >Add TimeTable</b></a> |       
        <a href="<%=ORSView.TIMETABLE_LIST_CTL %>">TimeTable List</b></a> |
       <%
     		}
 		%>
 		 <%
            if (userBean.getRoleId() == RoleBean.COLLEGE_SCHOOL) {
           System.out.println("======>><><>"+userBean.getRoleId());	
          %> 
       
        <a href="<%=ORSView.MARKSHEET_CTL%>">Add Marksheet</b></a> |       
        <a href="<%=ORSView.MARKSHEET_LIST_CTL%>">Marksheet List</b></a> |
        <a href="<%=ORSView.STUDENT_CTL%>">Add Student</b></a> |
        <a href="<%=ORSView.STUDENT_LIST_CTL%>">Student List</b></a> |
        <a href="<%=ORSView.FACULTY_LIST_CTL %>">Faculty List</b></a> |
        <a href="<%=ORSView.TIMETABLE_LIST_CTL %>">TimeTable List</b></a> |
        <a href="<%=ORSView.COURSE_LIST_CTL %>">Course List</b></a> |       
       <%
     		}
 		%>
 		
 		</font>
 		</td>
    </tr>
    	<%
        	}
   		 %>
</table>
<hr>
</body>	
</html>