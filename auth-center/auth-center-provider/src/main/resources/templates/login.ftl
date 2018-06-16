<html>
<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
    <link rel="stylesheet" href="webjars/Semantic-UI/2.2.10/semantic.min.css"/>
</head>
<style type="text/css">
    body {
        background-color: #364150;
    }
    body > .grid {
        height: 100%;
    }
    .image {
        margin-top: -100px;
    }
    .column {
        max-width: 450px;
        height: 600px;
    }
    .title {
        color: #32c5d2;
        margin-bottom: 25px;
        margin-top: 15px !important;
        font-size: 28px;
        font-weight: 400;
    }
    .item {
        height: 60px;
    }
    input {
        height: 45px;
    }
    .other {
        height: 40px;
        float: right;
        padding-top: 10px;
        padding-right: 15px;
        color: #9e9e9e;
    }
</style>
<body>
<div class="ui middle aligned center aligned grid">
    <div class="column">
        <form id="form" class="ui large form" action="login" method="post" style="border: 0">
            <div class="ui stacked segment">
                <h3 class="title"> 授权中心 </h3>
                <div class="field item">
                    <div class="ui left icon input">
                        <i class="user icon"></i>
                        <input id="username" name="username" placeholder="用户名">
                        <input type="text" id="type" name="type" value="user" hidden>
                    </div>
                </div>
                <div class="field item" style="height: 80px">
                    <div class="ui left icon input">
                        <i class="lock icon"></i>
                        <input type="password" id="password" name="password" placeholder="密码">
                    </div>
                    <div class="other">忘记密码？试试 &nbsp;<a href="javascript:void(0);" onclick="change_yzm()" style="color: #32c5d2">验证码登陆</a>| <a style="color: #32c5d2">扫码登陆</a></div>
                </div>

                <div id="submit" class="ui fluid large teal submit button" style="margin-bottom: 20px; background-color: #32c5d2">登录</div>
            </div>
            <div class="ui error message"></div>
        </form>

        <form id="form1" class="ui large form" action="login" method="post" style="border: 0;display: none">
            <div class="ui stacked segment">
                <h3 class="title"> 授权中心 </h3>
                <div class="field item">
                    <div class="ui left icon input">
                        <i class="user icon"></i>
                        <input id="phone" name="phone" placeholder="手机号码">
                        <input type="text" id="type" name="type" value="phone" hidden>
                    </div>
                </div>
                <div class="field item" style="height: 80px">
                    <div class="ui right action left icon input">
                        <i class="lock icon"></i>
                        <input type="text" id="verifyCode" name="verifyCode" placeholder="验证码">
                        <button id="send_btn" type="button" class="ui basic active button" onclick="send_yzm()">点击获取验证码</button>
                    </div>
                    <div class="other">返回 &nbsp;<a href="javascript:void(0);" onclick="change_default()" style="color: #32c5d2">账号密码</a>| <a style="color: #32c5d2">扫码登陆</a></div>
                </div>

                <div id="submit1" class="ui fluid large teal submit button" style="margin-bottom: 20px; background-color: #32c5d2">登录</div>
            </div>

            <div class="ui error message"></div>

        </form>
        <#if RequestParameters.error?? && Session.SPRING_SECURITY_LAST_EXCEPTION??>
            <#if Session.SPRING_SECURITY_LAST_EXCEPTION.message == "Bad credentials">
                <div class="ui red message">
                    密码错误
                </div>
             <#elseif Session.SPRING_SECURITY_LAST_EXCEPTION.message == "Bad verifyCode">
                 <div class="ui red message">
                     验证码错误
                 </div>
            <#else>
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
    $('#form')
            .form({
                fields: {
                    username: {
                        identifier: 'username',
                        rules: [
                            {
                                type   : 'empty',
                                prompt : '请输入用户名'
                            }
                        ]
                    },
                    password: {
                        identifier: 'password',
                        rules: [
                            {
                                type   : 'empty',
                                prompt : '请输入密码'
                            }
                        ]
                    },
                }
            })
    ;

    $('#form1')
            .form({
                fields: {
                    phone: {
                        identifier  : 'phone',
                        rules: [
                            {
                                type   : 'regExp[/^1[0-9]{10}$/]',
                                prompt : '请输入正确手机号码'
                            }
                        ]
                    },
                    verifyCode: {
                        identifier  : 'verifyCode',
                        rules: [
                            {
                                type   : 'empty',
                                prompt : '请输入验证码'
                            },
                            {
                                type   : 'minLength[4]',
                                prompt : '请输入4位验证码'
                            },
                            {
                                type   : 'maxLength[4]',
                                prompt : '请输入4位验证码'
                            }
                        ]
                    }
                }
            })
    ;
    $("#submit").click(function () {
        if ($("#username").val() && $("#password").val()) {
            $("#form").submit();
        }
    })
    $("#submit1").click(function () {
        if ($("#phone").val() && $("#verifyCode").val()) {
            $("#form1").submit();
        }
    })
    function change_yzm() {
        $("#form").css("display", "none")
        $("#form1").css("display", "block")
    }
    function change_default() {
        $("#form1").css("display", "none")
        $("#form").css("display", "block")
    }
    function send_yzm() {
        //$('#form1').form('validate form', 'phone')
        if($('#form1').form('is valid', 'phone')) {
            // 发送验证码
            $("#send_btn").attr('class','ui disabled  button')
            $("#send_btn").text('已发送')
        }
    }
</script>
</html>