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
            <li><span class="icon icon-info"></span>
                <ul class="notifications">
                    <li>
                        <div class="message-icon">
                            <span class="icon icon-invalid"></span>
                        </div>
                        <div class="message-text">
                            <a href="/">This is a long message to remind someone that something is wrong and they need to do address it.</a>
                        </div>
                    </li>
                    <li>
                        <div class="message-icon">
                            <span class="icon icon-info"></span>
                        </div>
                        <div class="message-text">
                            <a href="/">Today is <%= new Date() %>.</a>
                        </div>
                    </li>
                    <li>
                        <div class="message-icon">
                            <span class="icon icon-valid"></span>
                        </div>
                        <div class="message-text">
                            <a href="/">Congratulations! Something was successfully set-up!</a>
                        </div>
                    </li>
                    <li>
                        <div class="message-icon">
                            <span class="icon icon-invalid"></span>
                        </div>
                        <div class="message-text">
                            <a href="/">This is a long message to remind someone that something is wrong and they need to do address it.</a>
                        </div>
                    </li>
                    <li>
                        <div class="message-icon">
                            <span class="icon icon-info"></span>
                        </div>
                        <div class="message-text">
                            <a href="/">Today is <%= new Date() %>.</a>
                        </div>
                    </li>
                    <li>
                        <div class="message-icon">
                            <span class="icon icon-valid"></span>
                        </div>
                        <div class="message-text">
                            <a href="/">Congratulations! Something was successfully set-up!</a>
                        </div>
                    </li>
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