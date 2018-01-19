<!DOCTYPE html>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/include/header.inc"%>
<html>
<head>
<%@ include file="/include/csslib.jsp/"%>
</head>
<body>
	<form action="role/save.do" name="Form" id="Form" method="post">
		<input type="hidden" name="id" id="id" value="${id}"/>
		<input type="hidden" name="pid" id="pid" value="${pid}"/>
		<table id="table_report" class="table table-striped table-bordered table-hover">
			<tr>
				<td style="width:140px;text-align: right;vertical-align: middle;">角色名称:</td>
				<td><input type="text" name="role_name" id="role_name" value="${pd.role_name}" maxlength="32" placeholder="这里输入角色名称" title="角色名称" style="margin-bottom: 0px"/></td>
			</tr>
			<tr>
				<td style="text-align: right;" colspan="10">
					<a class="btn btn-mini btn-primary" id="savebtn" onclick="save();">保存</a>
					<a class="btn btn-mini btn-danger" onclick="frameElement.api.close();">取消</a>
				</td>
			</tr>
		</table>
	</form>
<%@ include file="/include/jslib.jsp/"%>
<script type="text/javascript">
	var api = frameElement.api, W = api.opener;
	//保存
	function save(){
		if($("#role_name").val()==""){
			$("#role_name").tips({
				side:3,
	            msg:"请输入角色名称",
	            bg:'#AE81FF',
	            time:1
	        });
			$("#role_name").focus();
			return false;
		}
		$("#savebtn").attr("disabled", "disabled");
		$.ajax({
			type: "POST",
			url: "${ctx}role/save.action",
			data: $("#Form").serialize(),
			dataType:"json",
			cache: false,
			success: function(data){
				W.$.dialog.alert("保存成功！",function(){
					api.reload();
				}); 
			},
			error: function(request) {
                W.$.dialog.alert("程序错误！",function(){
						$("#savebtn").removeAttr("disabled");
					});
            },
		});
	}
</script>
</body>
</html>