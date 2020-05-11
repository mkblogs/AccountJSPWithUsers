<!DOCTYPE html>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>  
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">
<title>Account</title>
<!-- Bootstrap core CSS -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/webjars/bootstrap/4.3.1/css/bootstrap.min.css" />
</head>

<body>
	<!-- Navigation -->
	 <jsp:include page="menu.jsp"/>

	<!-- Page Content -->
	<div class="container">
		<div class="card card-outline-secondary">
			<div class="card-header">
				<h3 class="mb-0">User Information</h3>
			</div>
			<div class="card-body">
				<div class="row">
					<div class="col-12">
						<label class="col-form-label form-control-label">User Id: ${user.id}</label>
					</div>
				</div>
				
				<div class="row">
					<div class="col-12">
						<label class="col-form-label form-control-label">Username: ${user.loginName}</label>
					</div>
				</div>
				<div class="row">
					<div class="col-12">
						<label class="col-form-label form-control-label">Last Login: ${user.lastLogin}</label>
					</div>
				</div>
				<div class="row">
					<div class="col-12">
						<label class="col-form-label form-control-label">User Account Expired: ${user.accountExpired ? 'YES' : 'NO'}</label>
					</div>
				</div>
				<div class="row">
					<div class="col-12">
						<label class="col-form-label form-control-label">User Account Expired: ${user.accountLocked ? 'YES' : 'NO'}</label>
					</div>
				</div>
				<div class="row">
					<div class="col-12">
						<label class="col-form-label form-control-label">User Credentials Expired: ${user.credentialsExpired ? 'YES' : 'NO'}</label>
					</div>
				</div>
				<div class="row">
					<div class="col-12">
						<label class="col-form-label form-control-label">User Account Enabled: ${user.enabled ? 'YES' : 'NO'}</label>
					</div>
				</div>
				<div class="row">
					<div class="col-12">
						<label class="col-form-label form-control-label">Created By: ${user.createdBy}</label>
					</div>
				</div>
				<div class="row">
					<div class="col-12">
						<label class="col-form-label form-control-label">Created Name: ${user.createdName}</label>
					</div>
				</div>
				<div class="row">
					<div class="col-12">
						<label class="col-form-label form-control-label">Created Ts: ${user.createdTs}</label>
					</div>
				</div>
				<div class="row">
					<div class="col-12">
						<label class="col-form-label form-control-label">Modified By: ${user.lastModifiedBy}</label>
					</div>
				</div>
				<div class="row">
					<div class="col-12">
						<label class="col-form-label form-control-label">Modified Name: ${user.lastModifiedName}</label>
					</div>
				</div>
				<div class="row">
					<div class="col-12">
						<label class="col-form-label form-control-label">Modified Ts: ${user.lastModifiedTs}</label> 
					</div>
				</div>
				<div class="row">
					<div class="col-12">
						<label class="col-form-label form-control-label">Version: ${user.version}</label>
					</div>
				</div>
				<div class="row">
					<div class="col-12">
						<hr/>
					</div>
				</div>
				<div class="row">
					<h5 class="mb-0">User Settings</h5>
				</div>
				<div class="row">
					<div class="col-12">
						<label class="col-form-label form-control-label">Connection Type: ${user.settings.connectionType}</label>
					</div>
				</div>
				<div class="row">
					<div class="col-12">
						<label class="col-form-label form-control-label">Primary Key Generation Type: ${user.settings.primaryKeyGenerationType}</label>
					</div>
				</div>
				<div class="row">
					<div class="col-12">
						<label class="col-form-label form-control-label">Authentication Type: ${user.settings.authenticationType}</label>
					</div>
				</div>
				<div class="row">
					<div class="col-12">
						<label class="col-form-label form-control-label">Authentication Encrypted: ${user.settings.authenticationEncrypted ? 'YES' : 'NO'}</label>
					</div>
				</div>
				<div class="row">
					<div class="col-12">
						<label class="col-form-label form-control-label">Role: ${user.role}</label>
					</div>
				</div>
				<div class="row">
					<div class="col-12">
						<hr/>
					</div>
				</div>
				<div class="row">
					<div class="col-12">
					   <sec:authorize access="hasAnyRole('ADMIN','ADMIN_READ_ONLY')">  
    					 <input type="button" id="back"  data-id="adminlanding" class="btn btn-info" value="Back"> 
    	               </sec:authorize>
					    <sec:authorize access="hasAnyRole('USER','USER_READ_ONLY')">  
    					 <input type="button" id="back"  data-id="userlanding" class="btn btn-info" value="Back"> 
    	               </sec:authorize> 	
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- Bootstrap core JavaScript -->
	<script type="text/javascript" 	src="${pageContext.request.contextPath}/webjars/jquery/3.4.1/jquery.min.js"></script>
	 <script type="text/javascript" src="${pageContext.request.contextPath}/webjars/bootstrap/4.3.1/js/bootstrap.min.js"></script>
     <script type="text/javascript">
     
     $('#back').on('click', function (e) {
    	 url = $(this).data("id"); 
    	 window.location.href = "/"+url;
    })
     
     </script>
</body>

</html>
