<%@ page import="com.centurylink.hospital.StyleTest" %>



<div class="field-container ${hasErrors(bean: styleTestInstance, field: 'column1', 'error')} ">
	<label for="column1">
		<g:message code="styleTest.column1.label" default="Column1" />

	</label>
	<g:textField name="column1" value="${styleTestInstance?.column1}"/>
</div>

<div class="field-container ${hasErrors(bean: styleTestInstance, field: 'column2', 'error')} ">
	<label for="column2">
		<g:message code="styleTest.column2.label" default="Column2" />

	</label>
	<g:textField name="column2" value="${styleTestInstance?.column2}"/>
</div>

<div class="field-container ${hasErrors(bean: styleTestInstance, field: 'column3', 'error')} ">
	<label for="column3">
		<g:message code="styleTest.column3.label" default="Column3" />

	</label>
	<g:textField name="column3" value="${styleTestInstance?.column3}"/>
</div>

<div class="field-container ${hasErrors(bean: styleTestInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="styleTest.name.label" default="Name" />

	</label>
	<g:textField name="name" value="${styleTestInstance?.name}"/>
</div>

