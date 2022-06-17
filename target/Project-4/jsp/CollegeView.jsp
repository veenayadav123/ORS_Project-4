<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@page import="in.co.rays.ctl.CollegeCtl"%>
<%@page import="in.co.rays.util.DataUtility"%>
<%@page import="in.co.rays.util.ServletUtility"%>
<html>
<head>
<link rel="icon" type="image/png" href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16*16"/>
<title> College Registration Page</title>
</head>
<body>
  <jsp:useBean id="bean" class="in.co.rays.bean.CollegeBean" scope="request"></jsp:useBean>
  <form action="CollegeCtl" method="post">
  <%@ include file="Header.jsp"%>

    

        
        <center>
           <h1>
            	<%
            		if(bean != null && bean.getId()>0){
            	%>
            		<tr><th><font>Update College</font></th></tr>
            	
            	<% }else{%>
            		
            		<tr><th><font>Add College</font></th></tr>
            	<% }%>            
            </h1>

      <div>
            <H2><font color="green"> <%=ServletUtility.getSuccessMessage(request)%></font></H2>
            <H2><font color="red"> <%=ServletUtility.getErrorMessage(request)%></font></H2>     
     </div>
           
            <input type="hidden" name="id" value="<%=bean.getId()%>"> 
            <input type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
            <input type="hidden" name="modifiedBy" value="<%=bean.getModifiedBy()%>"> 
            <input type="hidden" name="createdDatetime" value="<%=DataUtility.getTimestamp(bean.getCreatedDateTime())%>">
            <input type="hidden" name="modifiedDatetime" value="<%=DataUtility.getTimestamp(bean.getModifiedDateTime())%>">

            <table>
                <tr>
                    <th align="left">Name<span style="color: red">*</span></th>
                    <td><input type="text" name="name" placeholder="Enter College Name" size="23px" value="<%=DataUtility.getStringData(bean.getName())%>">
                    </td><td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("name", request)%></font></td>
                </tr>
       <tr><th style="padding: 3px"></th></tr>        
                <tr>
                    <th align="left">Address<span style="color: red">*</span></th>
                    <td><input type="text" name="address" placeholder="Enter College Address" size="23px" value="<%=DataUtility.getStringData(bean.getAddress())%>">
                    </td><td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("address", request)%></font></td>
                </tr>
      <tr><th style="padding: 3px"></th></tr>          
                <tr>
                    <th align="left">State<span style="color: red">*</span></th>
                    <td><input type="text" name="state"  placeholder="Enter State" size="23px" value="<%=DataUtility.getStringData(bean.getState())%>">
                    </td><td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("state", request)%></font></td>
                </tr>
      <tr><th style="padding: 3px"></th></tr>
                <tr>
                    <th align="left">City<span style="color: red">*</span></th>
                    <td><input type="text" name="city" placeholder="Enter City" size="23px" value="<%=DataUtility.getStringData(bean.getCity())%>">
                    </td><td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("city", request)%></font></td>
                </tr>
     <tr><th style="padding: 3px"></th></tr>            
                <tr>
                    <th align="left">MobileNo<span style="color: red">*</span></th>
                    <td><input type="text" name="phoneNo" placeholder="Enter Mobile No" maxlength="10"  size="23px" value="<%=DataUtility.getStringData(bean.getPhoneNo())%>">
                   </td><td style="position: fixed"> <font color="red"> <%=ServletUtility.getErrorMessage("phoneNo", request)%></font></td>
                </tr>
     <tr><th style="padding: 3px"></th></tr>            
                <tr><th></th>
                <%if(bean.getId()>0) {%>
                    <td colspan="2">
                    <input type="submit" name="operation" value="<%=CollegeCtl.OP_UPDATE%>">                    
                     
                     <input type="submit" name="operation" value="<%=CollegeCtl.OP_CANCEL%>"></td>
                <%}else{ %>
                	 <td colspan="2">
                    <input type="submit" name="operation" value="<%=CollegeCtl.OP_SAVE%>">                    
                     
                     <input type="submit" name="operation" value="<%=CollegeCtl.OP_RESET%>"></td>
                <%} %>
                </tr>
            </table>
    </form>
    </center>
    <%@ include file="Footer.jsp"%>
</body>
</html>