
<%@ page import="com.centurylink.hospital.StyleTest" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'styleTest.label', default: 'StyleTest')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-styleTest" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-styleTest" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list styleTest">

				<g:if test="${styleTestInstance?.column1}">
				<li class="fieldcontain">
					<span id="column1-label" class="property-label"><g:message code="styleTest.column1.label" default="Column1" /></span>

						<span class="property-value" aria-labelledby="column1-label"><g:fieldValue bean="${styleTestInstance}" field="column1"/></span>

				</li>
				</g:if>

				<g:if test="${styleTestInstance?.column2}">
				<li class="fieldcontain">
					<span id="column2-label" class="property-label"><g:message code="styleTest.column2.label" default="Column2" /></span>

						<span class="property-value" aria-labelledby="column2-label"><g:fieldValue bean="${styleTestInstance}" field="column2"/></span>

				</li>
				</g:if>

				<g:if test="${styleTestInstance?.column3}">
				<li class="fieldcontain">
					<span id="column3-label" class="property-label"><g:message code="styleTest.column3.label" default="Column3" /></span>

						<span class="property-value" aria-labelledby="column3-label"><g:fieldValue bean="${styleTestInstance}" field="column3"/></span>

				</li>
				</g:if>

				<g:if test="${styleTestInstance?.name}">
				<li class="fieldcontain">
					<span id="name-label" class="property-label"><g:message code="styleTest.name.label" default="Name" /></span>

						<span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${styleTestInstance}" field="name"/></span>

				</li>
				</g:if>

			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${styleTestInstance?.id}" />
					<g:link class="edit" action="edit" id="${styleTestInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
