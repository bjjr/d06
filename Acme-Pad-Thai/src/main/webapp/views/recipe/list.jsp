<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<!-- Search Form -->
<form:form action="recipe/search.do" modelAttribute="recipe">
	<form:input path="keyword"/>
	<input type="submit" name="search" value="<spring:message code="recipe.search"/>"/>
</form:form>
	
<!-- Listing grid -->
<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="recipes" requestURI="${requestURI}" id="row">
	<!-- Attributes -->
	
	<spring:message code="recipe.user.name" var="nameHeader" />
	<display:column property="user.name" title="${nameHeader}" sortable="true"></display:column>
	
	<spring:message code="recipe.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" sortable="false" />
	
	<spring:message code="recipe.ticker" var="tickerHeader" />
	<display:column property="ticker" title="${tickerHeader}" sortable="false" />
	
	<!-- Action links -->
	<display:column>
			<a href="recipe/display.do?recipeId=${row.id}">
				<spring:message	code="recipe.display" />
			</a>
	</display:column>
			
</display:table>