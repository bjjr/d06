<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="cook/edit.do" modelAttribute="cook">

	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="folders"/>
	<form:hidden path="sentMessages"/>
	<form:hidden path="receivedMessages"/>
	<form:hidden path="masterClasses"/>
	<form:hidden path="socialIdentities"/>
	<form:hidden path="masterClassesTeach"/>
		
	<div>
		<form:label path="name">
			<spring:message code="cook.name"/>
		</form:label>
		<form:input path="name"/>
		<form:errors cssClass="error" path="name"/>
	</div>

	<div>
		<form:label path="surname">
			<spring:message code="cook.surname"/>
		</form:label>
		<form:input path="surname"/>
		<form:errors cssClass="error" path="surname"/>	
	</div>		
	
	<div>
		<form:label path="email">
			<spring:message code="cook.email"/>
		</form:label>
		<form:input path="email"/>
		<form:errors cssClass="error" path="email"/>
	</div>
	
	<div>
		<form:label path="phone">
			<spring:message code="cook.phone"/>
		</form:label>
		<form:input path="phone"/>
		<form:errors cssClass="error" path="phone"/>
	</div>			

	<div>
		<form:label path="postalAddress">
			<spring:message code="cook.postalAddress"/>
		</form:label>
		<form:input path="postalAddress"/>
	</div>	
	
	<div>
		<form:label path="userAccount.username">
			<spring:message code="cook.username" />
		</form:label>
		
		<form:input path="userAccount.username" />
		
		<form:errors class="error" path="userAccount.username" />
	</div>
	
	<div>
		<form:label path="userAccount.password">
			<spring:message code="cook.password" />
		</form:label>
		<form:password path="userAccount.password" />
		<form:errors class="error" path="userAccount.password" />
	</div>
		
	<!-- Action buttons -->
	<div>
		<input type="submit" name="save"
		value="<spring:message code="cook.save"/>"/>&nbsp;
	<input type="button" name="cancel"
		value="<spring:message code="cook.cancel" />"
		onclick="javascript: relativeRedir('/');" />
	</div>
	
</form:form>