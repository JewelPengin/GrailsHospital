<!doctype html>
<html>
	<head>
		<meta name="layout" content="main"/>
		<title>View Testing</title>
        <script>
            $(function(){
                $('#mytable').grid({}, []);
            });
        </script>
	</head>
	<body>
        <header>
            <nav id="sub-nav">
                <ul>
                    <li><a href="/">sub item 1</a></li>
                    <li><a href="/">sub item 2</a></li>
                    <li><a href="/">sub item 3</a></li>
                </ul>
            </nav>
            <h1>Page Title</h1>
        </header>

        <section>
            <h2>Heading 2 - Hello world</h2>
            <h3>Heading 3 - Hello world</h3>
            <h4>Heading 4 - Hello world</h4>
            <div id="mytable">
                <table class="reorderable" data-data="{}" data-url="/?i={{test}}">
                    <thead>
                    <tr>
                        <th>Heading 1 <span class="order asc"></span></th>
                        <th>Heading 2 <span class="order desc"></span></th>
                        <th>Heading 3 <span class="order asc"></span></th>
                    </tr>
                    </thead>

                    <tbody>
                    <tr class="even">
                        <td>data 1</td>
                        <td>data 2</td>
                        <td>data 3</td>
                    </tr>
                    <tr class="odd">
                        <td>data 4</td>
                        <td>data 5</td>
                        <td>data 6</td>
                    </tr>
                    <tr class="even">
                        <td>data 7</td>
                        <td>data 8</td>
                        <td>data 9</td>
                    </tr>
                    </tbody>

                    <tfoot>
                    <tr>
                        <td>footer 1</td>
                    </tr>
                    </tfoot>
                </table>
            </div>
        </section>

        <footer></footer>
	</body>
</html>
