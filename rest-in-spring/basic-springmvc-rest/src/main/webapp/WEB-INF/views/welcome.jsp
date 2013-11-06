<!DOCTYPE html>
<%@include file="taglib.jsp" %>
<html>
<head>
<title>Welcome</title>
<link rel="stylesheet" href='<spring:url value="resources/css/styles.css"/>' />
<script type="text/javascript" src='<spring:url value="resources/jquery/jquery-1.10.2.js"/>'></script>
<script type="text/javascript" src='<spring:url value="resources/js/app.js"/>'></script>
<script type="text/javascript">
$(document).ready(function() {
	console.log("ready!");
});
</script>
</head>
<body>
	<h2> Welcome</h2>
	<p><a href="createUser">Create User</a></p>
	<h2>User List</h2>
	<table>
		<thead>
			<tr>
				<th>Id</th>
				<th>UserName</th>
				<th>Email</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="user" items="${users}">
				<tr>
					<td>${user.id}</td>
					<td>${user.userName}</td>
					<td>${user.email}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>