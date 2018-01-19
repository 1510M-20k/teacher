<!DOCTYPE html>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/include/header.inc"%>
<html>
<head>
<%@ include file="/include/csslib.jsp/"%>
<link type="text/css" rel="stylesheet" href="${ctx}plugins/zTree/2.6/zTreeStyle.css"/>
<style type="text/css">
footer{height:50px;position:fixed;bottom:0px;left:0px;width:100%;text-align: center;}
</style>
</head>
<body>
	<ul id="tree" class="tree" style="overflow:auto;">
	
	</ul>
	<footer>
		<div style="width: 100%;" class="center">
			<a class="btn btn-mini btn-primary" id="savebtn()" onclick="save();">保存</a>
			<a class="btn btn-mini btn-danger" onclick="frameElement.api.close();">取消</a>
		</div>
	</footer>
<%@ include file="/include/jslib.jsp/"%>
<script type="text/javascript" src="${ctx}plugins/zTree/2.6/jquery.ztree-2.6.min.js"></script>
<script type="text/javascript">
	var api = frameElement.api, W = api.opener;
	$(document).ready(function(){
			var setting = {
			    showLine: true,
			    checkable: true,
			    showIcon :false
			};
			var zn = '${zTreeNodes}';
			var zTreeNodes = eval(zn);
			zTree = $("#tree").zTree(setting, zTreeNodes);
	});
	function save(){
		var nodes = zTree.getCheckedNodes();
		var tmpNode;
		var ids = "";
		for(var i=0; i<nodes.length; i++){
			tmpNode = nodes[i];
			if(i!=nodes.length-1){
				ids += tmpNode.id+",";
			}else{
				ids += tmpNode.id;
			}
		}
		$("#savebtn").attr("disabled", "disabled");
		$.ajax({
			type: "POST",
			url: "${ctx}role/saveright.action",
			data: {
				id:"${id}",
				menuIds:ids,
				msg:"${msg}",
				tm:new Date().getTime()
			},
			dataType:"json",
			cache: false,
			success: function(data){
				if(data.success){
						W.$.dialog.alert("保存成功！",function(){
							api.reload();
						}); 
				} else{
				 	W.$.dialog.alert("保存失败，没有权限！",function(){
						$("#savebtn").removeAttr("disabled");
					});
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