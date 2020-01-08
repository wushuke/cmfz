<%@page isELIgnored="false" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set value="${pageContext.request.contextPath}" var="app"></c:set>
<script type="text/javascript">
    $(function () {
        $("#usertable").jqGrid({
            url: "${app}/user/userPage",
            datatype: "json",
            colNames: ['id', '电话', '密码', '头像', '名字', '昵称','性别','地区','注册时间'],
            colModel: [
                {name: 'id'},
                {name: 'phone',editable:true},
                {
                    name: 'password',editable:true
                },
                {name: 'photo',edittype:"file",editable:true,
                    editoptions: {enctype:"multipart/form-data"},formatter: function (cellvalue, options, rowObject) {
                        return "<img src='"+ cellvalue + "' style='width:100px;height:60px'>";
                    }
                },
                {
                    name: 'name',editable:true
                },
                {
                    name: 'nickName',editable:true

                },
                {name:'sex',editable:true,editrules:{required:true},edittype:"select",editoptions:{value:"1:男;2:女"},
                    formatter:function(data){
                        if(data=="1"){
                            return "男";
                        }else{
                            return "女";
                        }
                    }
                },
                {name:'location',editable:true},
                {name:'rigestDate'}
            ],
            autowidth: true,
            pager: "#userPage",
            rowNum: 2,
            rowList: [2, 4, 6],
            viewrecords: true,
            caption: "用户",
            height:"400px",
            styleUI: "Bootstrap",
            multiselect:true,
            editurl: "${app}/user/save"

        });
        jQuery("#usertable").jqGrid('navGrid', '#userPage',{edit: true, add: true, del: true},
            // 制定修改|添加|删除 之前 之后的事件
            {
                closeAfterEdit: true,
                afterSubmit:function (response,postData) {
                    var userId = response.responseJSON.userId;
                    $.ajaxFileUpload({
                        // 指定上传路径
                        url:"${app}/user/upload",
                        type:"post",
                        datatype:"json",
                        // 发送添加图片的id至controller
                        data:{userId:userId},
                        // 指定上传的input框id
                        fileElementId:"photo",
                        success:function (data) {
                            $("#usertable").trigger("reloadGrid");
                        }
                    });
                    // 防止页面报错
                    return postData;
                }
            },{
                closeAfterAdd: true,
                // 数据库添加轮播图后 进行上传 上传完成后需更改url路径 需要获取添加轮播图的Id
                //                   editurl 完成后 返回值信息
                afterSubmit:function (response,postData) {
                    var userId = response.responseJSON.userId;
                    $.ajaxFileUpload({
                        // 指定上传路径
                        url:"${app}/user/upload",
                        type:"post",
                        datatype:"json",
                        // 发送添加图片的id至controller
                        data:{userId:userId},
                        // 指定上传的input框id
                        fileElementId:"photo",
                        success:function (data) {
                            $("#usertable").trigger("reloadGrid");
                        }
                    });
                    // 防止页面报错
                    return postData;
                }
            },{
                closeAfterDel: true,
            });
    });


    function poiExe(){
        $.ajax({
            url:"${app}/user/imageUpload",
            type:"post",
            datatype: "json",
            success:function (){
                $("#usertable").trigger("reloadGrid");
            }
        })
    }

    function lead(){
        $.ajax({
            url:"${app}/user/leadExcel",
            type:"post",
            datatype: "json",
            success:function (){
                $("#usertable").trigger("reloadGrid");
            }
        })
    }
</script>

<ul class="nav nav-tabs">
    <li><a>用户信息</a></li>
    <li><a onclick="poiExe()">导出</a></li>
    <li><a onclick="lead()">导入</a></li>
</ul>

<table id="usertable"></table>
<div id="userPage" style="height: 50px"></div>