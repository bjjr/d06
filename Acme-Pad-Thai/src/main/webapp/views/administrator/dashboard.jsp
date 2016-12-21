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

<ul>
	<jstl:forEach var="entry" items="${queries}">
		<li><h2>
				<spring:message code="${entry.key }" />:
			</h2>
			<h3>
				<jstl:out value="${entry.value}" />
			</h3></li>
	</jstl:forEach>
</ul>

<h2><spring:message code="administrator.CbnC" /></h2>

<display:table pagesize="5" class="displaytag" name="compByCampaigns"
requestURI="administrator/dashboard.do" id="cbc">

	<spring:message code="sponsor.company" var="text" />
	<display:column title="${text}">
		<jstl:out value="${cbc}" />
	</display:column>

</display:table>

<h2><spring:message code="administrator.CbnB" /></h2>

<display:table pagesize="5" class="displaytag" name="compByBills"
requestURI="administrator/dashboard.do" id="cbb">

	<spring:message code="sponsor.company" var="text" />
	<display:column title="${text}">
		<jstl:out value="${cbb}" />
	</display:column>

</display:table>

<h2><spring:message code="administrator.inacS" /></h2>

<display:table pagesize="5" class="displaytag" name="inactiveSponsors"
requestURI="administrator/dashboard.do" id="sp">

	<spring:message code="sponsor.name" var="text" />
	<display:column title="${text}">
		<jstl:out value="${sp}" />
	</display:column>

</display:table>

<h2><spring:message code="administrator.CsltA" /></h2>

<display:table pagesize="5" class="displaytag" name="compSpentLess"
requestURI="administrator/dashboard.do" id="csl">

	<spring:message code="sponsor.company" var="text" />
	<display:column title="${text}">
		<jstl:out value="${csl}" />
	</display:column>

</display:table>

<h2><spring:message code="administrator.CsalN" /></h2>

<display:table pagesize="5" class="displaytag" name="compSpentLeast"
requestURI="administrator/dashboard.do" id="csl90">

	<spring:message code="sponsor.company" var="text" />
	<display:column title="${text}">
		<jstl:out value="${csl90}" />
	</display:column>

</display:table>

<h2><spring:message code="administrator.CkbnProMC" /></h2>

<display:table pagesize="5" class="displaytag" name="cooksByProMC"
requestURI="administrator/dashboard.do" id="cookByProMC">
	
	<spring:message code="cook.name" var="name" />
	<spring:message code="cook.surname" var="surname" />
	<display:column property="name" title="${name}" />
	<display:column property="surname" title="${surname}" />

</display:table>