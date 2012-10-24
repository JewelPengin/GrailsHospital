<!doctype html>
<html>
<head>
	<title><g:layoutTitle default="Home"/></title>
	<r:require modules="core,grid,darkColourScheme" />
	<script type="text/javascript" src="/security/info"></script>
	<r:layoutResources />
	<g:layoutHead/>
</head>
<body>
<header id="toolbar">
	<nav id="main-nav">
		<g:render template="/shared/nav" model="[nav: mainNav]" />
		<ul>
			<li><span class="icon icon-info"></span>
				<ul class="notifications">
					<g:each in="${notifications}">
						<li>
							<div class="message-icon">
								<span class="icon icon-${it[0].type}"></span>
							</div>
							<div class="message-text">
								<a href="${it[0].link == '' ? '#' : it[0].link}">${it[0].message}</a>
							</div>
						</li>
					</g:each>
				</ul>
			</li>
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