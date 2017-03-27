<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="clong">

    <title>Static Top Navbar Example for Bootstrap</title>

    <!-- Bootstrap core CSS -->
    <link href="//cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">


    <!-- Custom styles for this template -->
<#--<link href="navbar-static-top.css" rel="stylesheet">-->


    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="http://cdn.bootcss.com/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>

<body>

<!-- Static navbar -->
<nav class="navbar navbar-default navbar-static-top">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                    data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">Project name</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav">
                <li><a href="/">Home</a></li>
                <li><a href="/issues">Issues</a></li>
                <li class="active"><a href="/chat">Chat</a></li>
            </ul>

        </div><!--/.nav-collapse -->
    </div>
</nav>


<div class="container wrapper">

    <!-- Main component for a primary marketing message or call to action -->
    <div class="panel panel-default">
       <div class="panel-heading">
           <h3 class="panel-title">#reactor on gitter</h3>
       </div>
        <div class="panel-body">
            <dl class="dl-horizontal" id="chat-messages"></dl>
        </div>
    </div>

</div> <!-- /container -->


<!-- Bootstrap core JavaScript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="//cdn.bootcss.com/jquery/3.1.0/jquery.min.js"></script>
<script src="//cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script type="text/javascript">
    $(function () {
        var appendMessages = function (message) {
            var chatZone = $("#chat-messages");
            chatZone.append("<dt>" + message.fromUser.displayName + "</dt>");
            chatZone.append("<dd>" + message.text + "</dd>");
        };

        $.ajax("/chatMessages")
                .done(function (messages) {
                    messages.forEach(function (msg) {
                        appendMessages(msg);
                    });
                });

        //SSE
        var chatEventSource = new EventSource("/chatStream");
        chatEventSource.onmessage = function (e) {
            appendMessages(JSON.parse(e.data));
        };

    });
</script>
</body>
</html>
