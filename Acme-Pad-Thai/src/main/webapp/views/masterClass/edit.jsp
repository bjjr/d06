<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="tg" uri="http://displaytag.sf.net"%>

<form:form action="masterClass/edit.do" modelAttribute="masterClass">

	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="actors"/>
	<form:hidden path="cook"/>
	<form:hidden path="learningMaterials"/>
	
	<div>
		<form:label path="title">
			<spring:message code="masterClass.title" />
		</form:label>
		
		<security:authorize access="hasRole('ADMINISTRATOR')">
			<form:input path="title" readonly="true"/>
		</security:authorize>
		
		<security:authorize access="hasRole('COOK')">
			<form:input path="title"/>
		</security:authorize>
		
		<form:errors cssClass="error" path="title" />
	</div>
	
	<div>
		<form:label path="description">
			<spring:message code="masterClass.description"/>
		</form:label>
		
		<security:authorize access="hasRole('ADMINISTRATOR')">
			<form:textarea path="description" readonly="true"/>
		</security:authorize>
		
		<security:authorize access="hasRole('COOK')">
			<form:textarea path="description"/>
		</security:authorize>
		
		<form:errors cssClass="error" path="description" />
	</div>
	
	<security:authorize access="hasRole('ADMINISTRATOR')">
		<div>
			<form:label path="promoted">
				<spring:message code="masterClass.promoted"/>
			</form:label>
			
			<form:checkbox path="promoted"/>
		</div>
	</security:authorize>
	
	<div>
		
		<spring:message code="masterClass.save" var="saveText"/>
		<input type="submit" name="save" value="${saveText}"/>
		
		<security:authorize access="hasRole('COOK')">
			<jstl:if test="${masterClass.id != 0}" >
				<spring:message code="masterClass.delete" var="deleteText" />
				<spring:message code="masterClass.confirm.delete" var="confDeleteText" />
				<input type="submit" name="delete" value="${deleteText}"
					onclick="return confirm('${confDeleteText}')" />
			</jstl:if>
		
			<spring:message code="masterClass.cancel" var="cancelText" />
			<input type="button" name="cancel" value="${cancelText}"
				onclick="window.location='masterClass/cook/list-managed.do'" />
		</security:authorize>
		
		<security:authorize access="hasRole('ADMINISTRATOR')">
			<input type="button" name="cancel" value="${cancelText}"
			onclick="window.location='masterClass/list.do'" />
		</security:authorize>
		
	</div>
	
</form:form>