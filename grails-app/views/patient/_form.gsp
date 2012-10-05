<%@ page import="com.centurylink.hospital.Patient" %>



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

<div class="fieldcontain ${hasErrors(bean: patientInstance, field: 'occupied_room', 'error')} required">
	<label for="occupied_room">
		<g:message code="patient.occupied_room.label" default="Occupiedroom" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="occupied_room" name="occupied_room.id" from="${com.centurylink.hospital.Room.list()}" optionKey="id" required="" value="${patientInstance?.occupied_room?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: patientInstance, field: 'primary_care_doctor', 'error')} required">
	<label for="primary_care_doctor">
		<g:message code="patient.primary_care_doctor.label" default="Primarycaredoctor" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="primary_care_doctor" name="primary_care_doctor.id" from="${com.centurylink.hospital.Doctor.list()}" optionKey="id" required="" value="${patientInstance?.primary_care_doctor?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: patientInstance, field: 'received_prescription', 'error')} ">
	<label for="received_prescription">
		<g:message code="patient.received_prescription.label" default="Receivedprescription" />
		
	</label>
	
<ul class="one-to-many">
<g:each in="${patientInstance?.received_prescription?}" var="r">
    <li><g:link controller="prescription" action="show" id="${r.id}">${r?.encodeAsHTML()}</g:link></li>
</g:each>
<li class="add">
<g:link controller="prescription" action="create" params="['patient.id': patientInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'prescription.label', default: 'Prescription')])}</g:link>
</li>
</ul>

</div>

