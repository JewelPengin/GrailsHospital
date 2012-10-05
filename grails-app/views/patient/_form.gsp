<%@ page import="com.centurylink.hospital.Patient" %>



<div class="fieldcontain ${hasErrors(bean: patientInstance, field: 'occupiedRoom', 'error')} ">
	<label for="occupiedRoom">
		<g:message code="patient.occupiedRoom.label" default="Occupied Room" />
		
	</label>
	<g:select id="occupiedRoom" name="occupiedRoom.id" from="${com.centurylink.hospital.Room.list()}" optionKey="id" value="${patientInstance?.occupiedRoom?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: patientInstance, field: 'firstName', 'error')} ">
	<label for="firstName">
		<g:message code="patient.firstName.label" default="First Name" />
		
	</label>
	<g:textField name="firstName" value="${patientInstance?.firstName}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: patientInstance, field: 'lastName', 'error')} ">
	<label for="lastName">
		<g:message code="patient.lastName.label" default="Last Name" />
		
	</label>
	<g:textField name="lastName" value="${patientInstance?.lastName}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: patientInstance, field: 'primaryCareDoctor', 'error')} required">
	<label for="primaryCareDoctor">
		<g:message code="patient.primaryCareDoctor.label" default="Primary Care Doctor" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="primaryCareDoctor" name="primaryCareDoctor.id" from="${com.centurylink.hospital.Doctor.list()}" optionKey="id" required="" value="${patientInstance?.primaryCareDoctor?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: patientInstance, field: 'recievedPrescription', 'error')} ">
	<label for="recievedPrescription">
		<g:message code="patient.recievedPrescription.label" default="Recieved Prescription" />
		
	</label>
	
<ul class="one-to-many">
<g:each in="${patientInstance?.recievedPrescription?}" var="r">
    <li><g:link controller="prescription" action="show" id="${r.id}">${r?.encodeAsHTML()}</g:link></li>
</g:each>
<li class="add">
<g:link controller="prescription" action="create" params="['patient.id': patientInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'prescription.label', default: 'Prescription')])}</g:link>
</li>
</ul>

</div>

