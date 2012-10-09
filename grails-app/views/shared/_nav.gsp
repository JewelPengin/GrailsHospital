<g:if test="${ nav != null && !nav.isEmpty() }">
    <ul>
        <g:each var="link" in="${ nav }">
            <g:if test="${ link.active }">
                <li>
                    <a href="${ link.url }" title="${ link.title }">${ link.name }</a>
                    <g:render template="/shared/nav" model="[nav: link.children]" />
                </li>
            </g:if>
        </g:each>
    </ul>
</g:if>