<!DOCTYPE html>
<%@include file="taglib.jsp" %>
<html>
<head>
<title>Create User</title>
<link rel="stylesheet" href='<spring:url value="resources/css/styles.css"/>' />
<script type="text/javascript" src='<spring:url value="resources/jquery/jquery-1.10.2.js"/>'></script>
<script type="text/javascript" src='<spring:url value="resources/js/app.js"/>'></script>
<script type="text/javascript">
$(document).ready(function() {
	console.log("ready!");
	$("#createUserForm").submit(function( event ) {
		
		  $("#userNameError").html("");
		  $("#emailError").html("");
		  
		  var userName = $.trim($("#userName").val());
		  var password = $.trim($("#password").val());
		  var firstName = $.trim($("#userName").val());
		  var email = $.trim($("#email").val());
		  if(userName == '' || password == '' || firstName == '' || email == '')
		  {
				alert("Please enter all mandatory fields");
				event.preventDefault();
				return false;
          }
          else
          {
        	  var validForm = true;
        	  $.ajaxSetup({async: false});
        	  $.get("users/checkUserNameExists",{ userName : userName }, function(response){            	  
        		    if(response.status == false){
        		    	$("#userNameError").html(response.errors[0]);
        		    	validForm = false;
            		}
        	  });
        	  
        	  $.get("users/checkEmailExists",{ email : email }, function(response){
      		    if(response.status == false){
    		    	$("#emailError").html(response.errors[0]);
    		    	validForm = false;
          		}
      	  	});
        	
        	$.ajaxSetup({async: true});
        	if(!validForm){
        		event.preventDefault();
  				return false; 
            }
			return true;
          }	 
		   
	});
});
</script>
</head>
<body>
	<p><a href="welcome">Home</a></p>
	<form:form id="createUserForm" method="post" action="createUser" modelAttribute="user">
		<c:if test="${ERROR != null }">
			<div>
				<p>Error: ${ERROR}
			</div>
		</c:if>
		<table>
			
			<tr>
				<td>UserName*</td>
				<td><form:input path="userName" /><span id="userNameError"></span></td>
			</tr>
			<tr>
				<td>Password*</td>
				<td><form:password path="password"/> </td>
			</tr>
			<tr>
				<td>FirstName*</td>
				<td><form:input path="firstName" /> </td>
			</tr>
			<tr>
				<td>LastName</td>
				<td><form:input path="lastName" /> </td>
			</tr>
			<tr>
				<td>Email*</td>
				<td><form:input path="email" /><span id="emailError"></span> </td>
			</tr>
			<tr>
				<td>Phone</td>
				<td><form:input path="phone" /> </td>
			</tr>
			<tr>
				<td>DOB(dd-MM-yyyy)</td>
				<td><form:input path="dob" /> </td>
			</tr>
			<tr>
				<td></td>
				<td><input type="submit" value="Save"> </td>
			</tr>
		</table>
	</form:form>
	
</body>
</html>