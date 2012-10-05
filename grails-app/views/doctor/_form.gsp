<%@ page import="com.centurylink.hospital.Doctor" %>



<div class="fieldcontain ${hasErrors(bean: doctorInstance, field: 'firstName', 'error')} ">
	<label for="firstName">
		<g:message code="doctor.firstName.label" default="First Name" />
		
	</label>
	<g:textField name="firstName" value="${doctorInstance?.firstName}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: doctorInstance, field: 'lastName', 'error')} ">
	<label for="lastName">
		<g:message code="doctor.lastName.label" default="Last Name" />
		
	</label>
	<g:textField name="lastName" value="${doctorInstance?.lastName}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: doctorInstance, field: 'primaryCarePatient', 'error')} ">
	<label for="primaryCarePatient">
		<g:message code="doctor.primaryCarePatient.label" default="Primary Care Patient" />
		
	</label>
	
<ul class="one-to-many">
<g:each in="${doctorInstance?.primaryCarePatient?}" var="p">
    <li><g:link controller="patient" action="show" id="${p.id}">${p?.encodeAsHTML()}</g:link></li>
</g:each>
<li class="add">
<g:link controller="patient" action="create" params="['doctor.id': doctorInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'patient.label', default: 'Patient')])}</g:link>
</li>
</ul>

</div>

<div class="fieldcontain ${hasErrors(bean: doctorInstance, field: 'writtenPrescription', 'error')} ">
	<label for="writtenPrescription">
		<g:message code="doctor.writtenPrescription.label" default="Written Prescription" />
		
	</label>
	
<ul class="one-to-many">
<g:each in="${doctorInstance?.writtenPrescription?}" var="w">
    <li><g:link controller="prescription" action="show" id="${w.id}">${w?.encodeAsHTML()}</g:link></li>
</g:each>
<li class="add">
<g:link controller="prescription" action="create" params="['doctor.id': doctorInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'prescription.label', default: 'Prescription')])}</g:link>
</li>
</ul>

</div>

