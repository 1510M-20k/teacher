<!DOCTYPE html>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/include/header.inc"%>
<html>
<head>
<%@ include file="/include/csslib.jsp/"%>
</head>
<body class="login-layout">
	<div class="container-fluid" id="main-container">
		<div id="main-content">
			<div class="row-fluid">
				<div class="span12">
					<div class="login-container">
						<div class="row-fluid">
							<div class="center">
								<h1>
									<i class="icon-leaf green"></i> <span class="red">Wy</span> <span
										class="white">Application</span>
								</h1>
								<h4 class="blue">&copy; Company Name</h4>
							</div>
						</div>
						<div class="space-6"></div>
						<div class="row-fluid">
							<div class="position-relative">
								<div id="login-box" class="visible widget-box no-border">
									<div class="widget-body">
										<div class="widget-main">
											<div id="loginbox" style="height: 30px;color: red"></div>
											<h4 class="header lighter bigger" ><i class="icon-coffee green"></i>请输入您的登录信息</h4>
													<label> 
														<span class="block input-icon input-icon-right"> 
															<input type="text" class="span12" id="user_name" name="user_name" placeholder="用户名" /> 
															<i class="icon-user"></i> 
															</span> 
													</label> 
													<label> 
														<span class="block input-icon input-icon-right"> 
															<input type="password" class="span12" id="password" name="password" placeholder="密码" /> 
															<i class="icon-user"></i> 
															</span> 
													</label> 
													<label> 
														<span> 验证码:</span> <input type="text" class="span6" id="vcode" name="vcode" placeholder="验证码" maxlength="4"/> 
														<img src="${ctx}validCodeServlet" title="看不清，点击刷新" onclick="reloadValidCode()" id="imgcode" style="height: 32px;margin-bottom: 5px;"/>
													</label> 
													<div class="space"></div>
													<div class="row-fluid">
														<label class="span8"> 
															<input type="checkbox" id="saveid" onclick="savePaw();">
															<span class="lbl"> Remember Me</span> 
														</label>
														<button onclick="login();" class="span4 btn btn-small btn-primary">
															<i class="icon-key"></i> Login
														</button>
													</div>
										</div>
										<!--/widget-main-->


										<div class="toolbar clearfix">
											<div>
												<a href="#" onclick="show_box('forgot-box'); return false;"
													class="forgot-password-link"><i class="icon-arrow-left"></i>
													I forgot my password</a>
											</div>
											<div>
												<a href="#" onclick="show_box('signup-box'); return false;"
													class="user-signup-link">I want to register <i
													class="icon-arrow-right"></i>
												</a>
											</div>
										</div>
									</div>
									<!--/widget-body-->
								</div>
								<!--/login-box-->






								<div id="forgot-box" class="widget-box no-border">
									<div class="widget-body">
										<div class="widget-main">
											<h4 class="header red lighter bigger">
												<i class="icon-key"></i> Retrieve Password
											</h4>

											<div class="space-6"></div>

											<p>Enter your email and to receive instructions</p>
											<form>
												<fieldset>
													<label> <span
														class="block input-icon input-icon-right"> <input
															type="email" class="span12" placeholder="Email" /> <i
															class="icon-envelope"></i> </span> </label>

													<div class="row-fluid">
														<button onclick="return false;"
															class="span5 offset7 btn btn-small btn-danger">
															<i class="icon-lightbulb"></i> Send Me!
														</button>
													</div>

												</fieldset>
											</form>
										</div>
										<!--/widget-main-->

										<div class="toolbar center">
											<a href="#" onclick="show_box('login-box'); return false;"
												class="back-to-login-link">Back to login <i
												class="icon-arrow-right"></i>
											</a>
										</div>
									</div>
									<!--/widget-body-->
								</div>
								<!--/forgot-box-->



								<div id="signup-box" class="widget-box no-border">
									<div class="widget-body">
										<div class="widget-main">
											<h4 class="header green lighter bigger">
												<i class="icon-group blue"></i> New User Registration
											</h4>
											<div class="space-6"></div>
											<p>Enter your details to begin:</p>

											<form>
												<fieldset>
													<label> <span
														class="block input-icon input-icon-right"> <input
															type="email" class="span12" placeholder="Email" /> <i
															class="icon-envelope"></i> </span> </label> <label> <span
														class="block input-icon input-icon-right"> <input
															type="text" class="span12" placeholder="Username" /> <i
															class="icon-user"></i> </span> </label> <label> <span
														class="block input-icon input-icon-right"> <input
															type="password" class="span12" placeholder="Password" />
															<i class="icon-lock"></i> </span> </label> <label> <span
														class="block input-icon input-icon-right"> <input
															type="password" class="span12"
															placeholder="Repeat password" /> <i class="icon-retweet"></i>
													</span> </label> <label> <input type="checkbox"><span
														class="lbl"> I accept the <a href="#">User
																Agreement</a>
													</span> </label>

													<div class="space-24"></div>

													<div class="row-fluid">
														<button type="reset" class="span6 btn btn-small">
															<i class="icon-refresh"></i> Reset
														</button>
														<button onclick="return false;"
															class="span6 btn btn-small btn-success">
															Register <i class="icon-arrow-right icon-on-right"></i>
														</button>
													</div>

												</fieldset>
											</form>
										</div>

										<div class="toolbar center">
											<a href="#" onclick="show_box('login-box'); return false;"
												class="back-to-login-link"><i class="icon-arrow-left"></i>
												Back to login</a>
										</div>
									</div>
									<!--/widget-body-->
								</div>
								<!--/signup-box-->


							</div>
							<!--/position-relative-->

						</div>
					</div>
				</div>
				<!--/span-->
			</div>
			<!--/row-->
		</div>
	</div>
	<!--/.fluid-container-->
<%@ include file="/include/jslib.jsp/"%>
<script type="text/javascript">
	function keyEnter(){ 
		if (event.keyCode == 13) { 
			login();
		} 
	} 
	document.onkeydown =keyEnter; 
	function show_box(id) {
	   $('.widget-box.visible').removeClass('visible');
	   $('#'+id).addClass('visible');
	}
	function reloadValidCode() {
		$("#imgcode").prop('src',"${ctx}validCodeServlet?timed="+ new Date().getMilliseconds());
	}
	//客户端校验
	function check() {
		if ($("#user_name").val() == "") {
			$("#user_name").tips({
				side : 2,
				msg : "用户名不得为空",
				bg : "#AE81FF",
				time : 2
			});
			$("#user_name").focus();
			return false;
		} else {
			$("#user_name").val(jQuery.trim($('#user_name').val()));
		}
		if ($("#password").val() == "") {
			$("#password").tips({
				side : 2,
				msg : "密码不得为空",
				bg : "#AE81FF",
				time : 2
			});
			$("#password").focus();
			return false;
		}
		if ($("#vcode").val() == "") {
			$("#vcode").tips({
				side : 1,
				msg : "验证码不得为空",
				bg : "#AE81FF",
				time : 2
			});
			$("#vcode").focus();
			return false;
		}
		$("#loginbox").text("正在登录 , 请稍后 ...");
		return true;
	}
	//服务器校验
	function login(){
		if(check()){
			var user_name = $("#user_name").val();
			var password = $("#password").val();
			var vcode = $("#vcode").val();
			var keydata = "${user_namesjmcode}"+user_name+",wy,"+password+"${passwordsjmcode}"+",wy,"+vcode;
			$.ajax({
				type: "POST",
				url: "${ctx}admin/login.action",
			    data: {keydata:keydata,tm:new Date().getTime()},
				dataType:'json',
				cache: false,
				success: function(data){
					if(data.success){
						saveCookie();
						window.location.href="${ctx}admin/tomain.action";
					}else if("3" == data.errcode){
							$("#loginbox").text("");
							$("#user_name").tips({
								side : 1,
								msg : "用户名或密码有误",
								bg : '#FF5080',
								time : 15
							});
							$("#user_name").focus();
						}else if("2" == data.errcode){
							$("#loginbox").text("");
							$("#vcode").tips({
								side : 1,
								msg : "验证码输入有误",
								bg : '#FF5080',
								time : 15
							});
							$("#vcode").focus();
						}else{
							$("#loginbox").text("缺少参数");
						}
				}
			});
		}
	}
	//记住密码-增加cookie
	function saveCookie() {
		if ($("#saveid").attr("checked")) {
			$.cookie('user_name', $("#user_name").val(), {
				expires : 7
			});
			$.cookie('password', $("#password").val(), {
				expires : 7
			});
		}
	}
	//记住密码-删除cookie
	function savePaw() {
		if (!$("#saveid").attr("checked")) {
			$.cookie('user_name', '', {
				expires : -1
			});
			$.cookie('password', '', {
				expires : -1
			});
			$("#user_name").val('');
			$("#password").val('');
		}
	}
</script>
</body>
</html>
