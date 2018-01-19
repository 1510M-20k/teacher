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
					<form action="${r"${ctx}"}${objectNameLower}/list.action" method="post" name="Form" id="Form">
						<!-- 检索  开始-->
						<table>
							<tr>
								<td><span class="input-icon"> <input autocomplete="off" id="nav-search-input" type="text" name="keyword" value="${r"${pd.keyword}"}" placeholder="这里输入关键词" /> <i id="nav-search-icon" class="icon-search"></i> </span></td>
								<td><input class="span10 Wdate" name="start_time" id="start_time" value="${r"${pd.start_time}"}" type="text" onclick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd 00-00-00',lang:'zh-cn'})" readonly="readonly" style="width: 150px;height: 28px" placeholder="开始时间"/></td>
								<td><input class="span10 Wdate" name="end_time" id="end_time" value="${r"${pd.end_time}"}" type="text" onclick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd 23:59:59',lang:'zh-cn'})" readonly="readonly" style="width: 150px;height: 28px" placeholder="结束时间"/></td>
								<td style="vertical-align:top;padding-top:2px">
									<select class="chzn-select" name="status" id="status" data-placeholder="请选择" style="vertical-align:top;width: 120px;">
										<option value=""></option>
										<option value="">全部</option>
										<option value="">1</option>
										<option value="">2</option>
									</select>
								</td>
								<td style="vertical-align:top;">
									<button class="btn btn-mini btn-light" onclick="search();" title="检索"><i id="nav-search-icon" class="icon-search"></i></button>
								</td>
							</tr>
						</table>
						<!-- 检索结束 -->
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<thead>
								<tr>
									<th class="center"><label><input type="checkbox" id="zcheckbox" /><span class="lbl"></span></label></th>
									<th class="center">序号</th>
								<#list fieldList as var>
									<th class="center">${var[2]}</th>
								</#list>
									<th class="center">操作</th>
								</tr>
							</thead>
							<tbody>
								<!-- 开始循环 -->
								<c:choose>
									<c:when test="${r"${not empty varlist}"}">
										<c:forEach items="${r"${varlist}"}" var="var" varStatus="vs">
											<tr>
												<td class='center' style="width: 30px;"><label><input type='checkbox' name='ids' value="${r"${var.id}"}" /><span class="lbl"></span></label></td>
												<td class='center' style="width: 30px;">${r"${(pageModel.currentPage-1)*pageModel.showCount+vs.index+1}"}</td>
												<#list fieldList as var>
												<td>${r"${var."}${var[0]}${r"}"}</td>
												</#list>
												<td style="width: 30px;" class="center">
													<div class='hidden-phone visible-desktop btn-group'>
														<div class="inline position-relative">
															<button class="btn btn-mini btn-info" data-toggle="dropdown"><i class="icon-cog icon-only"></i></button>
															<ul class="dropdown-menu dropdown-icon-only dropdown-light pull-right dropdown-caret dropdown-close">
																<li><a style="cursor:pointer;" title="查看" onclick="edit('${r"${var.id}"}','look');" class="tooltip-warning" data-rel="tooltip" title="" data-placement="left"><span class="blue"><i class=' icon-eye-open'></i> </span></a></li>
																<li><a style="cursor:pointer;" title="编辑" onclick="edit('${r"${var.id}"}','edit');" class="tooltip-success" data-rel="tooltip" title="" data-placement="left"><span class="green"><i class="icon-edit"></i></span></a></li>
																<li><a style="cursor:pointer;" title="删除" onclick="del('${r"${var.id}"}');" class="tooltip-error" data-rel="tooltip" title="" data-placement="left"><span class="red"><i class="icon-trash"></i> </span> </a> </li>
															</ul>
														</div>
													</div>
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
									<td style="vertical-align:top;"><a class="btn btn-small btn-success" onclick="edit('${r"${var.id}"}','add');">新增</a> <a class="btn btn-small btn-danger" onclick="makeAll();" title="批量删除"><i class='icon-trash'></i> </a></td>
									<td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${r"${pageModel.pageStr}"}</div></td>
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
		$(function() {
			//下拉框
			$(".chzn-select").chosen(); 
			//复选框
			$('table th input:checkbox').on('click' , function(){
				var that = this;
				$(this).closest('table').find('tr > td:first-child input:checkbox').each(function(){
					this.checked = that.checked;
					$(this).closest('tr').toggleClass('selected');
				});
					
			});
		});
		//检索
		function search(){
			$("#Form").submit();
		}
		//新增、修改、查看
		function edit(id,otype){
			var title="查看详情";
			if(otype == "add"){
				title="新增";
			} else if(otype == "edit"){
				title="修改";
			}
			$.dialog({
				id : '${objectNameLower}_dg',
				title : title,
				content : "url:${r"${ctx}"}${objectNameLower}/toedit.action?id="+id+"&otype="+otype,
				cover : true,
				width : 800,
				height :450,
				lock : true
			});
		}
		//删除
		function del(id){
			bootbox.confirm('确定要删除吗?', function(result) {
				if(result) {
					$.ajax({
						type: "POST",
						url: "${r"${ctx}"}${objectNameLower}/delete.action",
						data: {id:id},
						dataType:'json',
						success: function(data){
							nextPage(${r"${pageModel.currentPage}"});
						}
					});
				}
			});	
		}
		//批量操作
		function makeAll(){
			var str = '';
			for(var i=0;i < document.getElementsByName('ids').length;i++){
				if(document.getElementsByName('ids')[i].checked){
				  	if(str==''){
				  		str += document.getElementsByName('ids')[i].value;
				  	}else{
				  		str += ',' + document.getElementsByName('ids')[i].value;
				  	}
				}
			}
			if(str==''){
				bootbox.alert("您没有选择任何内容!", function(){
					$("#zcheckbox").tips({
						side:3,
			            msg:'点这里全选',
			            bg:'#AE81FF',
			            time:1
			        });
				});
			}else{
				bootbox.confirm('确定要删除选中的数据吗?', function(result) {
					if(result) {
						$.ajax({
							type: "POST",
							url: '${r"${ctx}"}${objectNameLower}/deleteAll.action?tm='+new Date().getTime(),
					    	data: {ids:str},
							dataType:'json',
							cache: false,
							success: function(data){
								nextPage(${r"${pageModel.currentPage}"});
							}
						});
					}
				});
			}
		}
	</script>
</body>
</html>

