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

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="recipe" requestURI="${requestURI}" id="row">


	<spring:message code="recipe.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}"
		sortable="false" />
	<br />

	<spring:message code="recipe.ticker" var="tickerHeader" />
	<display:column property="ticker" title="${tickerHeader}"
		sortable="false" />
	<br />

	<spring:message code="recipe.user.name" var="nameUserHeader" />
	<display:column property="user.name" title="${nameUserHeader}"
		sortable="true"></display:column>
	<br />

	<spring:message code="recipe.summary" var="summaryHeader" />
	<display:column property="summary" title="${summaryHeader}"
		sortable="false" />
	<br />

	<spring:message code="recipe.momentAuthored" var="momentAuthoredHeader" />
	<display:column property="momentAuthored"
		title="${momentAuthoredHeader}" sortable="false" />
	<br />

	<spring:message code="recipe.momentLastUpdated"
		var="momentLastUpdatedHeader" />
	<display:column property="momentLastUpdated"
		title="${momentLastUpdatedAddressHeader}" sortable="false" />
	<br />

	<spring:message code="recipe.pictures" var="picturesHeader" />
	<display:column title="${picturesHeader}"
		sortable="false">
		<jstl:forEach var="picture" items=${row.pictures }>
			<img src="${picture}" />
			<br />
		</jstl:forEach>
	</display:column>
	<br />

	<spring:message code="recipe.hints" var="hintsHeader" />
	<display:column title="${hintsHeader}" sortable="false">
		<jstl:forEach var="hint" items=${row.hints }>
			<jstl:out value="${hint }"></jstl:out>
			<br />
		</jstl:forEach>
	</display:column>
	<br />
	
	<spring:message code="recipe.likesSA" var="likesSAHeader" />
	<display:column>
		<jstl:out value="${likesSA}"></jstl:out>
	</display:column>
	<br />
	
	<spring:message code="recipe.dislikesSA" var="dislikesSAHeader" />
	<display:column>
		<jstl:out value="${dislikesSA}"></jstl:out>
	</display:column>
	<br />
	
	<spring:message code="recipe.categories" var="categoriesHeader" />
	<display:column title="${categoriesHeader}" sortable="false">
		<jstl:forEach var="category" items=${row.categories }>
			<jstl:out value="${category.name }"></jstl:out>
			<br />
		</jstl:forEach>
	</display:column>
	<br />
	
</display:table>

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="quantities" requestURI="${requestURI}" id="row">
	
	<spring:message code="ingredient.name" var="nameIngredientHeader" />
	<display:column property="ingredient.name" title="${nameIngredientHeader}"
		sortable="false" />
	<br />
	
	<spring:message code="quantity.quantity" var="quantityHeader" />
	<display:column property="quantity" title="${quantityHeader}"
		sortable="false" />
	<br />
	
	<spring:message code="quantity.unit" var="unitHeader" />
	<display:column property="unit.unit" title="${unitHeader}"
		sortable="false" />
	<br />
	
</display:table>

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="steps" requestURI="${requestURI}" id="row">
	
	<spring:message code="step.description" var="descriptionHeader" />
	<display:column property="description" title="${descriptionHeader}"
		sortable="false" />
	<br />
	
	<spring:message code="step.picture" var="stepPictureHeader" />
	<display:column property="picture" title="${stepPictureHeader}"
		sortable="false" >
		<img src="${picture}" />
	</display:column>
	<br />
	
	<spring:message code="step.hints" var="stepHintsHeader" />
	<display:column title="${stepHintsHeader}" sortable="false">
		<jstl:forEach var="hint" items=${row.hints }>
			<jstl:out value="${hint }"></jstl:out>
			<br />
		</jstl:forEach>
	</display:column>
	<br />
	
</display:table>

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="comments" requestURI="${requestURI}" id="row">
	
	<spring:message code="comment.title" var="commentTitleHeader" />
	<display:column property="title" title="${commentTitleHeader}"
		sortable="false" />
	<br />
	
	<spring:message code="comment.text" var="textHeader" />
	<display:column property="text" title="${textHeader}"
		sortable="false" />
	<br />
	
	<spring:message code="comment.stars" var="starsHeader" />
	<display:column property="stars" title="${starsHeader}"
		sortable="false" />
	<br />
	
	<spring:message code="comment.identity" var="identityHeader" />
	<display:column property="identity" title="${identityHeader}"
		sortable="false" />
	<br />
	
	<spring:message code="comment.moment" var="momentHeader" />
	<display:column property="moment" title="${momentHeader}"
		sortable="false" />
	<br />
	
</display:table>

<security:authorize access="hasRole('USER')">
	<form:form action="" method="POST">
	<spring:message code="recipe.contest"></spring:message>
	<select id="contest" name="contest">
		<form:options items="${contests}" itemValue="id" itemLabel="title" />
	</select>
		<input type="submit" name="qualify" value="<spring:message code="recipe.qualify" />" 
		onclick="return confirm('<spring:message code="recipe.confirm.qualify" />')"/>&nbsp;
	</form:form>
</security:authorize>