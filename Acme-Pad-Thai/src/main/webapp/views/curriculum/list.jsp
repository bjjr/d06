<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p><spring:message code="curriculum.list.title" /></p>

<!-- Listing grid -->

<display:table pagesize="1" class="displaytag" keepStatus="true"
	name="curriculum" requestURI="${requestURI}" id="row">
	
	<!-- Attributes -->

	<spring:message code="curriculum.photo" var="photoHeader" />
	<display:column property="photo" title="${photoHeader}" sortable="false" />

	<spring:message code="curriculum.educationSection" var="educationSectionHeader" />
	<display:column property="educationSection" title="${educationSectionHeader}" sortable="false" />
	
	<spring:message code="curriculum.experienceSection" var="experienceSectionHeader" />
	<display:column property="experienceSection" title="${experienceSectionHeader}" sortable="false" />
	
	<spring:message code="curriculum.hobbiesSection" var="hobbiesSectionHeader" />
	<display:column property="hobbiesSection" title="${hobbiesSectionHeader}" sortable="false" />
	
	<spring:message code="curriculum.endorsers" var="endorsers" />
	<display:column>
		<a href="endorser/nutritionist/listByCurriculum.do?curriculumId=${row.id}">
			<spring:message code="curriculum.endorsers" var="endorsers" />
		</a>
	</display:column>
	
	<display:column>
		<a href="curriculum/nutritionist/edit.do?curriculumId=${row.id}">
			<spring:message	code="curriculum.edit" />
		</a>
	</display:column>
	
</display:table>

<jstl:if test="${row.id == 0} || ${row.id == null}">
	<input type="submit" name="addCurriculum"
		value="<spring:message code="curriculum.addCurriculum" />" 
		onclick="javascript: relativeRedir('curriculum/nutritionist/create.do');" />&nbsp;
</jstl:if>
