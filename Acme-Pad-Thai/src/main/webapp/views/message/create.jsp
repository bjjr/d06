<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="message/create.do" modelAttribute="message">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="moment" />
	<form:hidden path="sender" />
	<form:hidden path="priority.id"/>
	<form:hidden path="priority.version"/>

	<form:label path="subject">
		<spring:message code="message.subject" />:
	</form:label>
	<form:input path="subject" />
	<form:errors cssClass="error" path="subject" />
	<br />
	
	<form:label path="body">
		<spring:message code="message.body" />:
	</form:label>
	<form:textarea path="body" />
	<form:errors cssClass="error" path="body" />
	<br />
	
	<form:label path="recipients">
		<spring:message code="message.recipients" />:
	</form:label>
	<form:textarea path="recipients" />
	<form:errors cssClass="error" path="recipients" />
	<br />
	
	<form:select path="recipients" >
	<jstl:forEach var="i" items="${message.recipients}">
		<form:option value="${i.email}">i.email</form:option>
	</jstl:forEach>
	</form:select>
	
	<form:select path="priority.priority" >
		 <form:option value="High"><spring:message code="message.priority.High"/></form:option>
		 <form:option value="Neutral"><spring:message code="message.priority.Neutral"/></form:option>
		 <form:option value="Low"><spring:message code="message.priority.Low"/></form:option>
	</form:select>

	<!-- Buttons -->
	
	<input type="submit" name="save"
		value="<spring:message code="message.save" />" />&nbsp; 
		
	<input type="button" name="cancel"
		value="<spring:message code="message.cancel" />"
		onclick="window.location='folder/list.do'" />
	<br />

</form:form>