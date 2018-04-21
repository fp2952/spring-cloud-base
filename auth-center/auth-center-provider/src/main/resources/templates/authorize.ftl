<html>
<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
    <link rel="stylesheet" href="${request.contextPath}/webjars/Semantic-UI/2.2.10/semantic.min.css"/>
</head>
<style type="text/css">
    body {
        background-color: #DADADA;
    }
    body > .grid {
        height: 100%;
    }
    .image {
        margin-top: -100px;
    }
    .column {
        max-width: 450px;
    }
</style>
<body>
<div class="ui middle aligned center aligned grid">
    <div class="column">
        <div class="ui large form">
            <div class="ui stacked segment">
                <div class="ui grid">
                    <div class="left aligned sixteen wide column">
                        将允许应用 ${authorizationRequest.clientId} 进行以下操作:<br>
                        <i class="user icon"></i>获取你的用户信息
                    </div>
                    <div class="sixteen wide column">
                        <button id="ok" class="ui primary button">允许 </button>
                        <button id="no" class="ui button">拒绝 </button>
                    </div>
                    <form id="form" action="${request.contextPath}/oauth/authorize" method='post'>
                        <input id="approval" name='user_oauth_approval' value='false' type='hidden'/>
                        <input id="approveOrDeny" name='authorize' value='Authorize' type='hidden'/>
                        <input id="scope" name='scope.user' value='true' type='hidden'/>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script src="${request.contextPath}/webjars/jquery/3.2.1/jquery.min.js" ></script>
<script src="${request.contextPath}/webjars/Semantic-UI/2.2.10/semantic.min.js" ></script>
<script>
    $("#ok").click(function () {
        $("#approval").val("true");
        $("#approveOrDeny").attr("name", "authorize").val("Authorize");
        $("#form").submit();
    })

    $("#no").click(function () {
        $("#approval").val("false");
        $("#approveOrDeny").attr("name", "deny").val("Deny");
        $("#scope").val("false");
        $("#form").submit();
    })
</script>
</html>