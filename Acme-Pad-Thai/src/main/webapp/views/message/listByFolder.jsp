<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<!-- Listing grid -->

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="messages" requestURI="${requestURI}" id="row">
	
	<!-- Attributes -->

	<spring:message code="message.moment" var="momentHeader" />
	<display:column property="moment" title="${momentHeader}" sortable="false" />
	
	<spring:message code="message.subject" var="subjectHeader" />
	<display:column property="subject" title="${subjectHeader}" sortable="false" />
	
	<spring:message code="message.body" var="bodyHeader" />
	<display:column property="body" title="${bodyHeader}" sortable="false" />
	
	<spring:message code="message.priority" var="priorityHeader" />
	<display:column property="priority" title="${priorityHeader}" sortable="true" />
	
	<spring:message code="message.sender" var="senderHeader" />
	<display:column property="sender" title="${senderHeader}" sortable="true" />
	
	<spring:message code="message.recipients" var="recipientsHeader" />
	<display:column property="recipients" title="${recipientsHeader}" sortable="false" />
	<jstl:forEach var="m" items="${messages}">
		<jstl:forEach var="r" items="${m.recipients}">
			<jstl:out value="${r.name} ${r.surname}"></jstl:out>
		</jstl:forEach>
	</jstl:forEach>
	
	<display:column>
		<a href="message/move.do?messageId=${row.id}">
			<spring:message	code="message.move" />
		</a>
	</display:column>
	
	<display:column>
		<jstl:if test="${trashbox == true}">
			<input type="submit" name="delete"
				value="<spring:message code="message.delete" />"
				onclick="return confirm('<spring:message code="message.confirm.delete" />')" />&nbsp;
		</jstl:if>
		<jstl:if test="${trashbox == false}">
			<input type="submit" name="moveToTrashbox"
				value="<spring:message code="message.moveToTrashbox" />"
				onclick="return confirm('<spring:message code="message.confirm.moveToTrashbox" />')" />&nbsp;
		</jstl:if>
	</display:column>

</display:table>

	<br />
	<br />
	<input type="button" name="createMessage"
		value="<spring:message code="message.createMessage" />" 
		onclick="window.location='message/create.do'" />&nbsp;
		
