<g:if test="${ nav != null && !nav.isEmpty() }">
    <ul>
        <g:each var="link" in="${ nav }">
            <g:if test="${ link.active }">
                <li <g:if test="${ !link.children.isEmpty() }">class="has-dd"</g:if>>
                    <a href="${ link.url }" title="${ link.title }">${ link.name }</a>
                    <g:render template="/shared/nav" model="[nav: link.children]" />
                </li>
            </g:if>
        </g:each>
    </ul>
</g:if>