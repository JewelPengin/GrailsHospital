<%@ page import="com.centurylink.hospital.StyleTest" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'styleTest.label', default: 'StyleTest')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<header>
			<nav id="sub-nav">
				<ul>
					<li><g:link action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
				</ul>
			</nav>
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
		</header>

		<section>
			<g:if test="${flash.message}">
				<div class="messages">
					<ul class="notifications">
						<li>${flash.message}</li>
					</ul>
				</div>
			</g:if>
			<cl:grid name="${ entityName }Grid" data="${ list }" url="${createLink(action: 'show', params: [id: '{{id}}'])}" rows="15">
				<cl:column name="name" label="My Name Column" />
				<cl:column name="column1" />
				<cl:column name="column2" />
				<cl:column name="column3" />
			</cl:grid>
		</section>

		<footer></footer>
	</body>
</html>
