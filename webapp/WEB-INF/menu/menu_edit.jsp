<!DOCTYPE html>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/include/header.inc"%>
<html>
<head>
<%@ include file="/include/csslib.jsp/"%>
</head>
<body>
	<form action="" name="menuForm" id="menuForm" method="post">
		<input type="hidden" name="id" id="id" value="${pd.id}" />
		<div id="zhongxin">
			<table>
				<tr>
					<td><select name="pid" id="pid" onchange="setMUR()" title="菜单">
							<option value="0">顶级菜单</option>
							<c:forEach items="${menuList}" var="menu">
								<option value="${menu.id }" <c:if test="${menu.id == pd.pid}">selected="selected"</c:if>>${menu.menu_name }</option>
							</c:forEach>
					</select></td>
				</tr>

				<tr>
					<td><input type="text" name="menu_name" id="menu_name" placeholder="这里输入菜单名称" value="${pd.menu_name }" title="名称" />
					</td>
				</tr>
				<tr>
					<td><input type="text" name="menu_url" id="menu_url"
						placeholder="这里输入链接地址" value="${pd.menu_url }" title="地址" />
					</td>
				</tr>
				<tr>
					<td><input type="number" name="menu_order" id="menu_order"
						placeholder="这里输入序号" value="${pd.menu_order}" title="序号" />
					</td>
				</tr>
				<tr>
					<td style="text-align: center; padding-top: 10px;"><a
						class="btn btn-mini btn-primary" id="savebtn" onclick="save();">保存</a> <a
						class="btn btn-mini btn-danger" onclick="api.close();">取消</a>
					</td>
				</tr>
			</table>
		</div>
	</form>
<%@ include file="/include/jslib.jsp/"%>
	<script type="text/javascript">
		var api = frameElement.api, W = api.opener;
		$(document).ready(function() {
			if ($("#id").val() != "") {
				var pid = $("#pid").val();
				if (pid == "0") {
					$("#pid").attr("disabled", true);
				}
			}
		});

		var menu_url = "";
		function setMUR() {
			menu_urly = $("#menu_url").val();
			if (menu_urly != '') {
				menu_url = menu_urly;
			}
			if ($("#pid").val() == "0") {
				$("#menu_url").attr("readonly", true);
				$("#menu_url").val("");
			} else {
				$("#menu_url").attr("readonly", false);
				$("#menu_url").val(menu_url);
			}
		}

		//保存
		function save() {
			if ($("#menu_name").val() == "") {

				$("#menu_name").tips({
					side : 3,
					msg : '请输入菜单名称',
					bg : '#AE81FF',
					time : 2
				});

				$("#menu_name").focus();
				return false;
			}
			if ($("#menu_url").val() == "") {
				$("#menu_url").val('#');
			}
			if ($("#menu_order").val() == "") {
				$("#menu_order").tips({
					side : 1,
					msg : '请输入菜单序号',
					bg : '#AE81FF',
					time : 2
				});

				$("#menu_order").focus();
				return false;
			}

			if (isNaN(Number($("#menu_order").val()))) {

				$("#menu_order").tips({
					side : 1,
					msg : '请输入菜单序号',
					bg : '#AE81FF',
					time : 2
				});

				$("#menu_order").focus();
				$("#menu_order").val(1);
				return false;
			}
			$("#savebtn").attr("disabled", "disabled");
			$.ajax({
				type: "POST",
				url: "${ctx}menu/save.action",
				data: $("#menuForm").serialize(),
				dataType:"json",
				cache: false,
				success: function(data){
					W.$.dialog.alert("保存成功！",function(){
						api.close();
						 api.reload( api.get("menu_dg") );
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