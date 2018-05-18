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
    .ui.dimmer {
        background-color: #364150;
    }
    .title {
        font-size: 28px !important;
        font-weight: 400 !important;
    }
    .modal {
        height: 300px;
    }
    .title span{
        font-size: 16px;
        float: right;
    }
    .title a{
        font-size: 16px;
        float: right;
    }
</style>
<body>
<form id="form" action="${request.contextPath}/oauth/authorize" method='post'>
    <input id="approval" name='user_oauth_approval' value='false' type='hidden'/>
    <input id="approveOrDeny" name='authorize' value='Authorize' type='hidden'/>
    <input id="scope" name='scope.user' value='true' type='hidden'/>
</form>
<div class="ui modal" id="modal">
    <div class="header title">授权中心
        <a href="${request.contextPath}/logout">
            [退出]
        </a>
        <span>${userName} &nbsp;|&nbsp;&nbsp;</span>
    </div>
    <div class="content">
        <p style="line-height: 2.5">
            客户端应用：<br>
        <#list client as item>
            <a href="<#if item.webServerRedirectUri??>${item.webServerRedirectUri}<#else>javascript:void(0)</#if>">
                <i class="html5 icon"></i> ${item.name}
            </a><br>
        </#list>
        </p>
    </div>
</div>
</body>
<script src="${request.contextPath}/webjars/jquery/3.2.1/jquery.min.js" ></script>
<script src="${request.contextPath}/webjars/Semantic-UI/2.2.10/semantic.min.js" ></script>
<script>
    $("#modal").modal({closable: false}).modal('show')
    function logout() {
        window.location.replace(url)
    }
</script>
</html>