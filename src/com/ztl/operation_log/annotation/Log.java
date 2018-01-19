package com.ztl.operation_log.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.ztl.operation_log.enumeration.LogType;


/**
 * 记录用户日常操作的日志<br/>
 * type ： 操作的类型，{@link}<br/>
 * content ： 操作内容<br/>
 * 注：该annotation使用在controller中的方法上，使用此annotation时，方法参数中必须有HttpServletRequest参数，否则无法获取用户信息。</br>
 * 另外，controller方法的HttpServletRequest参数中或方法的返回值，两者必须有一个包含bId（business id），指定该日志属于那个业务。
 * @author xiajiang
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Log {
	
	public LogType type();
	
	public String content();

}
