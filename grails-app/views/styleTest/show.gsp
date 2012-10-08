
<%@ page import="com.centurylink.hospital.StyleTest" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'styleTest.label', default: 'StyleTest')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<header>
			<nav id="sub-nav">
				<ul>
					<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
					<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
				</ul>
			</nav>
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
		</header>

		<section>
			<g:if test="${flash.message}">
				<div class="messages">
					<ul class="notifications">
						<li>${flash.message}</li>
					</ul>
				</div>
			</g:if>
			<g:hasErrors bean="${styleTestInstance}">
				<div class="messages">
					<ul class="errors">
						<g:eachError bean="${styleTestInstance}" var="error">
							<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
						</g:eachError>
					</ul>
				</div>
			</g:hasErrors>
			<g:form method="post">
				<g:hiddenField name="id" value="${styleTestInstance?.id}" />
				<g:hiddenField name="version" value="${styleTestInstance?.version}" />
				<fieldset class="form">
					<g:render template="form" />
				</fieldset>
				<fieldset class="buttons">
					<g:link class="button" action="edit" id="${styleTestInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="button" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</section>

		<footer></footer>
	</body>
</html>
