<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

	<spring:message code="message.foldersToMove" />
	<br />
	
	<display:table pagesize="5" class="displaytag"
	name="folders" requestURI="message/selectFolder.do?messageId=${messageId}&actualFolderId=${actualFolderId}" id="row">
	
	<display:column>
		<a href="message/move.do?messageId=${messageId}&actualFolderId=${actualFolderId}&folderId=${row.id}"
		onclick="return confirm('<spring:message code="message.confirm.move" />')">${row.name}</a>
	</display:column>

	</display:table>
	
	<!-- Buttons -->
	
	<input type="button" name="cancel"
		value="<spring:message code="message.cancel" />"
		onclick="window.location='message/listByFolder.do?folderId=${actualFolderId}'" />
	<br />