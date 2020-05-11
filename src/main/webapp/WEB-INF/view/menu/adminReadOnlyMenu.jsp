<!-- Navigation -->
  <%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>  
  <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
  
  <c:set var="url" value='<%= request.getAttribute("javax.servlet.forward.servlet_path")%>'> </c:set>
 
 <nav class="navbar navbar-expand-lg navbar-dark bg-info static-top">
        <div class="container">
       		<a class="navbar-brand" href="/adminlanding">Spring Boot Account Information Services for Admin</a> 
       		<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarResponsive" 
       					aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>  
    <div class="collapse navbar-collapse" id="navbarResponsive">
        <ul class="navbar-nav ml-auto">        
            
               <li class="nav-item ${(not empty url && url =='/search') ? 'active': ''}">
         		<a class="nav-link" href="/search">Search</a>
              </li>
            
                <li class="nav-item ${(not empty url && url =='/admin/search') ? 'active': ''}">
      				<a class="nav-link" href="/admin/search">Search Users</a>
          		</li>
                           
		        <li class="nav-item dropdown">
				   <a class="nav-link dropdown-toggle" href="#" id="navbardrop" data-toggle="dropdown">
				     <sec:authentication property="principal"/> -- My Account
				   </a>
				   <div class="dropdown-menu">			
				      <a class="dropdown-item" href="/logout">Logout</a>
				   </div>
				</li>
        </ul>
      </div>
        </div>
    </nav> 
 