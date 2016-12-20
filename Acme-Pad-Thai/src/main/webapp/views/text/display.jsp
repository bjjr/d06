<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<h2>
	<jstl:out value="${text.title}"></jstl:out>
</h2>

<div>
	<jstl:out value="${text.body}"></jstl:out>
</div>

<br />

<div>
	<spring:message code="text.back" var="backText" />
	<input type="button" name="back" value="${backText}"
		onclick="window.location='learningMaterial/actor/list.do?masterClassId=${masterClassId}'" />
</div>