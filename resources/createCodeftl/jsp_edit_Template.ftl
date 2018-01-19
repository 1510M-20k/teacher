<!DOCTYPE html>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/include/header.inc"%>
<html>
<head>
<%@ include file="/include/csslib.jsp/"%>
</head>
<body>
	<form action="${objectNameLower}/save.do" name="Form" id="Form" method="post">
		<input type="hidden" name="id" id="id" value="${r"${pd."}id${r"}"}"/>
		<table id="table_report" class="table table-striped table-bordered table-hover">
<#list fieldList as var>
	<#if var[3] == "是">
		<#if var[1] == 'Date'>
			<tr>
				<td style="width:120px;text-align: right;vertical-align: middle;">${var[2]}:</td>
				<td><input class="span10 Wdate" name="${var[0]}" id="${var[0]}" value="${r"${pd."}${var[0]}${r"}"}" type="text" onclick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss',lang:'zh-cn'})" readonly="readonly" style="width: 205px;margin-bottom: 0px" placeholder="这里输入${var[2]}"/></td>
			</tr>
		<#elseif var[1] == 'Integer'>
			<tr>
				<td style="width:120px;text-align: right;vertical-align: middle;">${var[2]}:</td>
				<td><input type="number" name="${var[0]}" id="${var[0]}" value="${r"${pd."}${var[0]}${r"}"}" maxlength="11" placeholder="这里输入${var[2]}" title="${var[2]}" style="margin-bottom: 0px"/></td>
			</tr>
		<#else>
			<tr>
				<td style="width:120px;text-align: right;vertical-align: middle;">${var[2]}:</td>
				<td><input type="text" name="${var[0]}" id="${var[0]}" value="${r"${pd."}${var[0]}${r"}"}" maxlength="32" placeholder="这里输入${var[2]}" title="${var[2]}" style="margin-bottom: 0px"/></td>
			</tr>
		</#if>
	</#if>
</#list>
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
		if("${r"${otype}"}" == "look"){
			$("#table_report").find(":input").each(function(){
				$(this).parent().html($(this).val());
			});
			
			$("#savebtn").hide();
		}
	});

	//保存
	function save(){
	<#list fieldList as var>
		<#if var[3] == "是">
		if($("#${var[0]}").val()==""){
			$("#${var[0]}").tips({
				side:3,
	            msg:'请输入${var[2] }',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#${var[0]}").focus();
			return false;
		}
		</#if>
	</#list>
		$("#savebtn").attr("disabled", "disabled");
		$.ajax({
			type: "POST",
			url: "${r"${ctx}"}${objectNameLower}/save.action",
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