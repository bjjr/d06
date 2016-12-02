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

<form:form action="sponsor/edit.do" modelAttribute="sponsor">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="folders" />
	<form:hidden path="sentMessages" />
	<form:hidden path="receivedMessages" />
	<form:hidden path="masterClasses" />
	<form:hidden path="socialIdentities" />
	<form:hidden path="followers" />
	<form:hidden path="follows" />
	<form:hidden path="comments" />
	<form:hidden path="likesSA" />
	<form:hidden path="curriculum" />

	<form:label path="name">
		<spring:message code="sponsor.name" />
	</form:label>
	<form:input path="name" />
	<form:errors cssClass="error" path="name" />
	<br />

	<form:label path="surname">
		<spring:message code="sponsor.surname" />
	</form:label>
	<form:input path="surname" />
	<form:errors cssClass="error" path="surname" />
	<br />

	<form:label path="email">
		<spring:message code="sponsor.email" />
	</form:label>
	<form:input path="email" />
	<form:errors cssClass="error" path="email" />
	<br />

	<form:label path="phone">
		<spring:message code="sponsor.phone" />
	</form:label>
	<form:input path="phone" />
	<form:errors cssClass="error" path="phone" />
	<br />

	<form:label path="postalAddress">
		<spring:message code="sponsor.postalAddress" />
	</form:label>
	<form:input path="postalAddress" />
	<br />

	<form:label path="userAccount.username">
		<spring:message code="sponsor.username" />
	</form:label>
	<form:input path="userAccount.username" />
	<form:errors class="error" path="userAccount.username" />
	<br />

	<form:label path="userAccount.password">
		<spring:message code="sponsor.password" />
	</form:label>
	<form:password path="userAccount.password" />
	<form:errors class="error" path="userAccount.password" />
	<br />

	<form:label path="companyName">
		<spring:message code="sponsor.company" />
	</form:label>
	<form:input path="companyName" />
	<form:errors class="error" path="companyName" />
	<br />

	<fieldset>
		<legend align="left">
			<spring:message code="sponsor.creditCard" />
		</legend>

		<form:label path="creditCard.holderName">
			<spring:message code="sponsor.creditCard.holderName" />
		</form:label>
		<form:input path="creditCard.holderName" />
		<form:errors cssClass="error" path="creditCard.holderName" />
		<br />

		<form:label path="creditCard.number">
			<spring:message code="sponsor.creditCard.number" />
		</form:label>
		<form:input path="creditCard.number" />
		<form:errors cssClass="error" path="creditCard.number" />
		<br />

		<form:label path="creditCard.brandName">
			<spring:message code="sponsor.creditCard.brandName" />
		</form:label>
		<form:input path="creditCard.brandName" />
		<form:errors cssClass="error" path="creditCard.brandName" />
		<br />

		<form:label path="creditCard.cvv">
			<spring:message code="sponsor.creditCard.cvv" />
		</form:label>
		<form:input path="creditCard.cvv" />
		<form:errors cssClass="error" path="creditCard.cvv" />
		<br />

		<form:label path="creditCard.expirationMonth">
			<spring:message code="sponsor.creditCard.expirationMonth" />
		</form:label>
		<form:input path="creditCard.expirationMonth" />
		<form:errors cssClass="error" path="creditCard.expirationMonth" />
		<br />

		<form:label path="creditCard.expirationYear">
			<spring:message code="sponsor.creditCard.expirationYear" />
		</form:label>
		<form:input path="creditCard.expirationYear" />
		<form:errors cssClass="error" path="creditCard.expirationYear" />
		<br />

	</fieldset>

	<!-- Action buttons -->
	<input type="submit" name="save"
		value="<spring:message code="sponsor.save"/>" />&nbsp;
	<input type="button" name="cancel"
		value="<spring:message code="sponsor.cancel" />"
		onclick="javascript: relativeRedir('/');" />
	<br />

</form:form>