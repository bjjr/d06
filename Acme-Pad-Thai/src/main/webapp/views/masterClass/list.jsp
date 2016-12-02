<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table name="masterClasses" id="masterClass"
  requestURI="${requestURI}" pagesize="5"
  class="displaytag">
  
  	<security:authorize access="isAuthenticated()">
  		<jstl:if test="${registered == false}">
  			<display:column>
	  			<a href="masterClass/actor/register.do?masterClassId=${masterClass.id}">
	  				<spring:message code="masterClass.register" var="registerText" />
	  				<jstl:out value="${registerText}" />
	  			</a>
  			</display:column>
  		</jstl:if>
  	</security:authorize>
  		
  	<spring:message code="masterClass.cook" var="cookHeader" />
  	<display:column property="cook.name" title="${cookHeader}" sortable="true"/>
  	
  	<spring:message code="masterClass.title" var="titleHeader" />
  	<display:column property="title" title="${titleHeader}" sortable="true"/>
  	
  	<spring:message code="masterClass.description" var="descriptionHeader" />
  	<display:column property="description" title="${descriptionHeader}"/>
  	
  	
  	<security:authorize access="isAuthenticated()">
  		<jstl:if test="${registered == true}">
			<display:column>
		  		<a href="learningMaterial/actor/list.do?masterClassId=${masterClass.id}">
		  			<spring:message code="masterClass.learningMaterials" var="learningMaterialsText" />
		  			<jstl:out value="${learningMaterialsText}" />
		  		</a>
	  		</display:column>
  		</jstl:if>
  	</security:authorize>
  	
  	<security:authorize access="hasRole('COOK')">
  		<jstl:if test="${registered == null}">
	  		<display:column>
	  			<a href="learningMaterial/cook/list-managed.do=${masterClass.id}">
		  			<spring:message code="masterClass.learningMaterials" var="learningMaterialsText" />
		  			<jstl:out value="${learningMaterialsText}" />
		  		</a>
	  		</display:column>
  		</jstl:if>
  	</security:authorize>
  	
  	<security:authorize access="hasAnyRole('COOK','ADMINISTRATOR')">
  		<jstl:if test=${registered == null}>
	  		<spring:message code="masterClass.promoted" var="promotedHeader" />
		  	<display:column property="promoted" title="${promotedHeader}" />
		  		
	  		<display:column>
		  		<a href="masterClass/edit.do?masterClassId=${masterClass.id}">
		  			<spring:message code="masterClass.edit" var="editText" />
		  			<jstl:out value="${editText}" />
		  		</a>
	  		</display:column>
  		</jstl:if>
	</security:authorize>
	
</display:table>

<security:authorize access="hasRole('COOK')">
	<jstl:if test="${registered == null}">
		<div>
			<a href="masterClass/cook/create.do">
				<spring:message code="masterClass.create" var="createText" />
			  	<jstl:out value="${createText}" />
			</a>
		</div>
	</jstl:if>
</security:authorize>