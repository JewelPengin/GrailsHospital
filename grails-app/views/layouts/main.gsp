<!doctype html>
<html>
<head>
    <title><g:layoutTitle default="Home"/></title>
    <r:require modules="core,grid" />
    <r:layoutResources />
    <g:layoutHead/>
</head>
<body>
<header id="toolbar">
    <nav id="main-nav">
        <g:render template="/shared/nav" model="[nav: mainNav]" />
        <ul>
            <li><a href="/logout">logout</a></li>
        </ul>
    </nav>
</header>

<div id="body">
    <g:layoutBody/>
</div>

<footer>
    <h6>&copy; CenturyLink 2012</h6>
</footer>

<r:layoutResources />
</body>
</html>