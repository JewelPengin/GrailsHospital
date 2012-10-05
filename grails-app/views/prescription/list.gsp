
<%@ page import="com.centurylink.hospital.Prescription" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'prescription.label', default: 'Prescription')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-prescription" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-prescription" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<th><g:message code="prescription.prescribedDrug.label" default="Prescribed Drug" /></th>
					
						<th><g:message code="prescription.providingDoctor.label" default="Providing Doctor" /></th>
					
						<th><g:message code="prescription.receivingPatient.label" default="Receiving Patient" /></th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${prescriptionInstanceList}" status="i" var="prescriptionInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${prescriptionInstance.id}">${fieldValue(bean: prescriptionInstance, field: "prescribedDrug")}</g:link></td>
					
						<td>${fieldValue(bean: prescriptionInstance, field: "providingDoctor")}</td>
					
						<td>${fieldValue(bean: prescriptionInstance, field: "receivingPatient")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${prescriptionInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
