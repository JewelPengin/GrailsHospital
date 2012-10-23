<html>
	<head>
		<meta name="layout" content="main"/>
		<title>View Testing</title>
	</head>
	<body>
		<header>
			<nav id="sub-nav">
				<g:render template="/shared/nav" model="[nav: pageNav.children]" />
			</nav>
			<h1><%= pageNav.title %></h1>
		</header>

		<section>
			<cl:messages>
				<cl:message type="success">Lorem ipsum dolor sit amet, consectetur adipiscing elit.</cl:message>
				<cl:message type="error">Curabitur eros lacus, ultrices at fermentum at, varius malesuada enim.</cl:message>
				<cl:message type="warning">Ut imperdiet consectetur neque non interdum.</cl:message>
				<cl:message type="info">Cras viverra ante non nisl ultrices pretium porttitor augue aliquet.</cl:message>
			</cl:messages>

			<p>
				Phasellus consectetur magna eu <a href="#">nulla</a> pharetra et pharetra massa ornare.
				Aliquam luctus sapien id leo facilisis consectetur.
				Sed vitae erat velit, vel tincidunt dolor.
				Aenean aliquam, eros ac ornare viverra, mauris odio aliquet nunc, bibendum consequat nisi velit a mi.
				Proin ornare leo sed lectus dapibus vel feugiat ipsum aliquet.
				Vestibulum turpis libero, adipiscing nec feugiat sit amet, interdum sit amet libero.
				Etiam pharetra diam vitae mauris consectetur vitae convallis tortor rutrum.
				Suspendisse potenti.
			</p>

			<h2>Heading 2 - Hello world</h2>
			<h3>Heading 3 - Hello world</h3>
			<h4>Heading 4 - Hello world</h4>

			<cl:grid name="MyGrid" dataUrl="" reorderable="true" data="${[list: [[col1: 'data 1', col2: 'data 2', col3: 'data 3'], [col1: 'data 4', col2: 'data 5', col3: 'data 6'], [col1: 'data 7', col2: 'data 8', col3: 'data 9']], count: 3]}">
				<cl:column name="col1" label="Heading 1" />
				<cl:column name="col2" label="Heading 2" />
				<cl:column name="col3" label="Heading 3" />
			</cl:grid>

			<form method="post">
				<fieldset class="form">
					<div class="field-container">
						<div class="label">
							<label for="select">Select Box</label>
						</div>
						<div class="field">
							<select id="select" name="select">
								<option value="1">Item 1</option>
								<option value="2">Item 2</option>
								<option value="3">Item 3</option>
								<option value="4">Item 4</option>
								<option value="5">Item 5</option>
							</select>
						</div>
					</div>
					<div class="field-container">
						<div class="label">
							<label for="input">Input Box</label>
						</div>
						<div class="field">
							<input type="text" id="input" name="input">
						</div>
					</div>
					<div class="field-container">
						<div class="label">
							<label for="justhtml">Embedded HTML</label>
						</div>
						<div class="field">
							Nunc id tellus eget arcu luctus pulvinar quis vel ante.
						</div>
					</div>
					<div class="field-container">
						<div class="label">
							<label for="textbox">Text Area</label>
						</div>
						<div class="field">
							<textarea id="textbox">This is a text box of sorts.
Hello world.</textarea>
						</div>
					</div>

				</fieldset>
				<fieldset class="buttons">
					<input type="submit" name="create" class="button" value="Submit Button" id="create">
				</fieldset>
			</form>
		</section>

		<footer></footer>
	</body>
</html>
