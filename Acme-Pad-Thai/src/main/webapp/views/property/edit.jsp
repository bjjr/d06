<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="property/nutritionist/edit.do" modelAttribute="property">
	
	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="ingredients"/>
	
	<form:label path="name">
		<spring:message code="property.name"/>
	</form:label>
	<form:input path="name"/>
	<form:errors cssClass="error" path="name"/>
	<br />
		
	<!-- Action buttons -->
	<input type="submit" name="save"
		value="<spring:message code="property.save"/>"/>&nbsp;
	<input type="submit" name="delete" 
		value="<spring:message code="property.delete" />" 
		onclick="return confirm('<spring:message code="property.confirm.delete" />')" />&nbsp;
	<input type="button" name="cancel"
		value="<spring:message code="property.cancel" />"
		onclick="window.location='property/nutritionist/list.do'" />
	<br />
		
</form:form>