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

<form:form action="campaign/sponsor/edit.do" modelAttribute="campaign">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="sponsor" />
	<form:hidden path="bills" />
	<div>
		<spring:message code="campaign.start" />
		<form:input path="startMoment" />
		<form:errors cssClass="error" path="startMoment" />
	</div>
	<div>
		<spring:message code="campaign.end" />
		<form:textarea path="endMoment" />
		<form:errors cssClass="error" path="endMoment" />
	</div>
	<div>
		<spring:message code="campaign.banners" />
		<form:textarea path="banners" />
		<form:errors cssClass="error" path="banners" />
	</div>
	<div>
		<spring:message code="campaign.maxDisplayed" />
		<form:textarea path="maxDisplayed" />
		<form:errors cssClass="error" path="maxDisplayed" />
	</div>
	<div>
		<spring:message code="campaign.star" />
		<form:select path="star" >
		 <form:option value="True"><spring:message code="campaign.trueStar"/></form:option>
		 <form:option value="False"><spring:message code="campaign.falseStar"/></form:option>
		</form:select>
		<form:errors cssClass="error" path="star" />
	</div>
	<div>
		<input type="submit" name="save"
			value="<spring:message code="campaign.save" />" />;
		<jstl:if test="${campaign.id != 0}">
			<input type="submit" name="delete"
				value="<spring:message code="campaign.delete" />"
				onclick="return confirm('<spring:message code="campaign.confirm.delete" />')" />&nbsp;
	</jstl:if>
		<input type="button" name="cancel"
			value="<spring:message code="campaign.cancel" />"
			onclick="relativeRedir('campaign/sponsor/list.do')" /> <br />
	</div>



</form:form>