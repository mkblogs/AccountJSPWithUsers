<!DOCTYPE html>
<html lang="en">
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>  

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
  <jsp:include page="menu.jsp"/>

  <!-- Page Content -->
  <div class="container">
    <div class="card card-outline-secondary">
      <div class="card-header">
        <h3 class="mb-0">Users</h3>
       </div>
       <div class="card-body">
           <form:form class="form" id="userDTO"  action="users" modelAttribute="userDTO" method="post" autocomplete="off">
               <div class="form-group row">                 
                 <div class="col-12">
					<form:errors path="*" cssClass="alert alert-danger p-1" />                 
                 </div>              
              </div>
              <div class="form-group row">
                  <label class="col-2 col-form-label form-control-label">Username</label>
                  <div class="col-3">
                      <form:input path="username" class="form-control"/>
                  </div>
                  <div class="col-6">
                     <form:errors path="username" cssClass="alert alert-danger p-0" />
                  </div>
              </div>
              <div class="form-group row">
                <label class="col-2 col-form-label form-control-label">Password</label>
                <div class="col-3">
                    <form:password path="password" showPassword="true" class="form-control"/>
                </div>
                <div class="col-6"> 
                   <form:errors path="password" cssClass="alert alert-danger p-0" />
                </div>
            </div> 
            <div class="form-group row">
                <label class="col-2 col-form-label form-control-label">Repeat Password</label>
                <div class="col-3">
                    <form:password path="repeatPassword" showPassword="true" class="form-control"/>
                </div>
                <div class="col-6">  
                   <form:errors path="repeatPassword" cssClass="alert alert-danger p-0" />     
                </div>
            </div>
            <div class="form-group row">
                <label class="col-2 col-form-label form-control-label">Auth Type</label>
                <div class="col-3">                   
                     <form:select path="authType" class="form-control" >
                         <form:option value="">Select One</form:option>
                         <form:option value="DB">DB</form:option>                         
                    </form:select>
                </div>
                <div class="col-6">  
                  <form:errors path="authType" cssClass="alert alert-danger p-0" />    
                </div>
            </div>
            <div class="form-group row">
                <label class="col-2 col-form-label form-control-label">Encrypted</label>
                <div class="col-3">
                    <form:checkbox path="authEncrypted" />
                </div>
                <div class="col-6"> 
                </div>
            </div>
            <div class="form-group row">
                <label class="col-2 col-form-label form-control-label">Connection Type</label>
                <div class="col-3">                   
                    <form:select path="connectionType" class="form-control" >
                        <form:option value="">Select One</form:option>
                        <form:option value="PLAIN_JDBC">Plain_JDBC</form:option>
                       	<form:option value="TOMCAT">Tomcat DataSource</form:option>
                      	<form:option value="HIKARI">Hikari DataSource</form:option>
                       	<form:option value="ROW_MAPPER">Row Mapper With DBCP DataSource</form:option>
                       	<form:option value="ENTITY_MANAGER">Hibernate Entity Manager</form:option>
                       	<form:option value="JPA">JPA</form:option>
                       	<form:option value="QUERY_DSL">Query DSL</form:option>
                      </form:select>
                </div>
                <div class="col-6">  
                  <form:errors path="connectionType" cssClass="alert alert-danger p-0" />    
                </div>
            </div>
            <div class="form-group row">
                <label class="col-2 col-form-label form-control-label">Generation Type</label>
                <div class="col-3">
                    <form:select path="primaryKeyGenerationType" class="form-control" >
                         <form:option value="">Select One</form:option>
                         <form:option value="AUTO">AUTO</form:option>
                         <form:option value="ATOMIC">Java Atomic</form:option>
                         <form:option value="SERIES">Series Table</form:option>
                         <form:option value="CACHE">CACHE</form:option>
                    </form:select>
                </div>
                <div class="col-6"> 
                    <form:errors path="primaryKeyGenerationType" cssClass="alert alert-danger p-0" />                     
                </div>
            </div>                     
            <div class="form-group row">
                <label class="col-2 col-form-label form-control-label">Roles</label>
                <div class="col-3">
                   <form:select path="role" class="form-control" >
                     <form:option value="">Select One</form:option>
                     <form:option value="ROLE_USER">User</form:option>
                     <form:option value="ROLE_ADMIN">Admin</form:option>          
                   </form:select>                   
                </div>
                <div class="col-6">
                    <form:errors path="role" cssClass="alert alert-danger p-0" />  
                </div>
            </div>
	       <div class="form-group row">
              <label class="col-2 col-form-label form-control-label"></label>
              <div class="col-9">
                  <sec:authorize access="hasRole('ADMIN')"> 
   					 <input type="button" id="back"  data-id="adminlanding" class="btn btn-secondary" value="Cancel"> 
   	               </sec:authorize>
				    <sec:authorize access="hasRole('USER')">  
   					 <input type="button" id="back"  data-id="userlanding" class="btn btn-secondary" value="Cancel"> 
   	               </sec:authorize>
                  <input type="reset" class="btn btn-info" value="Reset">
                  <input type="submit" id="submit" class="btn btn-info" value="Save Changes">
              </div>
	        </div>
	        <div class="form-group row">
              <label class="col-2 col-form-label form-control-label"></label>
              <div class="col-9 alert">
                   ${userDTO.message}                 
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

  $('#submit').on('click', function (e) {
 	//alert("going to submit");
 	//document.forms[0].submit();
 	//$("#settingsForm").submit();
  }); 
 
   
  </script>
  
</body>

</html>
