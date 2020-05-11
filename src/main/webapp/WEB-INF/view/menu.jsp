<!-- Navigation -->
  <%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>  
  
  <!--  ADMIN READ ONLY ACCESS --> 
  <sec:authorize access="hasRole('ADMIN_READ_ONLY')">
	   <jsp:include page="menu/adminReadOnlyMenu.jsp"/> 
	   <input id="isEnable" type="hidden" value="false"/>   
  </sec:authorize>
  
  <!--  ADMIN ACCESS --> 
  <sec:authorize access="hasRole('ADMIN')">
	   <jsp:include page="menu/adminMenu.jsp"/> 
	   <input id="isEnable" type="hidden" value="true"/>   
  </sec:authorize>
  
  <!--  USER READ ONLY ACCESS --> 
  <sec:authorize access="hasRole('USER_READ_ONLY')">
	   <jsp:include page="menu/userReadOnlyMenu.jsp"/> 
	   <input id="isEnable" type="hidden" value="false"/>   
  </sec:authorize>
  
  <!--  USER ACCESS --> 
  <sec:authorize access="hasRole('USER')">
	   <jsp:include page="menu/userMenu.jsp"/>
	   <input id="isEnable" type="hidden" value="true"/>    
  </sec:authorize>
  
  
  
  