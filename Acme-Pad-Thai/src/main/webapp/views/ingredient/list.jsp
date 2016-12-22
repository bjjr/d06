<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<!-- Listing grid -->
<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="ingredients" requestURI="${requestURI}" id="row">
	<!-- Attributes -->
	<spring:message code="ingredient.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="true" />
	
	<spring:message code="ingredient.description" var="descriptionHeader" />
	<display:column property="description" title="${descriptionHeader}" sortable="false" />
	
	<spring:message code="ingredient.picture" var="pictureHeader" />
	<display:column title="${pictureHeader}" sortable="false">
		<img src="${row.picture}" width="25%" />
	</display:column>
	
	<spring:message code="ingredient.properties" var="propertiesHeader" />
	<display:column title="${propertiesHeader}">
		<jstl:forEach items="${row.properties }" var="property">
			<jstl:out value="${property.name }"></jstl:out>
			<br />
		</jstl:forEach>
	</display:column>
	
	<!-- Action links -->
	<security:authorize access="hasRole('NUTRITIONIST')">
		<display:column>
			<a href="ingredient/nutritionist/edit.do?ingredientId=${row.id}"><spring:message code="ingredient.edit"/></a>
		</display:column>
	</security:authorize>
	
		<security:authorize access="hasRole('NUTRITIONIST')">
		<display:column>
			<a href="ingredient/nutritionist/addProperty.do?ingredientId=${row.id}"><spring:message code="ingredient.add.property"/></a>
		</display:column>
	</security:authorize>
	
	<display:column>
			<a href="ingredient/nutritionist/removeProperty.do?ingredientId=${row.id}"><spring:message code="property.remove"/></a>
	</display:column>
	
</display:table>

<!-- Action links -->
	<security:authorize access="hasRole('NUTRITIONIST')">
			<a href="ingredient/nutritionist/create.do"><spring:message code="ingredient.create"/></a>
	</security:authorize>