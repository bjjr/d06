<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="quantity/edit.do" modelAttribute="quantity">
	
	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="recipe"/>
	
	<div>
		<form:label path="value">
			<spring:message code="quantity.quantity"/>
		</form:label>
		<form:input path="value"/>
		<form:errors cssClass="error" path="value"/>
	</div>
	
	<div>
		<form:label path="unit">
			<spring:message code="quantity.unit"/>
		</form:label>
		<form:select path="unit">
			<form:option label="-----" value="0" />
			<form:options items="${units}" itemLabel="unit" itemValue="id" />
		</form:select>
		<form:errors cssClass="${error}" path="unit"/>
	</div>
	
	<div>
		<form:label path="ingredient">
			<spring:message code="quantity.ingredient"/>
		</form:label>
		<form:select path="ingredient">
			<form:option label="-----" value="0" />
			<form:options items="${ingredients}" itemLabel="name" itemValue="id" />
		</form:select>
		<form:errors cssClass="error" path="ingredient"/>
	</div>
		
	<!-- Action buttons -->
	<div>
		<input type="submit" name="save"
			value="<spring:message code="quantity.save"/>"/>
		<jstl:if test="${row.id != 0}">
			<input type="submit" name="delete"
			value="<spring:message code="quantity.delete"/>"/>
		</jstl:if>
		<input type="button" name="cancel"
			value="<spring:message code="quantity.cancel" />"
			onclick="window.location='/Acme-Pad-Thai/recipe/display.do?recipeId=${recipeId}'" />
	</div>
		
</form:form>