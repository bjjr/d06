<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<!-- Listing grid -->

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="curriculum" requestURI="${requestURI}" id="row">
	
	<!-- Attributes -->

	<spring:message code="curriculum.photo" var="photoHeader" />
	<display:column title="${photoHeader}" sortable="false">
		<img src="${row.photo}" width="25%" />
	</display:column>

	<spring:message code="curriculum.educationSection" var="educationSectionHeader" />
	<display:column property="educationSection" title="${educationSectionHeader}" sortable="false" />
	
	<spring:message code="curriculum.experienceSection" var="experienceSectionHeader" />
	<display:column property="experienceSection" title="${experienceSectionHeader}" sortable="false" />
	
	<spring:message code="curriculum.hobbiesSection" var="hobbiesSectionHeader" />
	<display:column property="hobbiesSection" title="${hobbiesSectionHeader}" sortable="false" />
	
	<spring:message code="curriculum.endorsers" var="endorsersHeader" />
	<display:column title="${endorsersHeader}" sortable="false">
		<a href="endorser/nutritionist/listByCurriculum.do?curriculumId=${row.id}">
			<spring:message code="curriculum.endorsers"/>
		</a>
	</display:column>
	
	<display:column>
		<a href="curriculum/nutritionist/edit.do?curriculumId=${row.id}">
			<spring:message	code="curriculum.edit" />
		</a>
	</display:column>
	
</display:table>

<jstl:if test="${curriculum == null}">
	<br />
	<br />
	<input type="button" name="addCurriculum"
		value="<spring:message code="curriculum.addCurriculum" />" 
		onclick="window.location='curriculum/nutritionist/create.do'" />&nbsp;
		
</jstl:if>
