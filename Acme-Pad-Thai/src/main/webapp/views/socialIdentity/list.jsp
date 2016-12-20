<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table name="socialIdentities" id="row" requestURI="socialIdentity/list.do"
	pagesize="5" class="displaytag">

	<spring:message code="socialIdentity.nick" var="nick" />
	<display:column property="nick" title="${nick}" sortable="true">
		<jstl:out value="${row.nick}" />
	</display:column>

	<spring:message code="socialIdentity.nameSN" var="nameSN" />
	<display:column property="nameSN" title="${nameSN}"
		sortable="true">
		<jstl:out value="${row.nameSN}" />
	</display:column>

	<spring:message code="socialIdentity.link" var="link" />
	<display:column property="link" title="${link}" sortable="true">
		<jstl:out value="${row.link}" />
	</display:column>
	<spring:message code="socialIdentity.picture" var="picture" />
	<display:column title="${picture}">
		<jstl:out value="${row.picture }"/>
	</display:column>
</display:table>
