<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>  

<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">
  <title>Account</title>
  <!-- Bootstrap core CSS -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/webjars/bootstrap/4.3.1/css/bootstrap.min.css"/>
  
</head>

<body>
  <!-- Navigation -->
   <jsp:include page="menu.jsp"/>

  <!-- Page Content -->
  <div class="container">
    <div class="card card-outline-secondary">
      <div class="card-header">
        <h3 class="mb-0">Change Password</h3>
       </div>
       <div class="card-body">
           <form:form class="form"  action="changepassword" modelAttribute="passwordDTO" method="post" autocomplete="off">
              <div class="form-group row">
                  <form:errors path="*" cssClass="alert alert-danger p-1" />
              </div>
              <div class="form-group row">
                <label class="col-2 col-form-label form-control-label">Current Password</label>
                <div class="col-3">
                    <form:password path="currentPassword" showPassword="true" class="form-control"/>
                </div>
                <div class="col-6"> 
                   <form:errors path="currentPassword" cssClass="alert alert-danger p-0" />
                </div>
                <form:hidden path="id" />
            </div> 
              <div class="form-group row">
                <label class="col-2 col-form-label form-control-label">New Password</label>
                <div class="col-3">
                    <form:password path="password" showPassword="true" class="form-control"/>
                </div>
                <div class="col-6"> 
                   <form:errors path="password" cssClass="alert alert-danger p-0" />
                </div>
            </div> 
            <div class="form-group row">
                <label class="col-2 col-form-label form-control-label">New Repeat Password</label>
                <div class="col-3">
                    <form:password path="repeatPassword" showPassword="true" class="form-control"/>
                </div>
                <div class="col-6">  
                   <form:errors path="repeatPassword" cssClass="alert alert-danger p-0" />     
                </div>
            </div>
	          <div class="form-group row">
	              <label class="col-3 col-form-label form-control-label"></label>
	              <div class="col-9">
	                  <sec:authorize access="hasRole('ADMIN')"> 
	   					 <input type="button" id="back"  data-id="adminlanding" class="btn btn-secondary" value="Cancel"> 
	   	              </sec:authorize>
					  <sec:authorize access="hasRole('USER')">  
	   					 <input type="button" id="back"  data-id="userlanding" class="btn btn-secondary" value="Cancel"> 
	   	              </sec:authorize>    
	                  <input type="reset" class="btn btn-info" value="Reset">
	                  <input type="submit" class="btn btn-info" value="Update Changes">
	              </div>
	          </div>
	           <div class="form-group row">
              <label class="col-3 col-form-label form-control-label"></label>
              <div class="col-9 alert">
                   ${passwordDTO.message}                 
              </div>
	        </div>
           </form:form>
       </div>
    </div>
  </div>
  

  <!-- Bootstrap core JavaScript -->
   <script type="text/javascript" src="${pageContext.request.contextPath}/webjars/jquery/3.4.1/jquery.min.js"></script>
   <script type="text/javascript" src="${pageContext.request.contextPath}/webjars/bootstrap/4.3.1/js/bootstrap.min.js"></script>
   
   <script type="text/javascript">

  $('#back').on('click', function (e) {
 	 url = $(this).data("id"); 
 	 window.location.href = "/"+url;
  }); 
  
  </script>
   
</body>

</html>
