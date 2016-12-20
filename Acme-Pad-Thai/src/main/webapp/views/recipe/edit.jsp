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

<form:form action="recipe/user/edit.do" modelAttribute="recipe">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="ticker" />
	<form:hidden path="momentAuthored" />
	<form:hidden path="momentLastUpdated" />
	<form:hidden path="quantities" />
	<form:hidden path="categories" />
	<form:hidden path="steps" />
	<form:hidden path="comments" />
	<form:hidden path="likesSA" />
	<form:hidden path="user" />
	
	<div>
		<spring:message code="recipe.title" />
		<form:input path="title" />
		<form:errors cssClass="error" path="title" />
	</div>
	<div>
		<spring:message code="recipe.summary" />
		<form:input path="summary" />
		<form:errors cssClass="error" path="summary" />
	</div>
	<div>
		<spring:message code="recipe.pictures" />
		<form:textarea path="pictures" />
		<form:errors cssClass="error" path="pictures" />
	</div>
	<div>
		<spring:message code="recipe.hints" />
		<form:textarea path="hints" />
		<form:errors cssClass="error" path="hints" />
	</div>
	<div>
		<input type="submit" name="save"
			value="<spring:message code="recipe.save" />" />&nbsp;
		<jstl:if test="${recipe.id != 0}">
			<input type="submit" name="delete"
			 value="<spring:message code="recipe.delete" />"
			 onclick="return confirm('<spring:message code="recipe.confirm.delete" />')"/>&nbsp;
		</jstl:if>
		<input type="button" name="cancel"
			value="<spring:message code="recipe.cancel" />"
			onclick="/views/welcome/index.jsp" /> <br />
	</div>

</form:form>