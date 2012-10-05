<%@ page import="com.centurylink.hospital.Drug" %>



<div class="fieldcontain ${hasErrors(bean: drugInstance, field: 'containingPrescription', 'error')} ">
	<label for="containingPrescription">
		<g:message code="drug.containingPrescription.label" default="Containing Prescription" />
		
	</label>
	
<ul class="one-to-many">
<g:each in="${drugInstance?.containingPrescription?}" var="c">
    <li><g:link controller="prescription" action="show" id="${c.id}">${c?.encodeAsHTML()}</g:link></li>
</g:each>
<li class="add">
<g:link controller="prescription" action="create" params="['drug.id': drugInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'prescription.label', default: 'Prescription')])}</g:link>
</li>
</ul>

</div>

<div class="fieldcontain ${hasErrors(bean: drugInstance, field: 'drugName', 'error')} ">
	<label for="drugName">
		<g:message code="drug.drugName.label" default="Drug Name" />
		
	</label>
	<g:textField name="drugName" value="${drugInstance?.drugName}"/>
</div>

