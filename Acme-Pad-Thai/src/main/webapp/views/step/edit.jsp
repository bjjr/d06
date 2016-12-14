<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="step/user/edit.do" modelAttribute="step">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="user" />
	<div>
		<spring:message code="step.description" />
		<form:input path="description" />
		<form:errors cssClass="error" path="description" />
	</div>
	<div>
		<spring:message code="step.picture" />
		<form:input path="picture" />
		<form:errors cssClass="error" path="picture" />
	</div>
	<div>
		<spring:message code="step.hints" />
		<form:textarea path="hints" />
		<form:errors cssClass="error" path="hints" />
	</div>
	<div>
		<input type="submit" name="save"
			value="<spring:message code="step.save" />" />
		<jstl:if test="${step.id != 0}">
			<input type="submit" name="delete"
				value="<spring:message code="step.delete" />"
				onclick="return confirm('<spring:message code="step.confirm.delete" />')" />&nbsp;
	</jstl:if>
		<input type="button" name="cancel"
			value="<spring:message code="step.cancel" />"
			onclick="window.location='step/user/list.do'" /> <br />
	</div>



</form:form>