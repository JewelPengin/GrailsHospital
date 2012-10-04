
<%@ page import="com.centurylink.hospital.Doctor" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'doctor.label', default: 'Doctor')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-doctor" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-doctor" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list doctor">
			
				<g:if test="${doctorInstance?.firstName}">
				<li class="fieldcontain">
					<span id="firstName-label" class="property-label"><g:message code="doctor.firstName.label" default="First Name" /></span>
					
						<span class="property-value" aria-labelledby="firstName-label"><g:fieldValue bean="${doctorInstance}" field="firstName"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${doctorInstance?.lastName}">
				<li class="fieldcontain">
					<span id="lastName-label" class="property-label"><g:message code="doctor.lastName.label" default="Last Name" /></span>
					
						<span class="property-value" aria-labelledby="lastName-label"><g:fieldValue bean="${doctorInstance}" field="lastName"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${doctorInstance?.primary_care_patient}">
				<li class="fieldcontain">
					<span id="primary_care_patient-label" class="property-label"><g:message code="doctor.primary_care_patient.label" default="Primarycarepatient" /></span>
					
						<g:each in="${doctorInstance.primary_care_patient}" var="p">
						<span class="property-value" aria-labelledby="primary_care_patient-label"><g:link controller="patient" action="show" id="${p.id}">${p?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
				<g:if test="${doctorInstance?.provided_prescription}">
				<li class="fieldcontain">
					<span id="provided_prescription-label" class="property-label"><g:message code="doctor.provided_prescription.label" default="Providedprescription" /></span>
					
						<g:each in="${doctorInstance.provided_prescription}" var="p">
						<span class="property-value" aria-labelledby="provided_prescription-label"><g:link controller="prescription" action="show" id="${p.id}">${p?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${doctorInstance?.id}" />
					<g:link class="edit" action="edit" id="${doctorInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
