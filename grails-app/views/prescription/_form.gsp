<%@ page import="com.centurylink.hospital.Prescription" %>



<div class="fieldcontain ${hasErrors(bean: prescriptionInstance, field: 'prescribedDrug', 'error')} required">
	<label for="prescribedDrug">
		<g:message code="prescription.prescribedDrug.label" default="Prescribed Drug" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="prescribedDrug" name="prescribedDrug.id" from="${com.centurylink.hospital.Drug.list()}" optionKey="id" required="" value="${prescriptionInstance?.prescribedDrug?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: prescriptionInstance, field: 'providingDoctor', 'error')} required">
	<label for="providingDoctor">
		<g:message code="prescription.providingDoctor.label" default="Providing Doctor" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="providingDoctor" name="providingDoctor.id" from="${com.centurylink.hospital.Doctor.list()}" optionKey="id" required="" value="${prescriptionInstance?.providingDoctor?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: prescriptionInstance, field: 'receivingPatient', 'error')} required">
	<label for="receivingPatient">
		<g:message code="prescription.receivingPatient.label" default="Receiving Patient" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="receivingPatient" name="receivingPatient.id" from="${com.centurylink.hospital.Patient.list()}" optionKey="id" required="" value="${prescriptionInstance?.receivingPatient?.id}" class="many-to-one"/>
</div>

