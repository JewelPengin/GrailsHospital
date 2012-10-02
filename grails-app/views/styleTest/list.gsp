<%@ page import="hospital.StyleTest" %>
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
                <div class="message" role="status">${flash.message}</div>
            </g:if>
            <g:grid name="MyGrid" data="${[]}">
                <g:column name="column1" />
            </g:grid>
        </section>

        <footer></footer>
	</body>
</html>
