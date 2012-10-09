<%@ page import="com.centurylink.hospital.Doctor" %>



<% textOnly = textOnly ?: false; if (actionName == 'show') { textOnly = true } %>
<div class="field-container ${hasErrors(bean: doctorInstance, field: 'lastName', 'error')} required">
	<div class="label">
		<label for="lastName">
			<g:message code="doctor.lastName.label" default="Last Name" />
			<span class="required-indicator">*</span>
		</label>
	</div>
	<div class="field">
		<g:if test="${ !textOnly }">
			<g:textField name="lastName" required="" value="${doctorInstance?.lastName}"/>
		</g:if>
		<g:else>
			
				<g:fieldValue bean="${doctorInstance}" field="lastName"/>
			
		</g:else>
	</div>
</div>

<% textOnly = textOnly ?: false; if (actionName == 'show') { textOnly = true } %>
<div class="field-container ${hasErrors(bean: doctorInstance, field: 'firstName', 'error')} ">
	<div class="label">
		<label for="firstName">
			<g:message code="doctor.firstName.label" default="First Name" />
			
		</label>
	</div>
	<div class="field">
		<g:if test="${ !textOnly }">
			<g:textField name="firstName" value="${doctorInstance?.firstName}"/>
		</g:if>
		<g:else>
			
				<g:fieldValue bean="${doctorInstance}" field="firstName"/>
			
		</g:else>
	</div>
</div>

<% textOnly = textOnly ?: false; if (actionName == 'show') { textOnly = true } %>
<div class="field-container ${hasErrors(bean: doctorInstance, field: 'primaryCarePatient', 'error')} ">
	<div class="label">
		<label for="primaryCarePatient">
			<g:message code="doctor.primaryCarePatient.label" default="Primary Care Patient" />
			
		</label>
	</div>
	<div class="field">
		<g:if test="${ !textOnly }">
			
<ul class="one-to-many">
<g:each in="${doctorInstance?.primaryCarePatient?}" var="p">
    <li><g:link controller="patient" action="show" id="${p.id}">${p?.encodeAsHTML()}</g:link></li>
</g:each>
<li class="add">
<g:link controller="patient" action="create" params="['doctor.id': doctorInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'patient.label', default: 'Patient')])}</g:link>
</li>
</ul>

		</g:if>
		<g:else>
			
				<g:each in="${doctorInstance.primaryCarePatient}" var="p">
					<g:link controller="patient" action="show" id="${p.id}">${p?.encodeAsHTML()}</g:link>
				</g:each>
			
		</g:else>
	</div>
</div>

<% textOnly = textOnly ?: false; if (actionName == 'show') { textOnly = true } %>
<div class="field-container ${hasErrors(bean: doctorInstance, field: 'writtenPrescription', 'error')} ">
	<div class="label">
		<label for="writtenPrescription">
			<g:message code="doctor.writtenPrescription.label" default="Written Prescription" />
			
		</label>
	</div>
	<div class="field">
		<g:if test="${ !textOnly }">
			
<ul class="one-to-many">
<g:each in="${doctorInstance?.writtenPrescription?}" var="w">
    <li><g:link controller="prescription" action="show" id="${w.id}">${w?.encodeAsHTML()}</g:link></li>
</g:each>
<li class="add">
<g:link controller="prescription" action="create" params="['doctor.id': doctorInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'prescription.label', default: 'Prescription')])}</g:link>
</li>
</ul>

		</g:if>
		<g:else>
			
				<g:each in="${doctorInstance.writtenPrescription}" var="w">
					<g:link controller="prescription" action="show" id="${w.id}">${w?.encodeAsHTML()}</g:link>
				</g:each>
			
		</g:else>
	</div>
</div>

