var error=0;
var hasfocus = false;
$(function(){
	$('form').find(":input").blur(function(){
		var parent = $(this).parent();
		var value = $.trim($(this).val());
		var title =  $(this).attr("title");
		if(typeof(title) == 'undefined'){
			title="";
		}
		var isHidden = $(this).attr("type") == "hidden";
		if(!isHidden && !/^[^<>;]{0,}$/.test(value)){
			$(this).tips({
				side:3,
				msg:'不能输入符号^<>;',
	            bg:'#AE81FF',
	            time:2
	        });
			if(!hasfocus){
				hasfocus = true;
				$(this).focus();//if the frame is too large with scrollbar, must use focus() to scroll to the item with error.
			}
			error++;
		}
		if($(this).hasClass("required")){
			if(value==""){
				$(this).tips({
					side:3,
		            msg:title+'不能为空',
		            bg:'#AE81FF',
		            time:2
		        });
				if(!hasfocus){
					hasfocus = true;
					$(this).focus();
				}
				error++;
			}
		}
		//以判断不为空，下边的验证都是允许为空的，所以不为空时再校验
		if(value!=""){
			//特例情况，优先判断，根据ID进行验证
			if($(this).is('#your_vaidate_id')){
				
				
			}else if($(this).hasClass("phone")){//验证手机号码
				if(!/^1\d{10}$/.test(value)){
					$(this).tips({
						side:3,
			            msg:'手机号格式错误',
			            bg:'#AE81FF',
			            time:2
			        });
					if(!hasfocus){
						hasfocus = true;
						$(this).focus();
					}
					error++;
				}
			}else if($(this).hasClass("email")){//验证邮箱
				if(!/.+@.+\.[a-zA-Z]{2,4}$/.test(value)){
					$(this).tips({
						side:3,
			            msg:'邮箱格式错误',
			            bg:'#AE81FF',
			            time:2
			        });
					if(!hasfocus){
						hasfocus = true;
						$(this).focus();
					}
					error++;
				}
			}else if($(this).hasClass("postcode")){//邮政编码
				if(!/^[1-9][0-9]{5}$/.test(value)){
					$(this).tips({
						side:3,
			            msg:'邮政编码格式错误',
			            bg:'#AE81FF',
			            time:2
			        });
					if(!hasfocus){
						hasfocus = true;
						$(this).focus();
					}
					error++;
				}
			}else if($(this).hasClass("linephone")){//固定电话
				if(!/^(\d{3,4})-(\d{7,8})$/.test(value)){
					$(this).tips({
						side:3,
			            msg:'固定电话格式错误',
			            bg:'#AE81FF',
			            time:2
			        });
					if(!hasfocus){
						hasfocus = true;
						$(this).focus();
					}
					error++;
				}
			}else if($(this).hasClass("idcode")){//身份证
				if(!/^(\d{18,18}|\d{15,15}|\d{17,17}[xX]{1})$/.test(value)){
					$(this).tips({
						side:3,
			            msg:'身份证格式错误',
			            bg:'#AE81FF',
			            time:2
			        });
					if(!hasfocus){
						hasfocus = true;
						$(this).focus();
					}
					error++;
				}
			}else if($(this).hasClass("money")){//金额，整数位最多十位，小数为最多为两位，可以无小数位
				if(!/^(([0-9]|([1-9][0-9]{0,9}))((\.[0-9]{1,2})?))$/.test(value)){
					$(this).tips({
						side:3,
			            msg:'数据格式错误',
			            bg:'#AE81FF',
			            time:2
			        });
					if(!hasfocus){
						hasfocus = true;
						$(this).focus();
					}
					error++;
				}
			}else if($(this).hasClass("number")){//数字类型校验
				if(!/^[0-9]\d*$/.test(value)){
					$(this).tips({
						side:3,
			            msg:'数据格式错误',
			            bg:'#AE81FF',
			            time:2
			        });
					if(!hasfocus){
						hasfocus = true;
						$(this).focus();
					}
					error++;
				}
				
			}else if($(this).hasClass("charnumber")){//只可以是数字和字母
				if(!/^[A-Za-z]+[0-9]+$/.test(value)){ 
					$(this).tips({
						side:3,
			            msg:'只允许是字母+数字',
			            bg:'#AE81FF',
			            time:2
			        });
					if(!hasfocus){
						hasfocus = true;
						$(this).focus();
					}
					error++;
				}
			}
		}
		$(this).val(value);//把去除空格的值，返回
	});
});

function validateForm(){
	hasfocus = false;
	$('form').find(":input").each(function(){
		$(this).blur();
	})
	//下拉框必选
	$('form').find("select").each(function(){
		var value = $.trim($(this).val());
		var title =  $(this).attr("title");
		if(typeof(title) == 'undefined'){
			title="";
		}
		if($(this).hasClass("required")){
			if(value==""){
				$(this).tips({
					side:3,
			           msg:title+'不能为空',
			           bg:'#AE81FF',
			           time:2
			       });
				error++;
			}
		}
	});
	if(error > 0){
		error =0;
		return false;
	} else{
		return true;
	}
}
