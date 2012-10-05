
<%@ page import="com.centurylink.hospital.Patient" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'patient.label', default: 'Patient')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-patient" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-patient" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list patient">
			
				<g:if test="${patientInstance?.occupiedRoom}">
				<li class="fieldcontain">
					<span id="occupiedRoom-label" class="property-label"><g:message code="patient.occupiedRoom.label" default="Occupied Room" /></span>
					
						<span class="property-value" aria-labelledby="occupiedRoom-label"><g:link controller="room" action="show" id="${patientInstance?.occupiedRoom?.id}">${patientInstance?.occupiedRoom?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${patientInstance?.firstName}">
				<li class="fieldcontain">
					<span id="firstName-label" class="property-label"><g:message code="patient.firstName.label" default="First Name" /></span>
					
						<span class="property-value" aria-labelledby="firstName-label"><g:fieldValue bean="${patientInstance}" field="firstName"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${patientInstance?.lastName}">
				<li class="fieldcontain">
					<span id="lastName-label" class="property-label"><g:message code="patient.lastName.label" default="Last Name" /></span>
					
						<span class="property-value" aria-labelledby="lastName-label"><g:fieldValue bean="${patientInstance}" field="lastName"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${patientInstance?.primaryCareDoctor}">
				<li class="fieldcontain">
					<span id="primaryCareDoctor-label" class="property-label"><g:message code="patient.primaryCareDoctor.label" default="Primary Care Doctor" /></span>
					
						<span class="property-value" aria-labelledby="primaryCareDoctor-label"><g:link controller="doctor" action="show" id="${patientInstance?.primaryCareDoctor?.id}">${patientInstance?.primaryCareDoctor?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${patientInstance?.recievedPrescription}">
				<li class="fieldcontain">
					<span id="recievedPrescription-label" class="property-label"><g:message code="patient.recievedPrescription.label" default="Recieved Prescription" /></span>
					
						<g:each in="${patientInstance.recievedPrescription}" var="r">
						<span class="property-value" aria-labelledby="recievedPrescription-label"><g:link controller="prescription" action="show" id="${r.id}">${r?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${patientInstance?.id}" />
					<g:link class="edit" action="edit" id="${patientInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
