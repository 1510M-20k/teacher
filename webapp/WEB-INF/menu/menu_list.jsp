<!DOCTYPE html>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/include/header.inc"%>
<html>
<head>
<%@ include file="/include/csslib.jsp/"%>
</head>
<body>
	<table id="table_report" class="table table-striped table-bordered table-hover">
		<thead>
		<tr>
			<th class="center"  style="width: 50px;">序号</th>
			<th class='center'>名称</th>
			<th class='center'>资源路径</th>
			<th class='center'>排序</th>
			<th class='center'>操作</th>
		</tr>
		</thead>
		<c:choose>
			<c:when test="${not empty varlist}">
				<c:forEach items="${varlist}" var="menu" varStatus="vs">
				<tr id="tr${menu.id }">
				<td class="center">${vs.index+1}</td>
				<td class='center'><i class="${menu.menu_icon }" id="icon${menu.id}">&nbsp;</i>${menu.menu_name }</td>
				<td>${menu.menu_url == '#'? '': menu.menu_url}</td>
				<td class='center'>${menu.menu_order }</td>
				<td style="width: 25%;">
				<a class='btn btn-mini btn-warning' onclick="openClose('${menu.id }',this,${vs.index })" >展开</a>
				<a class='btn btn-mini btn-purple' title="图标" onclick="editTb('${menu.id }')" ><i class='icon-picture'></i></a>
				<a class='btn btn-mini btn-info' title="编辑" onclick="editmenu('${menu.id }')" ><i class='icon-edit'></i></a>
				<a class='btn btn-mini btn-danger' title="删除"  onclick="delmenu('${menu.id }',true)"><i class='icon-trash'></i></a>
				</tr>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<tr>
				<td colspan="100">没有相关数据</td>
				</tr>
			</c:otherwise>
		</c:choose>
	</table>
	
	<div class="page_and_btn">
		<div>
			&nbsp;&nbsp;<a class="btn btn-small btn-success" onclick="addmenu();">新增</a>
			&nbsp;&nbsp;<a class="btn btn-small btn-danger" onclick="api.close();">关闭</a>
		</div>
	</div>
<%@ include file="/include/jslib.jsp/"%>	
<script type="text/javascript">
	var api = frameElement.api, W = api.opener, cDG;
	//新增
	function addmenu(){
		cDG = W.$.dialog({
				id : "menu_add",
				title : "新增菜单",
				content : "url:${ctx}menu/toedit.action",
				cover : true,
				parent:api,
				width : 223,
				height :246,
				lock : true
			});
	}
	
	//修改
	function editmenu(id){
		cDG = W.$.dialog({
				id : "menu_edit",
				title : "编辑菜单",
				content : "url:${ctx}menu/toedit.action?id="+id,
				cover : true,
				parent:api,
				width : 223,
				height :246,
				lock : true
			});
	}
	
	//编辑顶部菜单图标
	function editTb(id){
		cDG =W.$.dialog({
				id : "menu_icon",
				title : "编辑菜单",
				content : "url:${ctx}menu/toeditIcon.action?id="+id,
				cover : true,
				parent:api,
				width : 530,
				height :200,
				lock : true
			});
	}
	function delmenu(id,isParent){
		var flag = false;
		if(isParent){
			bootbox.confirm("确定要删除该菜单吗？其下子菜单将一并删除！?", function(result) {
				if(result) {
					$.ajax({
						type: "POST",
						url: "${ctx}menu/delete.action",
						data: {id:id},
						dataType:'json',
						success: function(data){
							api.reload(window);
						}
					});
				}
			});
		}else{
			bootbox.confirm("确定要删除该菜单吗？", function(result) {
				if(result) {
					$.ajax({
						type: "POST",
						url: "${ctx}menu/delete.action",
						data: {id:id},
						dataType:'json',
						success: function(data){
							api.reload(window);
						}
					});
				}
			});
		}
	}
	
	function openClose(id,curObj,trIndex){
		var txt = $(curObj).text();
		if(txt=="展开"){
			$(curObj).text("折叠");
			$("#tr"+id).after("<tr id='tempTr"+id+"'><td colspan='5'>数据载入中</td></tr>");
			if(trIndex%2==0){
				$("#tempTr"+id).addClass("main_table_even");
			}
			var url = "${ctx}menu/getJsonlist.action?pid="+id+"&guid="+new Date().getTime();
			$.get(url,function(data){
				if(data.length>0){
					var html = "";
					$.each(data,function(i){
						html = "<tr style='height:24px;line-height:24px;' name='subTr"+id+"'>";
						html += "<td></td>";
						html += "<td><span style='width:80px;display:inline-block;'></span>";
						if(i==data.length-1)
							html += "<img src='${ctx}include/img/admin/joinbottom.gif' style='vertical-align: middle;'/>";
						else
							html += "<img src='${ctx}include/img/admin/join.gif' style='vertical-align: middle;'/>";
						html += "<span style='width:100px;text-align:left;display:inline-block;'>"+this.menu_name+"</span>";
						html += "</td>";
						html += "<td>"+this.menu_url+"</td>";
						html += "<td class='center'>"+this.menu_order+"</td>";
						html += "<td><a class='btn btn-mini btn-info' title='编辑' onclick='editmenu(\""+this.id+"\")'><i class='icon-edit'></i></a> <a class='btn btn-mini btn-danger' title='删除' onclick='delmenu(\""+this.id+"\",false)'><i class='icon-trash'></i></a></td>";
						html += "</tr>";
						$("#tempTr"+id).before(html);
					});
					$("#tempTr"+id).remove();
					if(trIndex%2==0){
						$("tr[name='subTr"+id+"']").addClass("main_table_even");
					}
				}else{
					$("#tempTr"+id+" > td").html("没有相关数据");
				}
			},"json");
		}else{
			$("#tempTr"+id).remove();
			$("tr[name='subTr"+id+"']").remove();
			$(curObj).text("展开");
		}
	}
	function turnicon(id,icon){
		$("#"+id).removeClass().addClass(icon);
	}
</script>
</body>
</html>