<!DOCTYPE html>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">
  <title>Account</title>
  <!-- Bootstrap core CSS -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/webjars/bootstrap/4.3.1/css/bootstrap.min.css"/>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/webjars/datatables/1.10.20/css/jquery.dataTables.min.css"/>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"/>
</head>

<body>
  <!-- Navigation -->
   <jsp:include page="menu.jsp"/>
   

  <!-- Page Content -->
  <div class="container">
    <div class="card card-outline-secondary">
      <div class="card-header">
        <h3 class="mb-0">User Search</h3>
       </div>
       <div class="card-body">
           <form:form class="form" role="form" modelAttribute="userDTO" action="search" method="post" autocomplete="off">
             <div class="form-group row">
                  <div class="col-2">
                      <label class="col-form-label form-control-label">User Name</label>
                      <form:input class="form-control" path="username"/>
                  </div>
                  <div class="col-2">
                      <label class="col-form-label form-control-label">Auth Type</label>                                         
                      <form:select path="authType" class="form-control" >
                         <form:option value="">Select One</form:option>
                         <form:option value="DB">DB</form:option>                         
                      </form:select>
                 </div>
                  <div class="col-2">
                       <label class="col-form-label form-control-label">Connection Type</label>
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
                  <div class="col-2">
                       <label class="col-form-label form-control-label">Role</label>
                       <form:select path="role" class="form-control" >
                        <form:option value="">Select One</form:option>
                        <form:option value="ROLE_USER">User</form:option>
                        <form:option value="ROLE_ADMIN">Admin</form:option>
                      </form:select>
                  </div>
                  <div class="col-2" style="display:flex;">
                        <input type="submit" id="search" class="btn btn-info btn-sm mt-auto" value="Search">&nbsp;&nbsp;&nbsp;
                        <input type="reset" id="reset" class="btn btn-info btn-sm mt-auto" value="Reset">
                  </div>
                  <div class="col-2">
                  </div>
              </div>
            </form:form>
          </div>
       </div>
       <hr/>
       <div class="row">
         <div class="col-12">
           <table id="example" class="display" style="width:100%">
             <thead>
                 <tr>
                     <th>ID</th>
                     <th>Username</th>
                     <th>Last Login</th>
                     <th>Auth Type</th>
                     <th>Auth Encrypted</th>
                     <th>Connection Type</th>
                     <th>PK Generation Type</th>
                     <th>Role</th>                    
                     <th>Actions</th>
                 </tr>
             </thead>
             <tbody>
                 <c:forEach  items="${userList}" var ="user">
	                <tr>
	                   <td>${user.id} </td>
	                   <td>${user.username} </td>
	                    <td>${user.lastLogin} </td>
	                   <td>${user.authType} </td>
	                   <td>${user.authEncrypted} </td>
	                   <td>${user.connectionType} </td>
	                   <td>${user.primaryKeyGenerationType} </td>
	                   <td>${user.role} </td>	                   
					   <td>Hello</td>
				   </tr>
                 </c:forEach>
                
             </tbody>
          </table>
       </div>
    </div>
    <div id="overlay" style="display:none;">
       <div class="spinner"></div>
       <br/>Loading...
    </div>
</div>

<!-- Modal -->
<div class="modal fade" id="deleteModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
  aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-body">
        <label> Are you sure to delete the record ?</label>
        <input type="hidden" name="deletedId" id="deletedId" value=""/>
      </div>
      <div class="modal-footer">
        <button type="button" id="cancel" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
        <button type="button" id="deleteModelRecord" class="btn btn-info">Delete</button>
      </div>
    </div>
  </div>
</div>
<!--Success Modal -->
<div class="modal fade" id="successDeleteModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
  aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-body">
        <label> Successfully Deleted Record<br/> It will redirect to the  search page</label>
      </div>
      <div class="modal-footer">
        <button type="button" id="SuccessReload" class="btn btn-secondary" data-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>
<!--Failure Modal -->
<div class="modal fade" id="failureDeleteModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
  aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-body">
        <label> Failed to Delete Record<br/> It will redirect to the  search page</label>
      </div>
      <div class="modal-footer">
        <button type="button" id="FailureReload" class="btn btn-secondary" data-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>
  <!-- Bootstrap core JavaScript -->
  <script type="text/javascript" src="${pageContext.request.contextPath}/webjars/jquery/3.4.1/jquery.min.js"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/webjars/bootstrap/4.3.1/js/bootstrap.min.js"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/webjars/datatables/1.10.20/js/jquery.dataTables.min.js"></script>
  
  <script type="text/javascript">

   var isEnableButtons = $("#isEnable").val();
   
   $('#example').DataTable({
       "aaSorting": [],        
       "stripeClasses": [ 'firstrow', 'secondrow' ],
       "columns" : [
    	   {"data" : "id"},
           {"data" : "username"},
           {"data" : "lastLogin"},           
           {"data" : "authType"},
           {"data" : "authEncrypted"},
           {"data" : "connectionType"},
           {"data" : "primaryKeyGenerationType"},
           {"data" : "role"},        
           {
              data: null,
              className: "center",
              fnCreatedCell: function (nTd, sData, oData, iRow, iCol) { 
                  var editButton   = "<a class='editLink' href='/admin/edit?id="+oData.id+"'>EDIT</a>" ;
                  var viewButton   = "<a class='editLink' href='/admin/view?id="+oData.id+"'>VIEW</a> ";
                  var deleteButton = "<a class='editLink' href='javascript:deletefn("+oData.id+")'>DELETE</a> ";
					if(isEnableButtons == 'true'){
						dataHTML = editButton + " / " + viewButton + " / " + deleteButton;
					}else{
						dataHTML =  viewButton;
					}             
                  $(nTd).html(dataHTML);
                },
          }
         ],
    });

   
    function deletefn (value) {
      $("#deletedId").val(value);
      $('#deleteModal').modal('show');
    }

    $('#deleteModelRecord').on('click', function (e) {    	
       $('#deleteModal').modal('hide');
       accountId = $("#deletedId").val();
       callAjaxToDeleteAccount(accountId);
    });
    
    function callAjaxToDeleteAccount(userId) {
    	$('#overlay').fadeIn();
    	 $.ajax({
             type: "DELETE",             
             url: "/admin/delete?id="+userId,             
             cache: false,
             timeout: 600000,
             success: function (data) {
                 if(data == "Success" || data == "Failed") {
                	 $('#overlay').fadeOut();          	              
                     $('#successDeleteModal').modal('show');
                 }else{
                	 alert(data);   
                 }            	 
             },
             error: function (e) {
                 $('#overlay').fadeOut();
                 $('#failureDeleteModal').modal('show');
                 alert(e);
             }
         });
     }
    
    $('#FailureReload').on('click', function (e) {
    	 window.location.href = '/admin/search';
    });
    
    $('#SuccessReload').on('click', function (e) {
   	 	 window.location.href = '/admin/search';
   });
    
   </script>
</body>

</html>
