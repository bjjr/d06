<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p><spring:message code="endorser.listByCurriculum.title" /></p>

<!-- Listing grid -->

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="endorsers" requestURI="${requestURI}" id="row">
	
	<!-- Attributes -->

	<spring:message code="endorser.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="true" />

	<spring:message code="endorser.homepage" var="homepageHeader" />
	<display:column property="homepage" title="${homepageHeader}" sortable="false" />
	
</display:table>

<br />
<input type="button" name="return"
		value="<spring:message code="endorser.return" />"
		onclick="javascript: relativeRedir('curriculum/nutritionist/list.do');" />
<br />
