<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="spamWord/administrator/edit.do" modelAttribute="spamWord">

	<form:hidden path="id" />
	<form:hidden path="version" />

	<form:label path="word"><spring:message code="spamWord.word" /></form:label>
	<form:input path="word" />
	<form:errors cssClass="error" path="word" />
	<br />
	<br />

	<!-- Buttons -->
	
	<input type="submit" name="save"
		value="<spring:message code="spamWord.save" />" />&nbsp; 
		
	<input type="button" name="cancel"
		value="<spring:message code="spamWord.cancel" />"
		onclick="window.location='spamWord/administrator/list.do'" />
	<br />

</form:form>