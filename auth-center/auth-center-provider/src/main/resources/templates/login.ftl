<html>
<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
    <link rel="stylesheet" href="webjars/Semantic-UI/2.2.10/semantic.min.css"/>
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
        <h2 class="ui teal image header">
            <img src="images/logo.png" class="image">
            <div class="content">
                登录到账号
            </div>
        </h2>
        <form id="form" class="ui large form" action="login" method="post">
            <div class="ui stacked segment">
                <div class="field">
                    <div class="ui left icon input">
                        <i class="user icon"></i>
                        <input id="username" name="username" placeholder="邮箱地址">
                    </div>
                </div>
                <div class="field">
                    <div class="ui left icon input">
                        <i class="lock icon"></i>
                        <input type="password" id="password" name="password" placeholder="密码">
                    </div>
                </div>
                <div id="submit" class="ui fluid large teal submit button">登录</div>
            </div>

            <div class="ui error message"></div>

        </form>
        <#if RequestParameters.error?? && Session.SPRING_SECURITY_LAST_EXCEPTION??>
            <#if Session.SPRING_SECURITY_LAST_EXCEPTION.message == "Bad credentials">
                <div class="ui red message">
                    密码错误
                </div>
            <#else >
                <div class="ui red message">
                    ${Session.SPRING_SECURITY_LAST_EXCEPTION.message}
                </div>
            </#if>
        </#if>
    </div>
</div>
</body>
<script src="webjars/jquery/3.2.1/jquery.min.js" ></script>
<script src="webjars/Semantic-UI/2.2.10/semantic.min.js" ></script>
<script>
    $("#submit").click(function () {
        if ($("#username").val() && $("#password").val()) {
            $("#form").submit();
        }
    })
</script>
</html>