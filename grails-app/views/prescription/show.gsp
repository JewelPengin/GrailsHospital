
<%@ page import="com.centurylink.hospital.Prescription" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'prescription.label', default: 'Prescription')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-prescription" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-prescription" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list prescription">
			
				<g:if test="${prescriptionInstance?.prescribedDrug}">
				<li class="fieldcontain">
					<span id="prescribedDrug-label" class="property-label"><g:message code="prescription.prescribedDrug.label" default="Prescribed Drug" /></span>
					
						<span class="property-value" aria-labelledby="prescribedDrug-label"><g:link controller="drug" action="show" id="${prescriptionInstance?.prescribedDrug?.id}">${prescriptionInstance?.prescribedDrug?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${prescriptionInstance?.providingDoctor}">
				<li class="fieldcontain">
					<span id="providingDoctor-label" class="property-label"><g:message code="prescription.providingDoctor.label" default="Providing Doctor" /></span>
					
						<span class="property-value" aria-labelledby="providingDoctor-label"><g:link controller="doctor" action="show" id="${prescriptionInstance?.providingDoctor?.id}">${prescriptionInstance?.providingDoctor?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${prescriptionInstance?.receivingPatient}">
				<li class="fieldcontain">
					<span id="receivingPatient-label" class="property-label"><g:message code="prescription.receivingPatient.label" default="Receiving Patient" /></span>
					
						<span class="property-value" aria-labelledby="receivingPatient-label"><g:link controller="patient" action="show" id="${prescriptionInstance?.receivingPatient?.id}">${prescriptionInstance?.receivingPatient?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${prescriptionInstance?.id}" />
					<g:link class="edit" action="edit" id="${prescriptionInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
