<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<!-- Listing grid -->

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="nutritionists" requestURI="${requestURI}" id="row">
	
	<!-- Attributes -->

	<spring:message code="nutritionist.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="true" />
	
	<spring:message code="nutritionist.surname" var="surnameHeader" />
	<display:column property="surname" title="${surnameHeader}" sortable="false" />
	
	<spring:message code="nutritionist.email" var="emailHeader" />
	<display:column property="email" title="${emailHeader}" sortable="true" />
	
	<jstl:if test="${socialActor == true}">
		<display:column>
			<a href="socialActor/follow.do?socialActorId=${row.id}"><spring:message code="nutritionist.follow" /></a>
		</display:column>
		
		<display:column>
			<a href="socialActor/unfollow.do?socialActortId=${row.id}"><spring:message code="nutritionist.unfollow" /></a>
		</display:column>
		
	</jstl:if>
		
</display:table>	
