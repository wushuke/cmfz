<%@page isELIgnored="false" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set value="${pageContext.request.contextPath}" var="app"></c:set>
<script type="text/javascript">
    $(function () {
        $("#bannerTable").jqGrid(
            {
                url : "${app}/banner/page",
                datatype : "json",
                colNames : [ 'ID', '标题', '路径','日期','描述','状态'],
                colModel : [
                    {name : 'id',index : 'id',width : 140},
                    {name : 'title',index : 'invdate',width : 140,editable:true},
                    {name : 'url',editable:true,edittype:"file",editoptions: {enctype:"multipart/form-data"},formatter:function (cellvalue, options, rowObject) {
                           return "<img src='"+cellvalue+"' style='width:100px;height:60px'>";

                        }},
                    {name : 'create_date',index : 'id',width : 140 ,editable:true,edittype: "date"},
                    {name : 'descript',index : 'id',width : 140 ,editable:true},
                    {name : 'status',align:"center",formatter:function (data) {
                            if (data=="1"){
                                return "展示";
                            } else return "冻结";
                        },editable:true,editrules:{required:true},edittype:"select",editoptions: {value:"1:展示;2:冻结"}}
                ],
                rowNum : 2,
                rowList : [ 2, 3, 4 ],
                pager : '#bannerPage',
                sortname : 'id',
                mtype : "post",
                viewrecords : true,
                editurl:"${app}/banner/save",
                sortorder : "desc",
                styleUI: "Bootstrap",
                height:"280px",
                autowidth: true,
                multiselect:true,
                caption : "JSON 实例"
            });
        jQuery("#bannerTable").jqGrid('navGrid', '#bannerPage',{edit: true, add: true, del: true},
            // 制定修改|添加|删除 之前 之后的事件
            {
                closeAfterEdit: true,
                afterSubmit:function (response,postData) {
                    var bannerId = response.responseJSON.bannerId;
                    $.ajaxFileUpload({
                        // 指定上传路径
                        url:"${app}/banner/upload",
                        type:"post",
                        datatype:"json",
                        // 发送添加图片的id至controller
                        data:{bannerId:bannerId},
                        // 指定上传的input框id
                        fileElementId:"url",
                        success:function (data) {
                            $("#bannerTable").trigger("reloadGrid");
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
                    var bannerId = response.responseJSON.bannerId;
                    $.ajaxFileUpload({
                        // 指定上传路径
                        url:"${app}/banner/upload",
                        type:"post",
                        datatype:"json",
                        // 发送添加图片的id至controller
                        data:{bannerId:bannerId},
                        // 指定上传的input框id
                        fileElementId:"url",
                        success:function (data) {
                        }
                    });
                    // 防止页面报错
                    return postData;
                }
            },{
                closeAfterDel: true,
            });
    });
</script>
<div class="page-header">
    <h4>轮播图管理</h4>
</div>
<ul class="nav nav-tabs">
    <li><a>轮播图信息</a></li>

</ul>
<table id="bannerTable"></table>


<div id="bannerPage" style="height: 50px"></div>