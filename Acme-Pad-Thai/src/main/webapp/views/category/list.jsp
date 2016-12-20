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
	name="categories" requestURI="${requestURI}" id="row">
	<!-- Attributes -->
	<spring:message code="category.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="true" />
	
	<spring:message code="category.description" var="descriptionHeader" />
	<display:column property="description" title="${descriptionHeader}" sortable="true" />
	
	<spring:message code="category.picture" var="pictureHeader" />
	<display:column property="picture" title="${pictureHeader}" sortable="true" />
	
	<spring:message code="category.tags" var="tagsHeader" />
	<display:column property="tags" title="${tagsHeader}" sortable="true" />
	
	<spring:message code="category.category" var="rootHeader" />
	<display:column property="root.name" title="${rootHeader}" sortable="true" />
	
	<spring:message code="category.subcategories" var="subcategoriesHeader" />
	<display:column title="${subcategoriesHeader}">
		<jstl:forEach items="${row.subcategories }" var="subcategory">
			<jstl:out value="${subcategory.name }"></jstl:out>
			<br />
		</jstl:forEach>
	</display:column>
	
	<!-- Action links -->
	<security:authorize access="hasRole('ADMINISTRATOR')">
		<display:column>
			<a href="category/administrator/edit.do?categoryId=${row.id}"><spring:message code="category.edit"/></a>
		</display:column>
	</security:authorize>
	
</display:table>

<!-- Action links -->
	<security:authorize access="hasRole('ADMINISTRATOR')">
			<a href="category/administrator/create.do"><spring:message code="category.create"/></a>
	</security:authorize>