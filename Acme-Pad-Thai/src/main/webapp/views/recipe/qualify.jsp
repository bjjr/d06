<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="recipe/user/qualify.do" modelAttribute="recipeCopy">
	
	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="winner"/>
	<form:hidden path="likesRC"/>
	<form:hidden path="dislikesRC"/>
	
	<form:label path="ticker"> 
		<spring:message code="recipe.ticker"/>
	</form:label>
	<form:input readonly="true" path="ticker"/>
	<form:errors cssClass="error" path="ticker"/>
	<br />
		
	<form:label path="title">
		<spring:message code="recipe.title"/>
	</form:label>
	<form:input readonly="true" path="title"/>
	<form:errors cssClass="error" path="title"/>
	<br />
		
	<form:label path="summary">
		<spring:message code="recipe.summary"/>
	</form:label>
	<form:input readonly="true" path="summary"/>
	<form:errors cssClass="error" path="summary"/>
	<br />
					
	<form:label path="momentAuthored">
		<spring:message code="recipe.momentAuthored"/>
	</form:label>
	<form:input readonly="true" path="momentAuthored"/>
	<form:errors cssClass="error" path="momentAuthored"/>
	<br />
	
	<form:label path="momentLastUpdated">
		<spring:message code="recipe.momentLastUpdated"/>
	</form:label>
	<form:input readonly="true" path="momentLastUpdated"/>
	<form:errors cssClass="error" path="momentLastUpdated"/>
	<br />
		
	<form:label path="pictures">
		<spring:message code="recipe.pictures" />
	</form:label>
	<form:textarea readonly="true" path="pictures" />	
	<form:errors cssclass="error" path="pictures" />
	<br />

	<form:label path="hints">
		<spring:message code="recipe.hints" />
	</form:label>
	<form:textarea readonly="true" path="hints" />
	<form:errors class="error" path="hints" />
	<br />
	
	<form:label path="nameUser">
		<spring:message code="recipe.user.name" />
	</form:label>
	<form:input readonly="true" path="nameUser" />
	<form:errors class="error" path="nameUser" />
	<br />
	
	<form:label path="contest">
		<spring:message code="recipe.contest" />:
	</form:label>
	<form:select id="contests" path="contest">
		<form:options items="${contests}" itemValue="id" itemLabel="title" />
	</form:select>
	<form:errors cssClass="error" path="contest" />
	<br />
		
	<!-- Action buttons -->
	<input type="submit" name="qualify"
		value="<spring:message code="recipe.qualify"/>"/>&nbsp;
	<input type="button" name="cancel"
		value="<spring:message code="recipe.cancel" />"
		onclick="window.location='recipe/user/list.do'" />
	<br />
		
</form:form>