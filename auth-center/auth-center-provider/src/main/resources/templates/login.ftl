<html>
<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
    <link rel="stylesheet" href="webjars/Semantic-UI/2.2.10/semantic.min.css"/>
</head>
<style type="text/css">
    body {
        /*background-color: #364150;*/
        background-image: url("static/img/background.jpg");
        background-repeat: no-repeat;
        background-size: 100% 100%;
    }
    body > .grid {
        height: 100%;
    }
    .grid{
        opacity:0.8
    }
    .image {
        margin-top: -100px;
    }
    .column {
        max-width: 450px;
        height: 600px;
    }
    .title {
        color: #2b4035;
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
    a {
        color: #32c5d2
    }
    .login{
        margin-bottom: 20px;
        background-color: #47b77d
    }
</style>
<body>
<div class="ui middle aligned center aligned grid">
    <div class="column transition hidden">
        <form id="form" class="ui large form" action="login" method="post" style="border: 0">
            <div class="ui stacked segment">
                <h3 class="title"> 授权中心 </h3>
                <div class="field item">
                    <div class="ui left icon input">
                        <i class="user icon"></i>
                        <input id="username" name="username" placeholder="用户名" value="test">
                    </div>
                </div>
                <div class="field item" style="height: 80px">
                    <div class="ui left icon input">
                        <i class="lock icon"></i>
                        <input type="password" id="password" name="password" placeholder="密码" value="123456">
                    </div>
                    <div class="other">忘记密码？试试 &nbsp;<a href="javascript:void(0);" onclick="change_yzm()"><i class="mobile icon"></i>验证码登陆</a>|<a><i class="qrcode icon"></i>扫码登陆</a></div>
                </div>

                <div id="submit" class="ui fluid large teal submit button login">登录</div>
            </div>
            <div class="ui error message"></div>
        </form>

        <form id="form1" class="ui large form transition hidden" action="phoneLogin" method="post" style="border: 0;">
            <div class="ui stacked segment">
                <h3 class="title"> 授权中心 </h3>
                <div class="field item">
                    <div class="ui left icon input">
                        <i class="mobile icon"></i>
                        <input id="phone" name="phone" placeholder="手机号码" value="13100000000">
                    </div>
                </div>
                <div class="field item" style="height: 80px">
                    <div class="ui right action left icon input">
                        <i class="lock icon"></i>
                        <input type="text" id="verifyCode" name="verifyCode" placeholder="验证码" value="1000">
                        <button id="send_btn" type="button" class="ui basic active button" onclick="send_yzm()">点击获取验证码</button>
                    </div>
                    <div class="other">返回 &nbsp;<a href="javascript:void(0);" onclick="change_default()"><i class="user icon"></i>账号密码</a>| <a><i class="qrcode icon"></i>扫码登陆</a></div>
                </div>

                <div id="submit1" class="ui fluid large teal submit button login">登录</div>
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
        <div class="ui warning message transition hidden">
            <i class="close icon"></i>
            <div class="header">请输入正确的手机号码！</div>
        </div>
    </div>
</div>
<div style="text-align: center">
    <a href="http://www.miitbeian.gov.cn/" target="_blank">粤ICP备18027057号</a>
</div>
</body>
<script src="webjars/jquery/3.2.1/jquery.min.js" ></script>
<script src="webjars/Semantic-UI/2.2.10/semantic.min.js" ></script>
<script>
    $('.column')
            .transition({
                animation  : 'slide left',
                duration   : '1s'
            })
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
    /**
     * 账号和验证码切换，点击过快可能渲染出错，加锁
     * @type {boolean}
     */
    var lock = false;
    function change_yzm() {
        if(lock) return
        $("#form1").transition({
            animation: 'slide left',
            duration   : '1s',
            onStart: function () {
                $("#form").removeClass("visible")
                $("#form").css("display", "none")
            },
            onComplete:function () {
                $("#form").addClass('transition hidden')
                lock = true
            }
        })
    }
    function change_default() {
        if(!lock) return
        $("#form").transition({
            animation: 'slide left',
            duration   : '1s',
            onStart: function () {
                $("#form1").removeClass("visible")
                $("#form1").css("display", "none")
            },
            onComplete:function () {
                $("#form1").addClass('transition hidden')
                lock = false
            }
        })
    }
    // 发送验证码
    function send_yzm() {
        if($('#form1').form('is valid', 'phone')) {
            // 发送验证码
            $("#send_btn").attr('class','ui disabled  button')
            //$("#send_btn").text('已发送')
            buttonCountdown($('#send_btn'), 1000 * 60, "m:s");
        } else {
            $('.warning')
                    .transition({
                        animation  : 'fade',
                        duration   : '1s'
                    })
        }
    }
    $('.message .close')
            .on('click', function() {
                $(this)
                        .closest('.message')
                        .transition('fade')
                ;
            })



    //jquery倒计时按钮常用于验证码倒计
    function buttonCountdown($el, msNum, timeFormat) {
        var text = $el.data("text") || $el.text(),
                timer = 0;
        $el.prop("disabled", true).addClass("disabled")
                .on("bc.clear", function () {
                    clearTime();
                });

        (function countdown() {
            var time = showTime(msNum)[timeFormat];
            $el.text(time + '后重发');
            if (msNum <= 0) {
                msNum = 0;
                clearTime();
            } else {
                msNum -= 1000;
                timer = setTimeout(arguments.callee, 1000);
            }
        })();

        function clearTime() {
            clearTimeout(timer);
            $el.prop("disabled", false).removeClass("disabled").text(text);
        }

        function showTime(ms) {
            var d = Math.floor(ms / 1000 / 60 / 60 / 24),
                    h = Math.floor(ms / 1000 / 60 / 60 % 24),
                    m = Math.floor(ms / 1000 / 60 % 60),
                    s = Math.floor(ms / 1000 % 60),
                    ss = Math.floor(ms / 1000);

            return {
                d: d + "天",
                h: h + "小时",
                m: m + "分",
                ss: ss + "秒",
                "d:h:m:s": d + "天" + h + "小时" + m + "分" + s + "秒",
                "h:m:s": h + "小时" + m + "分" + s + "秒",
                "m:s": m + "分" + s + "秒"
            };
        }

        return this;
    }
</script>
</html>