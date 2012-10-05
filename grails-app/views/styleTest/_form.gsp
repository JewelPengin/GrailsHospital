<%@ page import="com.centurylink.hospital.StyleTest" %>
<%
	if (textOnly == null) {
		textOnly = false;
	}
%>

<div class="field-container ${hasErrors(bean: styleTestInstance, field: 'column1', 'error')} ">
	<div class="label">
		<label for="column1">
			<g:message code="styleTest.column1.label" default="Column1" />
		</label>
	</div>
	<g:if test="${ !textOnly }">
		<g:textField name="column1" value="${styleTestInstance?.column1}"/>
	</g:if>
	<g:else>
		${styleTestInstance?.column1}
	</g:else>
</div>

<div class="field-container ${hasErrors(bean: styleTestInstance, field: 'column2', 'error')} ">
	<div class="label">
		<label for="column2">
			<g:message code="styleTest.column2.label" default="Column2" />
		</label>
	</div>
	<g:if test="${ !textOnly }">
		<g:textField name="column2" value="${styleTestInstance?.column2}"/>
	</g:if>
	<g:else>
		${styleTestInstance?.column2}
	</g:else>
</div>

<div class="field-container ${hasErrors(bean: styleTestInstance, field: 'column3', 'error')} ">
	<div class="label">
		<label for="column3">
			<g:message code="styleTest.column3.label" default="Column3" />
		</label>
	</div>
	<g:if test="${ !textOnly }">
		<g:textField name="column3" value="${styleTestInstance?.column3}"/>
	</g:if>
	<g:else>
		${styleTestInstance?.column3}
	</g:else>
</div>

<div class="field-container ${hasErrors(bean: styleTestInstance, field: 'name', 'error')} ">
	<div class="label">
		<label for="name">
			<g:message code="styleTest.name.label" default="Name" />
		</label>
	</div>
	<g:if test="${ !textOnly }">
		<g:textField name="name" value="${styleTestInstance?.name}"/>
	</g:if>
	<g:else>
		${styleTestInstance?.name}
	</g:else>
</div>