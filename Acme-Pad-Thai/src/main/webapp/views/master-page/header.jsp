<%--
 * header.jsp
 *
 * Copyright (C) 2016 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<div>
	<img src="images/acmepadthai.png" alt="Acme Pad-Thai, Inc." />
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		<security:authorize access="hasRole('ADMINISTRATOR')">
			<li><a class="fNiv"><spring:message	code="master.page.administrator" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="cook/create.do"><spring:message code="master.page.administrator.create.cook" /></a></li>
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('USER')">
			<li><a class="fNiv"><spring:message	code="master.page.user" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="recipe/user/list.do"><spring:message code="master.page.recipe.user.list" /></a></li>
					<li><a href="user/edit.do"><spring:message code="master.page.user.edit" /></a></li>
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('NUTRITIONIST')">
			<li><a class="fNiv"><spring:message code="master.page.nutritionist" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="curriculum/nutritionist/list.do"><spring:message code="master.page.nutritionist.curriculum.list" /></a></li>
					<li><a href="nutritionist/edit.do"><spring:message code="master.page.nutritionist.edit" /></a></li>
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="isAnonymous()">
			<li class="arrow"></li>
			<li><a class="fNiv" href="masterClass/list.do"><spring:message code="master.page.anonymous.masterClass.list" /></a></li>
			<li><a href="nutritionist/create.do"><spring:message code="master.page.nutritionist.create" /></a></li>
			<li><a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /></a></li>
			<li><a href="user/list.do"><spring:message code="master.page.user.list" /></a></li>	
			<li><a href="recipe/list.do"><spring:message code="master.page.recipe.list" /></a></li>	
		</security:authorize>
		
		<security:authorize access="isAuthenticated()">
			<li>
				<a class="fNiv" href="masterClass/list-unregistered.do">
					<spring:message code="master.page.masterClass.lu" />
				</a>
			</li>
			<li>
				<a class="fNiv"> 
					<spring:message code="master.page.profile" /> 
			        (<security:authentication property="principal.username" />)
				</a>
			</li>
			<li>
				<a class="fNiv" href="masterClass/list-registered.do">
					<spring:message code="master.page.masterClass.lr" />
				</a>
			</li>
			<li>
				<a class="fNiv" href="j_spring_security_logout">
					<spring:message code="master.page.logout" />				
					(<security:authentication property="principal.username" />)					 
				</a>
			</li>
			<li><a href="user/list.do"><spring:message code="master.page.user.list" /></a></li>	
					<li><a href="recipe/list.do"><spring:message code="master.page.recipe.list" /></a></li>
		</security:authorize>
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

