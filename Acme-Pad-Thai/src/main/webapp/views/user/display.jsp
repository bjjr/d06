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

<h2><spring:message code="user.information"/></h2>
<br />
<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="user" requestURI="${requestURI}" id="row">
	<!-- Attributes -->
	<spring:message code="user.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="true" />
	
	<spring:message code="user.surname" var="surnameHeader" />
	<display:column property="surname" title="${surnameHeader}" sortable="false" />

	<spring:message code="user.email" var="emailHeader" />
	<display:column property="email" title="${emailHeader}" sortable="false" />

	<spring:message code="user.phone" var="phoneHeader" />
	<display:column property="phone" title="${phoneHeader}" sortable="false" />
	
	<spring:message code="user.postalAddress" var="postalAddressHeader" />
	<display:column property="postalAddress" title="${postalAddressHeader}" sortable="false" />
</display:table>
	
	<h2><spring:message code="user.socialIdentities"/></h2>
	<br />
	<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="socialIdentities" requestURI="${requestURI}" id="row">
	
	<spring:message code="socialIdentity.nick" var="nickHeader" />
	<display:column property="nick" title="${nickHeader}"
		sortable="false" />
	
	<spring:message code="socialIdentity.nameSN" var="nameSNHeader" />
	<display:column property="nameSN" title="${nameSNHeader}"
		sortable="false" />

	<spring:message code="socialIdentity.link" var="linkHeader" />
	<display:column property="link" title="${linkHeader}"
		sortable="false" />
		
	<spring:message code="socialIdentity.picture" var="pictureHeader" />
	<display:column property="picture" title="${linkHeader}"
		sortable="false" />
	
</display:table>

