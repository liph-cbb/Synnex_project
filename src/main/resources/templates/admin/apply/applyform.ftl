<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">


    <title> -加班申请</title>
    <meta name="keywords" content="">
    <meta name="description" content="">

    <link rel="shortcut icon" href="favicon.ico">
    <link href="${ctx!}/assets/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
    <link href="${ctx!}/assets/css/font-awesome.css?v=4.4.0" rel="stylesheet">
    <link href="${ctx!}/assets/css/animate.css" rel="stylesheet">
    <link href="${ctx!}/assets/css/style.css?v=4.1.0" rel="stylesheet">
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.10.0/css/bootstrap-select.min.css">
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>申请加班</h5>
                </div>
                <div class="ibox-content">
                    <p>提交成功后，将通过邮件发送上级领导</p>
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>完整验证表单</h5>
                </div>
                <div class="ibox-content">
                    <form class="form-horizontal m-t" id="frm" method="post" action="${ctx!}/apply/edit">
                        <input type="hidden" id="id" name="applyid" value="${resource.applyid}" >
                        <div class="form-group">
                            <label class="col-sm-3 control-label">开始时间：</label>
                            <div class="col-sm-8">
                                <input id="begindate" name="begindate" readonly="readonly"
                                       class="laydate-icon form-control layer-date" value="${resource.begindate}">
                            </div>
                        </div>


                        <div class="form-group">
                            <label class="col-sm-3 control-label">结束时间：</label>
                            <div class="col-sm-8">
                                <input id="enddate" name="enddate" readonly="readonly"
                                       class="laydate-icon form-control layer-date" value="${resource.enddate}">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">发送邮箱：</label>
                            <div class="col-sm-8">
                                <select name="emails" id="emails" class="selectpicker show-tick form-control" multiple
                                        data-live-search="false" value="${resource.emails}">
                                <#list list as r>
                                    <option value="${r.email}">
                                    ${r.nickName}
                                    </option>
                                </#list>
                                </select>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">加班原因说明：</label>
                            <div class="col-sm-8">
                                <textarea class="form-control" id="applyReason" name="applyReason" value="${resource.applyReason}">
                                ${resource.applyReason}
                                </textarea>
                            </div>
                        </div>

                        <div class="form-group">
                            <div class="col-sm-8 col-sm-offset-3">
                                <button class="btn btn-primary" type="submit">提交</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

</div>


<!-- 全局js -->
<script src="${ctx!}/assets/js/jquery.min.js?v=2.1.4"></script>
<script src="${ctx!}/assets/js/bootstrap.min.js?v=3.3.6"></script>

<!-- 自定义js -->
<script src="${ctx!}/assets/js/content.js?v=1.0.0"></script>

<!-- jQuery Validation plugin javascript-->
<script src="${ctx!}/assets/js/plugins/validate/jquery.validate.min.js"></script>
<script src="${ctx!}/assets/js/plugins/validate/messages_zh.min.js"></script>
<script src="${ctx!}/assets/js/plugins/layer/layer.min.js"></script>
<script src="${ctx!}/assets/js/plugins/layer/laydate/laydate.js"></script>

<!-- Latest compiled and minified JavaScript -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.10.0/js/bootstrap-select.min.js"></script>

<script type="text/javascript">
    $(document).ready(function () {
        //外部js调用
        /*        laydate({
                    elem: '#birthday', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
                    event: 'focus' //响应事件。如果没有传入event，则按照默认的click
                });*/
        var start = {
            elem: '#begindate',
            format: 'YYYY/MM/DD hh:mm:ss',
            min: laydate.now(), //设定最小日期为当前日期
            max: '2099-06-16 23:59:59', //最大日期
            istime: true,
            istoday: false,
            choose: function (datas) {
                end.min = datas; //开始日选好后，重置结束日的最小日期
                end.start = datas //将结束日的初始值设定为开始日
            }
        };

        var end = {
            elem: '#enddate',
            format: 'YYYY/MM/DD hh:mm:ss',
            min: laydate.now(),
            max: '2099-06-16 23:59:59',
            istime: true,
            istoday: false,
            choose: function (datas) {
                start.max = datas; //结束日选好后，重置开始日的最大日期
            }
        };
        laydate(start);
        laydate(end);
        $("#frm").validate({
            rules: {
                begindate: {
                    required: true
                },
                enddate: {
                    required: true
                },

                emails: {
                    required: true
                },
                applyReason: {
                    required: true,
                    maxlength: 400
                }
            },
            messages: {},
            submitHandler: function (form) {
                $.ajax({
                    type: "POST",
                    dataType: "json",
                    url: "${ctx!}/apply/edit",
                    data: $(form).serialize(),
                    success: function (msg) {
                        layer.msg(msg.message, {time: 2000}, function () {
                            var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                            parent.layer.close(index);
                        });
                    }
                });
            }
        });
    });
</script>

</body>

</html>
