<!DOCTYPE html>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/include/header.inc"%>
<html>
<head>
<%@ include file="/include/csslib.jsp/"%>
</head>
<body>
	<form action="user/save.do" name="Form" id="Form" method="post">
		<input type="hidden" name="id" id="id" value="${pd.id}"/>
		<table id="table_report" class="table table-striped table-bordered table-hover">
			<tr>
				<td style="width:120px;text-align: right;vertical-align: middle;">用户名:</td>
				<td><input type="text" name="user_name" id="user_name" value="${pd.user_name}" maxlength="32" placeholder="这里输入用户名" title="用户名" style="margin-bottom: 0px"/></td>
			</tr>
			<tr>
				<td style="width:120px;text-align: right;vertical-align: middle;">姓名昵称:</td>
				<td><input type="text" name="name" id="name" value="${pd.name}" maxlength="32" placeholder="这里输入姓名昵称" title="姓名昵称" style="margin-bottom: 0px"/></td>
			</tr>
			<tr>
				<td style="width:120px;text-align: right;vertical-align: middle;">角色ID:</td>
				<td><input type="text" name="role_id" id="role_id" value="${pd.role_id}" maxlength="32" placeholder="这里输入角色ID" title="角色ID" style="margin-bottom: 0px"/></td>
			</tr>
			<tr>
				<td style="width:120px;text-align: right;vertical-align: middle;">ip地址:</td>
				<td><input type="text" name="ip" id="ip" value="${pd.ip}" maxlength="32" placeholder="这里输入ip地址" title="ip地址" style="margin-bottom: 0px"/></td>
			</tr>
			<tr>
				<td style="width:120px;text-align: right;vertical-align: middle;">皮肤:</td>
				<td><input type="text" name="skin" id="skin" value="${pd.skin}" maxlength="32" placeholder="这里输入皮肤" title="皮肤" style="margin-bottom: 0px"/></td>
			</tr>
			<tr>
				<td style="width:120px;text-align: right;vertical-align: middle;">邮箱:</td>
				<td><input type="text" name="email" id="email" value="${pd.email}" maxlength="32" placeholder="这里输入邮箱" title="邮箱" style="margin-bottom: 0px"/></td>
			</tr>
			<tr>
				<td style="width:120px;text-align: right;vertical-align: middle;">电话:</td>
				<td><input type="text" name="phone" id="phone" value="${pd.phone}" maxlength="32" placeholder="这里输入电话" title="电话" style="margin-bottom: 0px"/></td>
			</tr>
			<tr>
				<td style="text-align: center;" colspan="10">
					<a class="btn btn-mini btn-primary" id="savebtn" onclick="save();">保存</a>
					<a class="btn btn-mini btn-danger" onclick="frameElement.api.close();">取消</a>
				</td>
			</tr>
		</table>
	</form>
<%@ include file="/include/jslib.jsp/"%>
<script type="text/javascript">
	var api = frameElement.api, W = api.opener;
	$(function() {
		if("${otype}" == "look"){
			$("#table_report").find(":input").each(function(){
				$(this).parent().html($(this).val());
			});
			
			$("#savebtn").hide();
		}
	});

	//保存
	function save(){
		if($("#user_name").val()==""){
			$("#user_name").tips({
				side:3,
	            msg:'请输入用户名',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#user_name").focus();
			return false;
		}
		if($("#password").val()==""){
			$("#password").tips({
				side:3,
	            msg:'请输入密码',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#password").focus();
			return false;
		}
		if($("#name").val()==""){
			$("#name").tips({
				side:3,
	            msg:'请输入姓名昵称',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#name").focus();
			return false;
		}
		if($("#rights").val()==""){
			$("#rights").tips({
				side:3,
	            msg:'请输入权限',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#rights").focus();
			return false;
		}
		if($("#role_id").val()==""){
			$("#role_id").tips({
				side:3,
	            msg:'请输入角色ID',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#role_id").focus();
			return false;
		}
		if($("#ip").val()==""){
			$("#ip").tips({
				side:3,
	            msg:'请输入ip地址',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#ip").focus();
			return false;
		}
		if($("#skin").val()==""){
			$("#skin").tips({
				side:3,
	            msg:'请输入皮肤',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#skin").focus();
			return false;
		}
		if($("#email").val()==""){
			$("#email").tips({
				side:3,
	            msg:'请输入邮箱',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#email").focus();
			return false;
		}
		if($("#phone").val()==""){
			$("#phone").tips({
				side:3,
	            msg:'请输入电话',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#phone").focus();
			return false;
		}
		$("#savebtn").attr("disabled", "disabled");
		$.ajax({
			type: "POST",
			url: "${ctx}user/save.action",
			data: $("#Form").serialize(),
			dataType:"json",
			cache: false,
			success: function(data){
				bootbox.alert("保存成功！",function(){
					api.reload();
				});
			},
			error: function(request) {
               bootbox.alert("程序错误！",function(){
					$("#savebtn").removeAttr("disabled");
				});
            },
		});
	}
</script>
</body>
</html>