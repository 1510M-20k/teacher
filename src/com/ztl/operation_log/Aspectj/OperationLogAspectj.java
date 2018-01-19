 package com.ztl.operation_log.Aspectj;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import com.ztl.common.Const;
import com.ztl.common.GetUUID;
import com.ztl.common.GeyUserIP;
import com.ztl.common.PageData;
import com.ztl.common.Tools;
import com.ztl.operation_log.annotation.Log;
import com.ztl.operation_log.enumeration.Successed;
import com.ztl.operation_log.service.OperationLogService;


/**
 * 记录被{@link com.ztl.operation_log.annotation.Log}注解标记的方法，所产生的操作日志<br/>
 * 其中在执行方法前获取相应的注解值和方法参数；<br/>
 * 被标记方法执行完成后，保存所产生的日志；<br/>
 * 如果标记方法在执行过程中失败或异常，则记录失败的日志。
 *
 */
@Aspect
@Component
public class OperationLogAspectj {
	
	private static org.apache.commons.logging.Log logger = LogFactory.getLog(OperationLogAspectj.class);
	
	@Autowired
	private OperationLogService operationLogService;
	private Log logAnn;
	private String loginname;
	private String bid;
	private String agent;
	private String userip;
	private String content;
	private String logtype;
	@Pointcut("@annotation(com.ztl.operation_log.annotation.Log)")
	public void executeLog(){};
	
	@SuppressWarnings("rawtypes")
	@Before(value = "executeLog()")
	public void initOperationLog(JoinPoint jp){
		logger.trace("切面 @Before OperationLogAspectj initOperationLog(@annotation(com.ztl.operation_log.annotation.Log))");
		String _signatureName = jp.getSignature().getName();
		Class targetClass = jp.getTarget().getClass();
		logger.trace(String.format("Join Point kind : %s", jp.getKind()));
		logger.trace(String.format("Signature declaring type : %s", jp.getSignature().getDeclaringTypeName()));
		logger.trace(String.format("Signature name : %s", _signatureName));
		logger.trace(String.format("Target class : %s", targetClass.getName()));
		logger.trace(String.format("This class : %s", jp.getThis().getClass().getName()));
		
		Object[] args = jp.getArgs();
		getRequestArgs(args);
		
		Method[] targetMethods = targetClass.getDeclaredMethods();
		for (Method _method : targetMethods) {
			if (_method.getName().equalsIgnoreCase(_signatureName)) {
				logAnn = _method.getAnnotation(Log.class);
			}
		}
	}
	
	@AfterReturning(value = "executeLog()", returning = "result")
	public void executeResultMethod(Object result){
		logger.debug("=====>返回结果内容：" + ReflectionToStringBuilder.toString(result, ToStringStyle.MULTI_LINE_STYLE));
		if (result != null) {
			Map<String, Object> tageResult = null;
			if (result instanceof ModelAndView){
				tageResult =( (ModelAndView) result).getModel();
			} else if (result instanceof Map){
				tageResult = (Map<String, Object>) result;
			}
			if (tageResult == null){
				//throw new IllegalArgumentException("您执行的方法的返回值类型错误，请返回 java.util.Map 或 org.springframework.web.servlet.ModelAndView类型");
				logger.trace("您执行的方法的返回值类型错误，请返回 java.util.Map 或 org.springframework.web.servlet.ModelAndView类型");
			}
			if(this.bid == null) {
				bid = (String) tageResult.get("bid");
			}
			if(Tools.isEmpty(loginname)){
				this.loginname = (String) tageResult.get(Const.USER_NAME);
			}
			if(Tools.isEmpty(loginname)){
				loginname="未登录用户";
			}
			content = (String) tageResult.get("content");
			logtype = (String) tageResult.get("logtype");
		}
		this.createLog(Successed.SUCCESSED, "");
	}
	
	@AfterThrowing(value = "executeLog()", argNames = "exception", throwing = "exception")
	public void throwProcess(Exception exception){
		logger.trace("执行带有返回值的方法");
		this.bid = "-1";
		this.createLog(Successed.FAILED, exception.toString());
	}
	
	private void createLog(Successed successed, String desc){
		if (bid == null){
			//throw new IllegalArgumentException("您执行的方法的参数和返回值中都没有bId(business id)参数，导致无法保存日志");
			logger.trace("您执行的方法的参数和返回值中都没有bid(business id)参数");
		}
		PageData oLog = new PageData();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		oLog.put("id", GetUUID.get32UUID());
		oLog.put("loginname", loginname);
		oLog.put("operationtime", sdf.format(new Date()));
		oLog.put("agent",agent);
		oLog.put("content",logAnn.content());
		oLog.put("type",logAnn.type().toString());
		if(content != null && !content.equals("")){
			oLog.put("content",content);
		}
		if(logtype != null && !logtype.equals("")){
			oLog.put("type",logtype);
		}
		oLog.put("userip",userip);
		oLog.put("bid",bid);
		oLog.put("successed",successed);
		oLog.put("detail",desc);
		logger.debug("=====>日志内容：" + ReflectionToStringBuilder.toString(oLog, ToStringStyle.MULTI_LINE_STYLE));
		operationLogService.saveLog(oLog);
	}

	/**
	 * 从{@link javax.servlet.http.HttpSession}中获取登录用户的id
	 * @param args controller方法的参数
	 */
	private void getRequestArgs(Object[] args) {
		HttpServletRequest req = null;
		if (args.length < 1)
			throw new IllegalArgumentException("您执行的方法没有任何参数，导致无法获取操作用户信息");
		for (Object arg : args) {
			if(arg instanceof HttpServletRequest)
				req = (HttpServletRequest) arg;
		}
		if (req == null)
			throw new IllegalArgumentException("调用@Log的方法参数中，没有HttpServletRequest参数，导致无法获取操作用户信息");
		loginname = (String) req.getSession().getAttribute(Const.USER_NAME);
		bid = req.getAttribute("bid") == null ? req.getParameter("bid") : (String) req.getAttribute("bid");
		userip = GeyUserIP.getIpAddr(req);
		agent = req.getHeader("User-Agent");
	}
}
