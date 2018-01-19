<!DOCTYPE html>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/include/header.inc"%>
<html>
<head>
<%@ include file="/include/csslib.jsp/"%>
</head>
<body>
<form action="" id="pwdForm" method="post">
	<table>
		<tr>
			<td><input type="text" name="oldpassword" id="oldpassword" value="" maxlength="15" placeholder="这里输入原密码" title="原密码" />
			</td>
		</tr>
		<tr>
			<td><input type="password" name="password" id="password" maxlength="15" placeholder="输入新密码" title="新密码" />
			</td>
		</tr>
		<tr>
			<td><input type="password" name="qrpassword" id="qrpassword" maxlength="15" placeholder="确认密码" title="确认密码" />
			</td>
		</tr>
		<tr>
			<td style="text-align: center;"><a
				class="btn btn-mini btn-primary" id="savebtn" onclick="save();">保存</a> <a
				class="btn btn-mini btn-danger" onclick="api.close();">取消</a>
			</td>
		</tr>
	</table>
</form>
<%@ include file="/include/jslib.jsp/"%>
<script type="text/javascript">
	var api = frameElement.api, W = api.opener;
	function save() {
		if ($("#oldpassword").val() == "") {
			$("#oldpassword").tips({
				side : 3,
				msg : "原密码不能为空",
				bg : '#AE81FF',
				time : 2
			});
			$("#oldpassword").focus();
			return false;
		}
		if ($("#oldpassword").val().length < 6) {
			$("#oldpassword").tips({
				side : 3,
				msg : "密码长度为6-15位",
				bg : '#AE81FF',
				time : 2
			});
			$("#oldpassword").focus();
			return false;
		}
		if ($("#password").val().length < 6) {
			$("#password").tips({
				side : 3,
				msg : "密码长度为6-15位",
				bg : '#AE81FF',
				time : 2
			});
			$("#password").focus();
			return false;
		}
		if ($("#password").val() != $("#qrpassword").val()) {
			$("#qrpassword").tips({
				side : 3,
				msg : "两次密码输入不一致",
				bg : '#AE81FF',
				time : 2
			});
			$("#qrpassword").focus();
			return false;
		}
	      $("#savebtn").attr("disabled", "disabled");
			$.ajax({
				type: "POST",
				url: "${ctx}user/savepwd.action",
				data: $("#pwdForm").serialize(),
				dataType:"json",
				cache: false,
				success: function(data){
					if(data.success){
						W.$.dialog.alert("修改成功！",function(){
						 api.close();
						}); 
					} else{
						$("#oldpassword").tips({
							side : 3,
							msg : "原密码错误",
							bg : '#AE81FF',
							time : 2
						});
						$("#oldpassword").focus();
						$("#savebtn").removeAttr("disabled");
					}
					
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