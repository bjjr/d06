<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table name="learningMaterials" id="learningMaterial"
  requestURI="${requestURI}" pagesize="5"
  class="displaytag">

	<spring:message code="learningMaterial.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" />
	
	<spring:message code="learningMaterial.abstractText" var="abstractHeader" />
	<display:column property="abstractText" title="${abstractHeader}" />
	
	<spring:message code="learningMaterial.attachments" var="attachmentHeader" />
	<display:column title="${attachmentHeader}">
		<jstl:choose>
			<jstl:when test="${empty learningMaterial.attachments}">
				<spring:message code="learningMaterial.empty" var="emptyText" />
				<jstl:out value="${emptyText}" />
			</jstl:when>
			
			<jstl:otherwise>
				<jstl:forEach var="url" items="learningMaterial.attachments">
					<a href="${url}">
						<jstl:out value="${url}" />
					</a>
				</jstl:forEach>
			</jstl:otherwise>
		</jstl:choose>
	</display:column>
	
	<display:column>
			<jstl:choose>
				<jstl:when test="${learningMaterial['class'].name == 'domain.Text'}">
					<a href="text/actor/display.do?masterClassId=${masterClassId}&textId=${learningMaterial.id}">
						<spring:message code="learningMaterial.display.text" var="displayText" />
						<jstl:out value="${displayText}" />
					</a>
				</jstl:when>
				
				<jstl:when test="${learningMaterial['class'].name == 'domain.Presentation'}">
					<a href="${learningMaterial.path}">
						<spring:message code="learningMaterial.display.presentation" var="displayText" />
						<jstl:out value="${displayText}" />
					</a>
				</jstl:when>
				
				<jstl:when test="${learningMaterial['class'].name == 'domain.Video'}">
					<a href="${learningMaterial.identifier}">
						<spring:message code="learningMaterial.display.video" var="displayText" />
						<jstl:out value="${displayText}" />
					</a>
				</jstl:when>
				
				<jstl:otherwise></jstl:otherwise>
				
			</jstl:choose>
		</display:column>	
</display:table>
