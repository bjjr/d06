<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<!-- Listing grid -->
<display:table pagesize="5" class="displaytag" name="categories"
	requestURI="${requestURI}" id="row">
	<!-- Attributes -->
	<spring:message code="category.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="true" />

	<spring:message code="category.description" var="descriptionHeader" />
	<display:column property="description" title="${descriptionHeader}"
		sortable="false" />

	<!-- Action links -->
	<display:column>
		<a href="recipe/user/remove.do?recipeId=${recipe.id}&categoryId=${row.id}"><spring:message
				code="category.remove" /></a>
	</display:column>

</display:table>