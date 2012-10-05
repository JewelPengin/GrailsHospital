
<%@ page import="com.centurylink.hospital.Drug" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'drug.label', default: 'Drug')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-drug" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-drug" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list drug">
			
				<g:if test="${drugInstance?.containingPrescription}">
				<li class="fieldcontain">
					<span id="containingPrescription-label" class="property-label"><g:message code="drug.containingPrescription.label" default="Containing Prescription" /></span>
					
						<g:each in="${drugInstance.containingPrescription}" var="c">
						<span class="property-value" aria-labelledby="containingPrescription-label"><g:link controller="prescription" action="show" id="${c.id}">${c?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
				<g:if test="${drugInstance?.drugName}">
				<li class="fieldcontain">
					<span id="drugName-label" class="property-label"><g:message code="drug.drugName.label" default="Drug Name" /></span>
					
						<span class="property-value" aria-labelledby="drugName-label"><g:fieldValue bean="${drugInstance}" field="drugName"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${drugInstance?.id}" />
					<g:link class="edit" action="edit" id="${drugInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
