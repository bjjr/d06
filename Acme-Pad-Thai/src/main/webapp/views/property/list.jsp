<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<!-- Listing grid -->
<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="properties" requestURI="${requestURI}" id="row">
	<!-- Attributes -->
	<spring:message code="property.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="true" />
	
	<!-- Action links -->
	<security:authorize access="hasRole('NUTRITIONIST')">
		<display:column>
			<a href="property/nutritionist/edit.do?propertyId=${row.id}"><spring:message code="property.edit"/></a>
		</display:column>
	</security:authorize>
	
</display:table>

<!-- Action links -->
	<security:authorize access="hasRole('NUTRITIONIST')">
			<a href="property/nutritionist/create.do"><spring:message code="property.create"/></a>
	</security:authorize>