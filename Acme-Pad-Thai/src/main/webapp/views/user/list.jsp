<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<!-- Search form -->	
<form:form action="" modelAttribute="user">
	<input type="text" name="user"/>
	<input type="submit" name="search" value="<spring:message code="user.search"/>"/>
</form:form>	

<!-- Listing grid -->
<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="users" requestURI="${requestURI}" id="row">
	<!-- Attributes -->
	<spring:message code="user.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="true" />
	
	<spring:message code="user.surname" var="surnameHeader" />
	<display:column property="surname" title="${surnameHeader}" sortable="false" />

	<spring:message code="user.email" var="emailHeader" />
	<display:column property="email" title="${emailHeader}" sortable="false" />

	<spring:message code="user.phone" var="phoneHeader" />
	<display:column property="phone" title="${phoneHeader}" sortable="false" />
	
	<spring:message code="user.postalAddress" var="postalAddressHeader" />
	<display:column property="postalAddress" title="${postalAddressHeader}" sortable="false" />
	
	<spring:message code="user.recipes" var="recipesHeader" />
	<display:column title="${recipesHeader}">
		<jstl:forEach items="${row.recipes }" var="rec">
			<a href="recipe/display.do?recipeId=${rec.id}">${rec.title}</a>
			<br />
		</jstl:forEach>
	</display:column> 
	
		
</display:table>