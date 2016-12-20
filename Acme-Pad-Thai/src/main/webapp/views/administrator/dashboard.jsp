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

<ul>
	<jstl:forEach var="entry" items="${queries}">
		<li><h2>
				<spring:message code="${entry.key }" />:
			</h2>
			<h3>
				<jstl:out value="${entry.value}" />
			</h3></li>
	</jstl:forEach>
	<jstl:forEach var="entry2" items="${listQueries}">
		<li><h2>
				<spring:message code="${entry2.key }" />:
			</h2>
			<ol>
				<jstl:forEach var="value" items="${entry2.value}">
					<li><h3>
							<jstl:out value="${value}" />
						</h3></li>
				</jstl:forEach>
			</ol></li>
	</jstl:forEach>
</ul>