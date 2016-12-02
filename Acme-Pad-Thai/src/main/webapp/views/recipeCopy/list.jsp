<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table name="recipeCopies" id="row"
	requestURI="recipeCopy/list.do" pagesize="5" class="displaytag">

	<spring:message code="recipeCopy.ticker" var="ticker" />
	<display:column property="ticker" title="${ticker}" sortable="true">
		<jstl:out value="${row.ticker}" />
	</display:column>


	<spring:message code="recipeCopy.title" var="title" />
	<display:column property="title" title="${title}" sortable="true">
		<jstl:out value="${row.title}" />
	</display:column>

	<spring:message code="recipeCopy.summary" var="summary" />
	<display:column property="summary" title="${summary}" sortable="true">
		<jstl:out value="${row.summary}" />
	</display:column>

	<spring:message code="recipeCopy.nameUser" var="nameUser" />
	<display:column property="nameUser" title="${nameUser}" sortable="true">
		<jstl:out value="${row.nameUser}" />
	</display:column>

	<spring:message code="recipeCopy.winner" var="winner" />
	<display:column property="winner" title="${winner}" sortable="true">
		<jstl:catch>
			<jstl:when test="${row.winner== true}">
				<spring:message code="campaign.trueStar" />
			</jstl:when>
			<jstl:otherwise>
				<spring:message code="campaign.falseStar" />
			</jstl:otherwise>
		</jstl:catch>
	</display:column>

</display:table>