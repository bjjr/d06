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

<form:form action="socialIdentity/actor/edit.do" modelAttribute="socialIdentity">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="Actor" />
	<div>
		<spring:message code="socialIdentity.nick" />
		<form:input path="nick" />
		<form:errors cssClass="error" path="nick" />
	</div>
	<div>
		<spring:message code="socialIdentity.nameSN" />
		<form:input path="nameSN" />
		<form:errors cssClass="error" path="nameSN" />
	</div>
	<div>
	<spring:message code="socialIdentity.link" />
		<form:input path="link" />
		<form:errors cssClass="error" path="link" />
	</div>
		<div>
	<spring:message code="socialIdentity.picture" />
		<form:input path="picture" />
		<form:errors cssClass="error" path="picture" />
	</div>
	<div>
		<input type="submit" name="save"
			value="<spring:message code="socialIdentity.save" />" />
		<jstl:if test="${socialIdentity.id != 0}">
			<input type="submit" name="delete"
				value="<spring:message code="socialIdentity.delete" />"
				onclick="return confirm('<spring:message code="socialIdentity.confirm.delete" />')" />&nbsp;
	</jstl:if>
		<input type="button" name="cancel"
			value="<spring:message code="socialIdentity.cancel" />"
			onclick="window.location='socialIdentity/list.do'" /> <br />
	</div>



</form:form>