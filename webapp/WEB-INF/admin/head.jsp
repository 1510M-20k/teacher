﻿<div class="navbar navbar-inverse">
		  <div class="navbar-inner">
		   <div class="container-fluid">
			  <a class="brand"><small><i class="icon-leaf"></i> wy的demo</small> </a>
			  <ul class="nav ace-nav pull-right">
					<!--  <li class="grey">
						<a href="javascript:alert('预留功能,待开发');" class="dropdown-toggle" data-toggle="dropdown">
							<i class="icon-tasks"></i>
							<span class="badge">1</span>
						</a>
						<ul class="pull-right dropdown-navbar dropdown-menu dropdown-caret dropdown-closer">
							<li class="nav-header">
								<i class="icon-ok"></i> 4 任务完成
							</li>
							<li>
								<a href="javascript:alert('预留功能,待开发');">
									<div class="clearfix">
										<span class="pull-left">软件更新</span>
										<span class="pull-right">65%</span>
									</div>
									<div class="progress progress-mini"><div class="bar" style="width:65%"></div></div>
								</a>
							</li>
							<li>
								<a href="javascript:alert('预留功能,待开发');">
									<div class="clearfix">
										<span class="pull-left">软件更新</span>
										<span class="pull-right">35%</span>
									</div>
									<div class="progress progress-mini progress-danger"><div class="bar" style="width:35%"></div></div>
								</a>
							</li>
							<li>
								<a href="javascript:alert('预留功能,待开发');">
									<div class="clearfix">
										<span class="pull-left">运行测试</span>
										<span class="pull-right">15%</span>
									</div>
									<div class="progress progress-mini progress-warning"><div class="bar" style="width:15%"></div></div>
								</a>
							</li>
							
							<li>
								<a href="javascript:alert('预留功能,待开发');">
									<div class="clearfix">
										<span class="pull-left">Bug 修复</span>
										<span class="pull-right">90%</span>
									</div>
									<div class="progress progress-mini progress-success progress-striped active"><div class="bar" style="width:90%"></div></div>
								</a>
							</li>
							
							<li>
								<a href="javascript:alert('预留功能,待开发');">
									查看任务明细
									<i class="icon-arrow-right"></i>
								</a>
							</li>
						</ul>
					</li>
					<li class="green">
						<a href="javascript:alert('预留功能,待开发');" class="dropdown-toggle" data-toggle="dropdown">
							<i class="icon-envelope-alt icon-animated-vertical icon-only"></i>
							<span class="badge badge-success">1</span>
						</a>
						<ul class="pull-right dropdown-navbar dropdown-menu dropdown-caret dropdown-closer">
							<li class="nav-header">
								<i class="icon-envelope"></i> 5 条信件
							</li>
							
							<li>
								<a href="javascript:alert('预留功能,待开发');">
									<img alt="Alex's Avatar" class="msg-photo" src="${ctx}include/img/avatars/avatar.png" />
									<span class="msg-body">
										<span class="msg-title">
											<span class="blue">张三:</span>
											你好，我们在哪里吃饭? ...
										</span>
										<span class="msg-time">
											<i class="icon-time"></i> <span>1个月以前</span>
										</span>
									</span>
								</a>
							</li>
							
							<li>
								<a href="javascript:alert('预留功能,待开发');">
									<img alt="Susan's Avatar" class="msg-photo" src="${ctx}include/img/avatars/avatar3.png" />
									<span class="msg-body">
										<span class="msg-title">
											<span class="blue">李四:</span>
											你在哪上班? ...
										</span>
										<span class="msg-time">
											<i class="icon-time"></i> <span>20分钟前</span>
										</span>
									</span>
								</a>
							</li>
							
							<li>
								<a href="javascript:alert('预留功能,待开发');">
									<img alt="Bob's Avatar" class="msg-photo" src="${ctx}include/img/avatars/avatar4.png" />
									<span class="msg-body">
										<span class="msg-title">
											<span class="blue">王五:</span>
											你好，我对你很感兴趣 ...
										</span>
										<span class="msg-time">
											<i class="icon-time"></i> <span>下午 3:15 </span>
										</span>
									</span>
								</a>
							</li>
							
							<li>
								<a href="javascript:alert('预留功能,待开发');">
									查看所有信件
									<i class="icon-arrow-right"></i>
								</a>
							</li>									
	
						</ul>
					</li>
					
					
					<li class="purple" onclick="creatw();">
						<a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown">
							<i class="icon-bell-alt icon-animated-bell icon-only"></i>
						</a>
					</li>
					-->
					
					<li class="light-blue user-profile">
						<a class="user-menu dropdown-toggle" href="javascript:;" data-toggle="dropdown">
							<img alt="FH" src="${ctx}include/img/avatars/user.jpg" class="nav-user-photo" />
							<span id="user_info" style="margin-top: 8px">
								${user_name}
							</span>
							<i class="icon-caret-down"></i>
						</a>
						<ul id="user_menu" class="pull-right dropdown-menu dropdown-yellow dropdown-caret dropdown-closer">
							<li><a onclick="editPwd();" style="cursor:pointer;"><i class="icon-user"></i> 修改密码</a></li>
							<li id="productCode"><a onclick="productCode();" style="cursor:pointer;"><i class="icon-cogs"></i> 代码生成</a></li>
							<!--  <li id="systemset"><a onclick="editSys();" style="cursor:pointer;"><i class="icon-cog"></i> 系统设置</a></li>
							-->
							<li class="divider"></li>
							<li><a href="javascript:;" onclick="logout()"><i class="icon-off"></i> 退出</a></li>
						</ul>
					</li>
			  </ul><!--/.ace-nav-->
		   </div><!--/.container-fluid-->
		  </div><!--/.navbar-inner-->
		</div><!--/.navbar-->
<script type="text/javascript">
function logout(){
	bootbox.confirm("确定要退出登录吗?", function(result) {
		if(result) {
			window.location.href="${ctx}admin/logout.action";
		}
	});
}
//修改个人资料
function productCode(){
	 $.dialog({
		id : 'productCode',
		title : "代码生成",
		content : "url:${ctx}createCode/createCode.action",
		cover : true,
		width : 800,
		height :400,
		lock : true
	});
}
//修改个人资料
function editPwd(){
	$.dialog({
		id : 'editPwd',
		title : "修改密码",
		content : "url:${ctx}user/edit_pwd.action",
		cover : true,
		width : 225,
		height :180,
		lock : true
	});
}
</script>