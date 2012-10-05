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
            You made styles!
        </section>

        <footer></footer>
	</body>
</html>
