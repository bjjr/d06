<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table name="contests" id="row" requestURI="contest/list.do"
	pagesize="5" class="displaytag">

	<spring:message code="contest.title" var="title" />
	<display:column property="title" title="${title}" sortable="true">
		<jstl:out value="${row.title}" />
	</display:column>

	<spring:message code="contest.open" var="open" />
	<display:column property="openingTime" title="${open}" sortable="true">
		<jstl:out value="${row.openingTime}" />
	</display:column>

	<spring:message code="contest.close" var="close" />
	<display:column property="closingTime" title="${close}" sortable="true">
		<jstl:out value="${row.closingTime}" />
	</display:column>
		<spring:message code="contest.recipes" var="recipes" />
		<display:column title="${recipes}">
			<a href="recipeCopy/list.do?contestId=${row.id}">${recipes}</a>
		</display:column>
	<security:authorize access="hasRole('ADMINISTRATOR')">
	<spring:message code="contest.edit" var="edit" />
	<display:column title="${edit}">
		<a href="contest/administrator/edit.do?contestId=${row.id}">${edit}</a>
	</display:column>
	</security:authorize>

</display:table>