<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table name="bills" id="row"
	requestURI="bill/sponsor/list.do" pagesize="5" class="displaytag">

	<spring:message code="bill.creation" var="creation" />
	<display:column property="creation" title="${creation}" sortable="true">
		<jstl:out value="${row.creationMoment}" />
	</display:column>

	<spring:message code="bill.paidM" var="paidM" />
	<display:column property="paidM" title="${paidM}" sortable="true">
		<jstl:out value="${row.paidMoment}" />
	</display:column>

	<spring:message code="bill.description" var="description" />
	<display:column property="description" title="${description}"
		sortable="true">
		<jstl:out value="${row.description}" />
	</display:column>

	<spring:message code="bill.campaign" var="campaign" />
	<display:column property="campaign" title="${campaign}"
		sortable="true">
		<jstl:out value="${row.campaign}" />
	</display:column>

</display:table>