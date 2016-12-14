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

<form:form action="contest/administrator/edit.do" modelAttribute="contest">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="recipeCopies" />
	<div>
		<spring:message code="contest.title" />
		<form:input path="title" />
		<form:errors cssClass="error" path="title" />
	</div>
	<div>
		<spring:message code="contest.open" />
		<form:input path="openingTime" />
		<form:errors cssClass="error" path="openingTime" />
	</div>
	<div>
		<spring:message code="contest.close" />
		<form:input path="closingTime" />
		<form:errors cssClass="error" path="closingTime" />
	</div>
	<div>
		<input type="submit" name="save"
			value="<spring:message code="contest.save" />" />
		<jstl:if test="${contest.id != 0}">
			<input type="submit" name="delete"
				value="<spring:message code="contest.delete" />"
				onclick="return confirm('<spring:message code="contest.confirm.delete" />')" />&nbsp;
	</jstl:if>
		<input type="button" name="cancel"
			value="<spring:message code="contest.cancel" />"
			onclick="window.location='contest/list.do'" /> <br />
	</div>



</form:form>