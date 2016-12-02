<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table name="campaigns" id="row"
	requestURI="campaign/sponsor/list.do" pagesize="5" class="displaytag">

	<spring:message code="campaign.start" var="start" />
	<display:column property="start" title="${start}" sortable="true">
		<jstl:out value="${row.startMoment}" />
	</display:column>


	<spring:message code="campaign.end" var="end" />
	<display:column property="end" title="${end}" sortable="true">
		<jstl:out value="${row.endMoment}" />
	</display:column>

	<spring:message code="campaign.banners" var="bannners" />
	<display:column property="banners" title="${banners}" sortable="true">
		<jstl:out value="${row.banners}" />
	</display:column>

	<spring:message code="campaign.maxDisplayed" var="maxDisplayed" />
	<display:column property="maxDisplayed" title="${maxDisplayed}"
		sortable="true">
		<jstl:out value="${row.maxDisplayed}" />
	</display:column>

	<spring:message code="campaign.displayed" var="displayed" />
	<display:column property="displayed" title="${displayed}"
		sortable="true">
		<jstl:out value="${row.displayed}" />
	</display:column>

	<spring:message code="campaign.star" var="star" />
	<display:column property="star" title="${star}" sortable="true">
		<jstl:catch>
			<jstl:when test="${row.star == true}">
				<spring:message code="campaign.trueStar" />
			</jstl:when>
			<jstl:otherwise>
				<spring:message code="campaign.falseStar" />
			</jstl:otherwise>
		</jstl:catch>
	</display:column>
</display:table>