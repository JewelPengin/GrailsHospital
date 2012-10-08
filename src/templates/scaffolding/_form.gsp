<%=packageName%>
<% import grails.persistence.Event %>

<%  excludedProps = Event.allEvents.toList() << 'version' << 'dateCreated' << 'lastUpdated'
	persistentPropNames = domainClass.persistentProperties*.name
	boolean hasHibernate = pluginManager?.hasGrailsPlugin('hibernate')
	if (hasHibernate && org.codehaus.groovy.grails.orm.hibernate.cfg.GrailsDomainBinder.getMapping(domainClass)?.identity?.generator == 'assigned') {
		persistentPropNames << domainClass.identifier.name
	}
	props = domainClass.properties.findAll { persistentPropNames.contains(it.name) && !excludedProps.contains(it.name) }
	Collections.sort(props, comparator.constructors[0].newInstance([domainClass] as Object[]))
	for (p in props) {
		if (p.embedded) {
			def embeddedPropNames = p.component.persistentProperties*.name
			def embeddedProps = p.component.properties.findAll { embeddedPropNames.contains(it.name) && !excludedProps.contains(it.name) }
			Collections.sort(embeddedProps, comparator.constructors[0].newInstance([p.component] as Object[]))
			%><fieldset class="embedded"><legend><g:message code="${domainClass.propertyName}.${p.name}.label" default="${p.naturalName}" /></legend><%
				for (ep in p.component.properties) {
					renderFieldForProperty(ep, p.component, "${p.name}.")
				}
			%></fieldset><%
		} else {
			renderFieldForProperty(p, domainClass)
		}
	}

private renderFieldForProperty(p, owningClass, prefix = "") {
	boolean hasHibernate = pluginManager?.hasGrailsPlugin('hibernate')
	boolean display = true
	boolean required = false
	if (hasHibernate) {
		cp = owningClass.constrainedProperties[p.name]
		display = (cp ? cp.display : true)
		required = (cp ? !(cp.propertyType in [boolean, Boolean]) && !cp.nullable && (cp.propertyType != String || !cp.blank) : false)
	}
	if (display) { %>
${ '<% textOnly = textOnly ?: false; if (actionName == \'show\') { textOnly = true } %>' }
<div class="field-container \${hasErrors(bean: ${propertyName}, field: '${prefix}${p.name}', 'error')} ${required ? 'required' : ''}">
	<div class="label">
		<label for="${prefix}${p.name}">
			<g:message code="${domainClass.propertyName}.${prefix}${p.name}.label" default="${p.naturalName}" />
			<% if (required) { %><span class="required-indicator">*</span><% } %>
		</label>
	</div>
	<div class="field">
		<g:if test="\${ !textOnly }">
			${renderEditor(p)}
		</g:if>
		<g:else>
			<%  if (p.isEnum()) { %>
				<g:fieldValue bean="\${${propertyName}}" field="${p.name}"/>
			<%  } else if (p.oneToMany || p.manyToMany) { %>
				<g:each in="\${${propertyName}.${p.name}}" var="${p.name[0]}">
					<g:link controller="${p.referencedDomainClass?.propertyName}" action="show" id="\${${p.name[0]}.id}">\${${p.name[0]}?.encodeAsHTML()}</g:link>
				</g:each>
			<%  } else if (p.manyToOne || p.oneToOne) { %>
				<g:link controller="${p.referencedDomainClass?.propertyName}" action="show" id="\${${propertyName}?.${p.name}?.id}">\${${propertyName}?.${p.name}?.encodeAsHTML()}</g:link>
			<%  } else if (p.type == Boolean || p.type == boolean) { %>
				<g:formatBoolean boolean="\${${propertyName}?.${p.name}}" /></span>
			<%  } else if (p.type == Date || p.type == java.sql.Date || p.type == java.sql.Time || p.type == Calendar) { %>
				<g:formatDate date="\${${propertyName}?.${p.name}}" />
			<%  } else if(!p.type.isArray()) { %>
				<g:fieldValue bean="\${${propertyName}}" field="${p.name}"/>
			<%  } %>
		</g:else>
	</div>
</div>
<%  }   } %>
