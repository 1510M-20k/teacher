<!DOCTYPE html>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/include/header.inc"%>
<html>
<head>
<%@ include file="/include/csslib.jsp/"%>
</head>
<body>
	<div class="container-fluid" id="main-container">
		<div id="page-content" class="clearfix">
			<div class="row-fluid">
				<div class="row-fluid">
					<div id="breadcrumbs">
		<table class="center" style="width:100%;">
			<tr height="35">
				<td style="width:69px;"><a href="javascript:edit_name('','0','add');" class="btn btn-small btn-success">新增</a></td>
			<c:forEach items="${roleList}" var="role" varStatus="vs">
				<td style="width:100px;" class="center" <c:choose><c:when test="${pd.pid == role.id}">bgcolor="#FFC926" onMouseOut="javascript:this.bgColor='#FFC926';"</c:when><c:otherwise>bgcolor="#E5E5E5" onMouseOut="javascript:this.bgColor='#E5E5E5';"</c:otherwise></c:choose>  onMouseMove="javascript:this.bgColor='#FFC926';" >
					<a href="${ctx}role/list.action?pid=${role.id}" style="text-decoration:none; display:block;"><li class=" icon-group"></li>&nbsp;<font color="#666666">${role.role_name}</font></a>
				</td>
				<td style="width:5px;"></td>
			</c:forEach>
				<td></td>
			</tr>
		</table>
	</div>	
					<form action="${ctx}role/list.action?pid=${role.id}" method="post" name="Form" id="Form">
						<!-- 检索  开始-->
						<table>
							<tr height="7px;"><td colspan="100"></td></tr>
							<tr>
							<td><font color="#808080">本组：</font></td>
							<td>
							<a class="btn btn-mini btn-info" onclick="edit_name('${pd.pid}','0','edit');">修改组名称<i class="icon-arrow-right  icon-on-right"></i></a>
							<a class="btn btn-mini btn-purple" onclick="editRights('${pd.pid}','rights');"><i class="icon-pencil"></i>组菜单权限</a>
							</td>
							</tr>
							<tr height="7px;"><td colspan="100"></td></tr>
						</table>
						<!-- 检索结束 -->
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<thead>
								<tr>
									<th class="center">序号</th>
									<th class="center">角色名称</th>
									<th class="center">新增</th>
									<th class="center">编辑</th>
									<th class="center">删除</th>
									<th class="center">查询</th>
									<th class="center">审核</th>
									<th class="center">备用1</th>
									<th class="center">备用2</th>
									<th class="center">备用3</th>
									<th class="center">操作</th>
								</tr>
							</thead>
							<tbody>
								<!-- 开始循环 -->
								<c:choose>
									<c:when test="${not empty varlist}">
										<c:forEach items="${varlist}" var="var" varStatus="vs">
											<tr>
												<td class='center' style="width: 30px;">${vs.index+1}</td>
												<td>${var.role_name}</td>
												<td><a onclick="editRights('${var.id}','add_qx');" class="btn btn-warning btn-mini" title="分配新增权限"><i class="icon-wrench icon-2x icon-only"></i></a></td>
												<td><a onclick="editRights('${var.id}','edit_qx');" class="btn btn-warning btn-mini" title="分配编辑权限"><i class="icon-wrench icon-2x icon-only"></i></a></td>
												<td><a onclick="editRights('${var.id}','del_qx');" class="btn btn-warning btn-mini" title="分配删除权限"><i class="icon-wrench icon-2x icon-only"></i></a></td>
												<td><a onclick="editRights('${var.id}','cha_qx');" class="btn btn-warning btn-mini" title="分配查询权限"><i class="icon-wrench icon-2x icon-only"></i></a></td>
												<td><a onclick="editRights('${var.id}','sh_qx');" class="btn btn-warning btn-mini" title="分配审核权限"><i class="icon-wrench icon-2x icon-only"></i></a></td>
												<td><a onclick="editRights('${var.id}','by1_qx');" class="btn btn-warning btn-mini" title="分配备用1权限"><i class="icon-wrench icon-2x icon-only"></i></a></td>
												<td><a onclick="editRights('${var.id}','by2_qx');" class="btn btn-warning btn-mini" title="分配备用2权限"><i class="icon-wrench icon-2x icon-only"></i></a></td>
												<td><a onclick="editRights('${var.id}','by3_qx');" class="btn btn-warning btn-mini" title="分配备用3权限"><i class="icon-wrench icon-2x icon-only"></i></a></td>
												<td style="width: 200px;">
													<a class="btn btn-mini btn-purple" onclick="editRights('${var.id}','rights');"><i class="icon-pencil"></i>菜单权限</a>
													<a class='btn btn-mini btn-info' title="编辑" onclick="edit_name('${var.id}','${var.pid}','edit')"><i class='icon-edit'></i></a>
													<c:if test="${var.role_name != '超级管理员'}">
													<a class='btn btn-mini btn-danger' title="删除" onclick="del('${var.id }','${var.role_name}');"><i class='icon-trash'></i></a>
													</c:if>
												</td>
											</tr>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<tr class="main_info">
											<td colspan="100" class="center">没有相关数据</td>
										</tr>
									</c:otherwise>
								</c:choose>
							</tbody>
						</table>
						<div class="page-header position-relative">
							<table style="width:100%;">
								<tr>
									<td style="vertical-align:top;"><a class="btn btn-small btn-success" onclick="edit_name('','${pd.pid}','add');">新增</a></td>
								</tr>
							</table>
						</div>
					</form>
				</div>
				<!-- PAGE CONTENT ENDS HERE -->
			</div>
			<!--/row-->
		</div>
		<!--/#page-content-->
	</div>
	<!--/.fluid-container#main-container-->
	<!-- 返回顶部  -->
	<a href="#" id="btn-scroll-up" class="btn btn-small btn-inverse"> <i class="icon-double-angle-up icon-only"></i> </a>
	<!-- 引入js -->
	<%@ include file="/include/jslib.jsp/"%>
	<script type="text/javascript">
		//新增、修改、查看角色
		function edit_name(id,pid,otype){
			var title="查看详情";
			if(otype == "add"){
				title="新增";
			} else if(otype == "edit"){
				title="修改";
			}
			$.dialog({
				id : 'role_dg',
				title : title,
				content : "url:${ctx}role/toedit.action?id="+id+"&pid="+pid+"&otype="+otype,
				cover : true,
				width : 320,
				height :120,
				lock : true
			});
		}
		//按钮权限
		function editRights(id,msg){
			if(msg == 'add_qx'){
				var Title = "授权新增权限";
			}else if(msg == 'del_qx'){
				Title = "授权删除权限";
			}else if(msg == 'edit_qx'){
				Title = "授权修改权限";
			}else if(msg == 'cha_qx'){
				Title = "授权查看权限";
			}else if(msg == 'sh_qx'){
				Title = "授权审核权限";
			}else if(msg == 'by1_qx'){
				Title = "授权备用1权限";			
			}else if(msg == 'by2_qx'){
				Title = "授权备用2权限";	
			}else if(msg == 'by3_qx'){
				Title = "授权备用3权限";	
			}else if(msg == 'by3_qx'){
				Title = "授权备用3权限";	
			}else if(msg == 'rights'){
				Title = "授权菜单权限";	
			}
			$.dialog({
				id : 'right_dg',
				title : Title,
				content : "url:${ctx}role/toeditright.action?id="+id+"&msg="+msg,
				cover : true,
				width : 260,
				height :350,
				lock : true
			});
		}
		
		//删除
		function del(id,role_name){
			bootbox.confirm("确定要删除【"+role_name+"】吗?", function(result) {
				if(result) {
					$.ajax({
						type: "POST",
						url: "${ctx}role/delete.action",
						data: {id:id},
						dataType:'json',
						success: function(data){
							window.location.reload();
						}
					});
				}
			});	
		}
	</script>
</body>
</html>

