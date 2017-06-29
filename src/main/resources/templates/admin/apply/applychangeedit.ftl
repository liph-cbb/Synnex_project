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
                    <h5>调休申请审批</h5>
                </div>
                <div class="ibox-content">
                    <p>提交成功后，将通过邮件发送下属</p>
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
                    <form class="form-horizontal m-t" id="frm" method="post" action="${ctx!}/apply/addapprove">
                        <input type="hidden" id="id" name="applyid" value="${resource.applyid}" >
                        <input type="hidden" id="userid" name="userid" value="${resource.userid}" >
                        <input type="hidden" id="applytype" name="applytype" value="1">

                        <div class="form-group">
                            <label class="col-sm-3 control-label">开始时间：</label>
                            <div class="col-sm-8">
                                <input id="begindate" name="begindate" readonly="readonly"
                                       class="laydate-icon form-control" value="${resource.begindate}">
                            </div>
                        </div>


                        <div class="form-group">
                            <label class="col-sm-3 control-label">结束时间：</label>
                            <div class="col-sm-8">
                                <input id="enddate" name="enddate" readonly="readonly"
                                       class="laydate-icon form-control" value="${resource.enddate}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">调休时长：</label>
                            <div class="col-sm-8">
                                <input id="enddate" name="hours"
                                       class="form-control" value="${resource.hours}">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">调休原因说明：</label>
                            <div class="col-sm-8">
                                <textarea class="form-control" style="text-align: left" id="applyReason" name="applyReason" value="${resource.applyReason}" readonly="readonly">${resource.applyReason}
                                </textarea>
                            </div>
                        </div>

                        <div class="from-group">
                            <label class="col-sm-3 control-label">审批：</label>
                            <div class="col-sm-8">
                                <select name="applystatus" class="form-control">
                                    <option value="0" <#if resource.applystatus == 0>selected="selected"</#if>>未审批</option>
                                    <option value="1" <#if resource.applystatus == 1>selected="selected"</#if>>同意</option>
                                    <option value="2" <#if resource.applystatus == 2>selected="selected"</#if>>不同意</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">审批说明：</label>
                            <div class="col-sm-8">
                                <textarea class="form-control" id="approveReason" name="approveReason" value="${resource.approveReason}">${resource.approveReason}</textarea>
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
        $("#frm").validate({
            rules: {
                applystatus: {
                    required: true
                },
                hours: {
                    required: true
                },
                approveReason: {
                    required: true,
                    maxlength: 400
                }
            },
            messages: {},
            submitHandler: function (form) {
                $.ajax({
                    type: "POST",
                    dataType: "json",
                    url: "${ctx!}/apply/addapprove",
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
