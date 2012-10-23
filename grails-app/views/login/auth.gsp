<html>
	<head>
		<meta name="layout" content="main"/>
		<title><g:message code="springSecurity.login.title"/></title>
	</head>

	<body>
		<div id="login">
			<div class="header"><g:message code="springSecurity.login.header"/></div>

			<g:if test="${flash.message}">
				<cl:messages>
					<cl:message type="error" message="${flash.message}" />
				</cl:messages>
			</g:if>

			<div class="body">

				<form action="${postUrl}" method="post" autocomplete="off">
					<cl:field label="Username" for="username">
						<input type="text" name="j_username" id="username"/>
					</cl:field>

					<cl:field label="Password" for="password">
						<input type="password" name="j_password" id="password"/>
					</cl:field>

					<cl:field label="Remember Me" for="remember_me">
						<input type="checkbox" name="${rememberMeParameter}" id="remember_me" <g:if test="${hasCookie}">checked="checked"</g:if>/>
					</cl:field>

					<cl:buttons>
						<input type="submit" id="submit" value="${message(code: 'springSecurity.login.button')}"/>
					</cl:buttons>
				</form>
			</div>
			<script type="text/javascript">
				(function() {
					document.forms['loginForm'].elements['j_username'].focus();
				})();
			</script>
		</div>
	</body>
</html>
