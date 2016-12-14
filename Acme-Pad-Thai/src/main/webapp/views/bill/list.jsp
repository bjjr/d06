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
	<display:column property="creationMoment" title="${creation}" sortable="true">
		<jstl:out value="${row.creationMoment}" />
	</display:column>

	<spring:message code="bill.paidM" var="paidM" />
	<display:column property="paidMoment" title="${paidM}" sortable="true">
		<jstl:out value="${row.paidMoment}" />
	</display:column>

	<spring:message code="bill.description" var="description" />
	<display:column property="description" title="${description}"
		sortable="true">
		<jstl:out value="${row.description}" />
	</display:column>
<spring:message code="bill.paid" var="paid" />
		<display:column title="${paid}">
<jstl:if test="${row.paidMoment ==null }">
			<a href="bill/sponsor/paid.do?billId=${row.id}">${paid}</a>
</jstl:if>
		</display:column>
</display:table>