<% import grails.persistence.Event %>
<%=packageName%>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="\${message(code: '${domainClass.propertyName}.label', default: '${className}')}" />
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
			<g:if test="\${flash.message}">
				<div class="messages">
					<ul class="notifications">
						<li>\${flash.message}</li>
					</ul>
				</div>
			</g:if>
			<cl:grid name="\${entityName}Grid" data="\${list}" url="\${createLink(action: 'show', params: [id: '{{id}}'])}" rows="15">
				<%  excludedProps = Event.allEvents.toList() << 'id' << 'version'
					allowedNames = domainClass.persistentProperties*.name << 'dateCreated' << 'lastUpdated'
					props = domainClass.properties.findAll { allowedNames.contains(it.name) && !excludedProps.contains(it.name) && it.type != null && !Collection.isAssignableFrom(it.type) }
					Collections.sort(props, comparator.constructors[0].newInstance([domainClass] as Object[]))
					props.eachWithIndex { p, i ->

				%>
					<cl:column name="${p.name}" label="\${message(code: '${domainClass.propertyName}.${p.name}.label', default: '${p.naturalName}')}" sortable="${!p.isAssociation()}" />
				<%
					}
				%>
			</cl:grid>
		</section>

		<footer></footer>
	</body>
</html>

