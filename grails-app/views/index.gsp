<html>
    <head>
        <meta name="layout" content="main"/>
        <title>View Testing</title>
        <script type="text/javascript">
            function callback(response) {
              if (response.status == 200) {
                var data = response.responseBody;
                if (data.length > 0) {
                    console.log(data);
                }
              }
            }

            var url = 'atmosphere/sample';
            $.atmosphere.subscribe(url, callback, $.atmosphere.request = {transport:'streaming'});
        </script>
    </head>
    <body>
        <header>
            <nav id="sub-nav">
                <g:render template="/shared/nav" model="[nav: pageNav.children]" />
            </nav>
            <h1><%= pageNav.title %></h1>
        </header>

        <section>
            <h2>Heading 2 - Hello world</h2>
            <h3>Heading 3 - Hello world</h3>
            <h4>Heading 4 - Hello world</h4>
            <cl:grid name="MyGrid" dataUrl="" reorderable="true" data="${[list: [[col1: 'data 1', col2: 'data 2', col3: 'data 3'], [col1: 'data 4', col2: 'data 5', col3: 'data 6'], [col1: 'data 7', col2: 'data 8', col3: 'data 9']], count: 3]}">
                <cl:column name="col1" label="Heading 1" />
                <cl:column name="col2" label="Heading 2" />
                <cl:column name="col3" label="Heading 3" />
            </cl:grid>
        </section>

        <footer></footer>
    </body>
</html>
