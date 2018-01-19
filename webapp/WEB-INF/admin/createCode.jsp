<!DOCTYPE html>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/include/header.inc"%>
<html>
<head>
<%@ include file="/include/csslib.jsp/"%>
<style type="text/css">
		#dialog-add,#dialog-message,#dialog-comment{width:100%; height:100%; position:fixed; top:0px; z-index:10000; display:none;}
		.commitopacity{position:absolute; width:100%; height:500px; background:#7f7f7f; filter:alpha(opacity=50); -moz-opacity:0.5; -khtml-opacity: 0.5; opacity: 0.5; top:0px; z-index:99999;}
		.commitbox{width:79%; padding-left:81px; padding-top:69px; position:absolute; top:0px; z-index:99999;}
		.commitbox_inner{width:96%; height:235px;  margin:6px auto; background:#efefef; border-radius:5px;}
		.commitbox_top{width:100%; height:230px; margin-bottom:10px; padding-top:10px; background:#FFF; border-radius:5px; box-shadow:1px 1px 3px #e8e8e8;}
		.commitbox_top textarea{width:95%; height:165px; display:block; margin:0px auto; border:0px;}
		.commitbox_cen{width:95%; height:40px; padding-top:10px;}
		.commitbox_cen div.left{float:left;background-size:15px; background-position:0px 3px; padding-left:18px; color:#f77500; font-size:16px; line-height:27px;}
		.commitbox_cen div.left img{width:30px;}
		.commitbox_cen div.right{float:right; margin-top:7px;}
		.commitbox_cen div.right span{cursor:pointer;}
		.commitbox_cen div.right span.save{border:solid 1px #c7c7c7; background:#6FB3E0; border-radius:3px; color:#FFF; padding:5px 10px;}
		.commitbox_cen div.right span.quxiao{border:solid 1px #f77400; background:#f77400; border-radius:3px; color:#FFF; padding:4px 9px;}
</style>
</head>
<body>
		
	<!-- 添加属性  -->
	<input type="hidden" name="hcdname" id="hcdname" value="" />
	<input type="hidden" name="msgIndex" id="msgIndex" value="" />
	<input type="hidden" name="dtype" id="dtype" value="String" />
	<input type="hidden" name="isQian" id="isQian" value="是" />
	<div id="dialog-add">
		<div class="commitopacity"></div>
	  	<div class="commitbox">
		  	<div class="commitbox_inner">
		        <div class="commitbox_top">
		        	<br/>
		        	<table>
		        		<tr>
		        			<td style="padding-left: 16px;text-align: right;">属性名：</td><td><input name="dname" id="dname" type="text" value="" placeholder="首字母必须为字母或下划线" title="属性名" /></td>
		        			<td style="padding-left: 16px;text-align: right;">属性类型：</td>
		        			<td style="padding-bottom: 6px;">
		        				<label style="float:left;padding-left: 20px;"><input name="form-field-radiot" id="form-field-radio1" onclick="setType('String');" type="radio" value="icon-edit" ><span class="lbl">String</span></label>
								<label style="float:left;padding-left: 20px;"><input name="form-field-radiot" id="form-field-radio2" onclick="setType('Integer');" type="radio" value="icon-edit"><span class="lbl">Integer</span></label><br>
								<label style="float:left;padding-left: 20px;"><input name="form-field-radiot" id="form-field-radio3" onclick="setType('Money');" type="radio" value="icon-edit"><span class="lbl">Money  </span></label>
								<label style="float:left;padding-left: 20px;"><input name="form-field-radiot" id="form-field-radio4" onclick="setType('Date');" type="radio" value="icon-edit"><span class="lbl">Date</span></label>
							</td>
		        		</tr>
		        		<tr>
		        			<td style="padding-left: 16px;text-align: right;">其备注：</td><td><input name="dbz" id="dbz" type="text" value="" placeholder="例如 name的备注为 '姓名'" title="备注"/></td>
		        			<td style="padding-left: 16px;text-align: right;">前台录入：</td>
		        			<td style="padding-bottom: 6px;">
		        				<label style="float:left;padding-left: 20px;"><input name="form-field-radioq" id="form-field-radio5" onclick="isQian('是');" type="radio" value="icon-edit" checked="checked"><span class="lbl">是</span></label>
								<label style="float:left;padding-left: 20px;"><input name="form-field-radioq" id="form-field-radio6" onclick="isQian('否');" type="radio" value="icon-edit"><span class="lbl">否</span></label>
							</td>
		        		</tr>
		        		<tr>
		        			<td style="padding-left: 16px;text-align: right;">默认值：</td><td><input name="ddefault" id="ddefault" type="text" value="" disabled="disabled" placeholder="后台附加值时生效" title="默认值"/></td>
		        			<td style="padding-left: 16px;text-align: right;"></td>
		        			<td>
		        			<div class="commitbox_cen">
				                <div class="left" id="cityname"></div>
				                <div class="right"><span class="save" onClick="saveD()">保存</span>&nbsp;&nbsp;<span class="quxiao" onClick="cancel_pl()">取消</span></div>
				            </div>
		        			</td>
		        		</tr>
		        		<tr>
		        			<td style="padding-left: 16px;" colspan="100">
		        				<font color="red" style="font-weight: bold;">
		        					注意：<br/>
		        					  系统会自动帮忙生成主键id,创建时间create_time,最后修改时间last_update_time请不要重复添加。所有字段只能由字母和"_"组成，的字母均用小写。
		        				</font>
							</td>
		        		</tr>
		        	</table>
		        </div>
		  	</div>
	  	</div>
	</div>

	<form action="${ctx}createCode/createout.action" name="Form" id="Form" method="post">
		<input type="hidden" name="zindex" id="zindex" value="0">
		<div id="zhongxin">
		<table style="margin-top: 10px;" border="0">
			<tr>
				<td style="width:76px;text-align: right;">上级包名：</td>
				<td colspan="1"><input type="text" name="packageName" id="packageName" value="" placeholder="这里输入包名  (请用纯字母)" style="width:200px" title="包名称"/></td>
				<td style="width:76px;text-align: right;">业务说明：</td>
				<td><input type="text" name="remark" id="remark" value="" placeholder="这里输入业务中文说明" style="width:2WW00px" title="包名称"/></td>
			</tr>
		</table>
		<table border="0">
			<tr>
				<td style="width:76px;text-align: right;">处理类名：</td>
				<td><input type="text" name="objectName" id="objectName" value="" placeholder="这里输入处理类名称" style="width:200px" title="类名称"/></td>
				<td>&nbsp;<font color="red" style="font-weight: bold;">类名首字母必须为大写字母</font></td>
				<td style="width:76px;text-align: right;">表前缀：</td>
				<td><input type="text" name="tabletop" id="tabletop" value="t_" placeholder="这里输入表前缀" style="width:60px" title="表前缀"/></td>
			</tr>
		</table>
		<table id="table_report" class="table table-striped table-bordered table-hover">
				
				<thead>
					<tr>
						<th class="center">属性名</th>
						<th class="center">类型</th>
						<th class="center">备注</th>
						<th class="center" style="width:59px;">前台录入</th>
						<th class="center">默认值</th>
						<th class="center" style="width:80px;">操作</th>
					</tr>
				</thead>
										
				<tbody id="fields"></tbody>
				
		</table>
		<table id="table_report" class="table table-striped table-bordered table-hover">
			<tr>
				<td style="text-align: center;" colspan="100">
					<a class="btn btn-app btn-success btn-mini" onclick="dialog_open();"><i class="icon-ok"></i>新增</a>
					<a class="btn btn-app btn-success btn-mini" onclick="save();" id="productc"><i class="icon-print"></i>生成</a>
					<a class="btn btn-app btn-danger btn-mini" onclick="frameElement.api.close();"><i class="icon-share-alt"></i>取消</a>
				</td>
			</tr>
		</table>
		</div>
	</form>
	
<%@ include file="/include/jslib.jsp/"%>
<script type="text/javascript" src="${ctx}include/js/createCode.js"></script>
<script type="text/javascript">
var api = frameElement.api, W = api.opener;
</script>
</body>
</html>