<!DOCTYPE html>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/include/header.inc"%>
<html>
<head>
<%@ include file="/include/csslib.jsp/"%>
</head>
<body>
	<!-- 页面顶部¨ -->
	<%@ include file="head.jsp"%>
	<div id="websocket_button"></div>
	<div class="container-fluid" id="main-container">
		<a href="#" id="menu-toggler"><span></span></a>
		<!-- menu toggler -->
		<!-- 左侧菜单 -->
		<%@ include file="left.jsp"%>
		<div id="main-content" class="clearfix">
			<div id="jzts" style="display:none; width:100%; position:fixed; z-index:99999999;">
				<div class="commitopacity" id="bkbgjz"></div>
				<div style="padding-left: 70%;padding-top: 1px;">
					<div style="float: left;margin-top: 3px;"><img src="${ctx}include/img/common/loading.gif" /> </div>
					<div style="margin-top: 5px;"><h4 class="lighter block red">&nbsp;加载中 ...</h4></div>
				</div>
			</div>
			<div>
				<iframe name="mainFrame" id="mainFrame" frameborder="0" src="${ctx}admin/tab.action" style="margin:0 auto;width:100%;height:100%;"></iframe>
			</div>
			<!-- 换肤 -->
			<div id="ace-settings-container">
				<div class="btn btn-app btn-mini btn-warning" id="ace-settings-btn">
					<i class="icon-cog"></i>
				</div>
				<div id="ace-settings-box">
					<div>
						<div class="pull-left">
							<select id="skin-colorpicker" class="hidden">
								<option data-class="default" value="#438EB9"
									<c:if test="${ADMINSKIN =='default' }">selected</c:if>>#438EB9</option>
								<option data-class="skin-1" value="#222A2D"
									<c:if test="${ADMINSKIN =='skin-1' }">selected</c:if>>#222A2D</option>
								<option data-class="skin-2" value="#C6487E"
									<c:if test="${ADMINSKIN =='skin-2' }">selected</c:if>>#C6487E</option>
								<option data-class="skin-3" value="#D0D0D0"
									<c:if test="${ADMINSKIN =='skin-3' }">selected</c:if>>#D0D0D0</option>
							</select>
						</div>
						<span>&nbsp; 选择皮肤</span>
					</div>
					<div>
						<label><input type='checkbox' name='menusf' id="menusf" onclick="menusf();" /><span class="lbl" style="padding-top: 5px;">菜单缩放</span></label>
					</div>
				</div>
			</div>
			<!--/#ace-settings-container-->

		</div>
		<!-- #main-content -->
	</div>
	<!--/.fluid-container#main-container-->
<%@ include file="/include/jslib.jsp/"%>
<script type="text/javascript">
	var fmid = "fhindex";
	var mid = "fhindex";//后台首页
	$(function(){
		//初始化菜单-展开
		if (typeof ($.cookie('menusf')) == "undefined") {
			$("#menusf").attr("checked", true);
			$("#sidebar").attr("class", "menu-min");	
		} else {
			$("#sidebar").attr("class", "");
		}
		//换肤
		$("#skin-colorpicker").ace_colorpicker().on("change",function(){
			var b=$(this).find("option:selected").data("class");
			hf(b);
			$.get("${ctx}admin/setSKIN.action?SKIN="+b+"&tm="+new Date().getTime(),function(data){});
		});
		cmainFrame();
	});
	function cmainFrame(){
			var hmain = document.getElementById("mainFrame");
			var bheight = document.documentElement.clientHeight;
			hmain .style.width = '100%';
			hmain .style.height = (bheight  - 51) + 'px';
			var bkbgjz = document.getElementById("bkbgjz");
			bkbgjz .style.height = (bheight  - 41) + 'px';
	}
	window.onresize=function(){  
		cmainFrame();
	};
	//点击菜单
	function siMenu(id,fid,menu_name,menu_url){
		if(id != mid){
			$("#"+mid).removeClass();
			mid = id;
		}
		if(fid != fmid){
			$("#"+fmid).removeClass();
			fmid = fid;
		}
		$("#"+fid).attr("class","active open");
		$("#"+id).attr("class","active");
		//$("#mainFrame").attr("src",MENU_URL);
		top.mainFrame.tabAddHandler(id,menu_name,menu_url);
	}
	//保存缩放菜单状态
	function menusf(){
		if(document.getElementsByName('menusf')[0].checked){
			$.cookie('menusf', '', { expires: -1 });
			$("#sidebar").attr("class","menu-min");
		}else{
			$.cookie('menusf', 'ok');
			$("#sidebar").attr("class","");
		}
		cmainFrame();
	}
	//换肤
	function hf(b){
	var a=$(document.body);
	a.attr("class",a.hasClass("navbar-fixed")?"navbar-fixed":"");
	if(b!="default"){
		a.addClass(b);
	}if(b=="skin-1"){
		$(".ace-nav > li.grey").addClass("dark");
	}else{
		$(".ace-nav > li.grey").removeClass("dark");
	}
	if(b=="skin-2"){
			$(".ace-nav > li").addClass("no-border margin-1");
			$(".ace-nav > li:not(:last-child)").addClass("white-pink")
			.find('> a > [class*="icon-"]').addClass("pink").end()
			.eq(0).find(".badge").addClass("badge-warning");
	}else{
			$(".ace-nav > li").removeClass("no-border").removeClass("margin-1");
			$(".ace-nav > li:not(:last-child)").removeClass("white-pink")
			.find('> a > [class*="icon-"]').removeClass("pink").end()
			.eq(0).find(".badge").removeClass("badge-warning");
	}
	if(b=="skin-3"){
		$(".ace-nav > li.grey").addClass("red").find(".badge").addClass("badge-yellow");
	}else{
		$(".ace-nav > li.grey").removeClass("red").find(".badge").removeClass("badge-yellow");
	}
}
function editmenu(){
	$.dialog({
		id : "menu_dg",
		title : "菜单管理",
		content : "url:${ctx}menu/list.action?pid=0",
		cover : true,
		width : 800,
		height :450,
		lock : true
	});
}

</script>
</body>
</html>
