<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">


    <title> - 收件箱</title>
    <meta name="keywords" content="">
    <meta name="description" content="">

    <link rel="shortcut icon" href="favicon.ico"> <link href="${ctx!}/assets/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
    <link href="${ctx!}/assets/css/font-awesome.css?v=4.4.0" rel="stylesheet">
    <link href="${ctx!}/assets/css/plugins/iCheck/custom.css" rel="stylesheet">
    <link href="${ctx!}/assets/css/animate.css" rel="stylesheet">
    <link href="${ctx!}/assets/css/style.css?v=4.1.0" rel="stylesheet">

</head>

<body class="gray-bg">
    <div class="wrapper wrapper-content">
        <div class="row">
            <div class="col-sm-3">
                <div class="ibox float-e-margins">
                    <div class="ibox-content mailbox-content">
                        <div class="file-manager">
                            <a class="btn btn-block btn-primary compose-mail" href="mail_compose.html">写信</a>
                            <div class="space-25"></div>
                            <h5>文件夹</h5>
                            <ul class="folder-list m-b-md" style="padding: 0">
                                <li>
                                    <a href="mailbox.html"> <i class="fa fa-inbox "></i> 收件箱 <span class="label label-warning pull-right">16</span>
                                    </a>
                                </li>
                                <li>
                                    <a href="mailbox.html"> <i class="fa fa-envelope-o"></i> 发信</a>
                                </li>
                                <li>
                                    <a href="mailbox.html"> <i class="fa fa-certificate"></i> 重要</a>
                                </li>
                                <li>
                                    <a href="mailbox.html"> <i class="fa fa-file-text-o"></i> 草稿 <span class="label label-danger pull-right">2</span>
                                    </a>
                                </li>
                                <li>
                                    <a href="mailbox.html"> <i class="fa fa-trash-o"></i> 垃圾箱</a>
                                </li>
                            </ul>
                            <h5>分类</h5>
                            <ul class="category-list" style="padding: 0">
                                <li>
                                    <a href="mail_compose.html#"> <i class="fa fa-circle text-navy"></i> 工作</a>
                                </li>
                                <li>
                                    <a href="mail_compose.html#"> <i class="fa fa-circle text-danger"></i> 文档</a>
                                </li>
                                <li>
                                    <a href="mail_compose.html#"> <i class="fa fa-circle text-primary"></i> 社交</a>
                                </li>
                                <li>
                                    <a href="mail_compose.html#"> <i class="fa fa-circle text-info"></i> 广告</a>
                                </li>
                                <li>
                                    <a href="mail_compose.html#"> <i class="fa fa-circle text-warning"></i> 客户端</a>
                                </li>
                            </ul>

                            <h5 class="tag-title">标签</h5>
                            <ul class="tag-list" style="padding: 0">
                                <li><a href="mail_compose.html"><i class="fa fa-tag"></i> 朋友</a>
                                </li>
                                <li><a href="mail_compose.html"><i class="fa fa-tag"></i> 工作</a>
                                </li>
                                <li><a href="mail_compose.html"><i class="fa fa-tag"></i> 家庭</a>
                                </li>
                                <li><a href="mail_compose.html"><i class="fa fa-tag"></i> 孩子</a>
                                </li>
                                <li><a href="mail_compose.html"><i class="fa fa-tag"></i> 假期</a>
                                </li>
                                <li><a href="mail_compose.html"><i class="fa fa-tag"></i> 音乐</a>
                                </li>
                                <li><a href="mail_compose.html"><i class="fa fa-tag"></i> 照片</a>
                                </li>
                                <li><a href="mail_compose.html"><i class="fa fa-tag"></i> 电影</a>
                                </li>
                            </ul>
                            <div class="clearfix"></div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-sm-9 animated fadeInRight">
                <div class="mail-box-header">

                    <form method="get" action="index.html" class="pull-right mail-search">
                        <div class="input-group">
                            <input type="text" class="form-control input-sm" name="search" placeholder="搜索邮件标题，正文等">
                            <div class="input-group-btn">
                                <button type="submit" class="btn btn-sm btn-primary">
                                    搜索
                                </button>
                            </div>
                        </div>
                    </form>
                    <h2>
                    收件箱 (16)
                </h2>
                    <div class="mail-tools tooltip-demo m-t-md">
                        <div class="btn-group pull-right">
                            <button class="btn btn-white btn-sm"><i class="fa fa-arrow-left"></i>
                            </button>
                            <button class="btn btn-white btn-sm"><i class="fa fa-arrow-right"></i>
                            </button>

                        </div>
                        <button class="btn btn-white btn-sm" data-toggle="tooltip" data-placement="left" title="刷新邮件列表"><i class="fa fa-refresh"></i> 刷新</button>
                        <button class="btn btn-white btn-sm" data-toggle="tooltip" data-placement="top" title="标为已读"><i class="fa fa-eye"></i>
                        </button>
                        <button class="btn btn-white btn-sm" data-toggle="tooltip" data-placement="top" title="标为重要"><i class="fa fa-exclamation"></i>
                        </button>
                        <button class="btn btn-white btn-sm" data-toggle="tooltip" data-placement="top" title="标为垃圾邮件"><i class="fa fa-trash-o"></i>
                        </button>

                    </div>
                </div>
                <div class="mail-box">

                    <table class="table table-hover table-mail">
                        <tbody>

<#--                            <tr class="unread">
                                <td class="mail-ontact"><a href="mail_detail.html">支付宝</a>
                                </td>
                                <td class="mail-subject"><a href="mail_detail.html">支付宝提醒</a>
                                </td>
                                <td class=""><i class="fa fa-paperclip"></i>
                                </td>
                                <td class="text-right mail-date">昨天 10:20</td>
                            </tr>
                            <tr class="unread">
                                <td class="mail-ontact"><a href="mail_detail.html">Amaze UI</a>
                                </td>
                                <td class="mail-subject"><a href="mail_detail.html">Amaze UI Beta2 发布</a>
                                </td>
                                <td class=""></td>
                                <td class="text-right mail-date">上午10:57</td>
                            </tr>-->
                            <#list list as r>
                                <tr class="unread">
                                    <td class="mail-ontact"><a href="mail_detail.html">${r.sendfrom}</a>
                                    </td>
                                    <td class="mail-subject"><a href="mail_detail.html">${r.subject}</a>
                                    </td>
                                    <td class="text-right mail-date"><a href="mail_detail.html">${r.sendtime}</a>
                                    </td>
                                </tr>
                            </#list>
                    </table>


                </div>
            </div>
        </div>
    </div>


    <!-- 全局js -->
    <script src="${ctx!}/assets/js/jquery.min.js?v=2.1.4"></script>
    <script src="${ctx!}/assets/js/bootstrap.min.js?v=3.3.6"></script>



    <!-- 自定义js -->
    <script src="${ctx!}/assets/js/content.js?v=1.0.0"></script>


    <!-- iCheck -->
    <script src="${ctx!}/assets/js/plugins/iCheck/icheck.min.js"></script>
    <script>
        $(document).ready(function () {
            $('.i-checks').iCheck({
                checkboxClass: 'icheckbox_square-green',
                radioClass: 'iradio_square-green',
            });
        });


    </script>

    
    

</body>

</html>
