<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<!-- Listing grid -->

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="socialActors" requestURI="${requestURI}" id="row">

	<!-- Attributes -->

	<spring:message code="socialActor.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="true" />

	<spring:message code="socialActor.surname" var="surnameHeader" />
	<display:column property="surname" title="${surnameHeader}"
		sortable="false" />

	<spring:message code="socialActor.email" var="emailHeader" />
	<display:column property="email" title="${emailHeader}" sortable="true" />

	<display:column>
	<jstl:choose>
	<jstl:when test="${follows.contains(row)}">
		<a href="socialActor/unfollow.do?socialActorId=${row.id}"><spring:message code="socialActor.unfollow"/></a>
	</jstl:when>
	<jstl:otherwise>
		<a href="socialActor/follow.do?socialActorId=${row.id}"><spring:message code="socialActor.follow"/></a>
	</jstl:otherwise>
	</jstl:choose>
	</display:column>

</display:table>
