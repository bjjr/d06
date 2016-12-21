<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<!-- Listing grid -->

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="folders" requestURI="${requestURI}" id="row">
	
	<!-- Attributes -->

	<spring:message code="folder.name" var="nameHeader" />
	<display:column title="${nameHeader}" sortable="true">
		<a href="message/listByFolder.do?folderId=${row.id}">${row.name}</a>
	</display:column>
	
	<display:column>
		<jstl:if test="${row.obligatory == false}">
			<a href="folder/edit.do?folderId=${row.id}">
				<spring:message	code="folder.edit" />
			</a>
		</jstl:if>
	</display:column>
	
</display:table>
	<input type="button" name="createFolder"
		value="<spring:message code="folder.createFolder" />" 
		onclick="window.location='folder/create.do'" />&nbsp;
		
