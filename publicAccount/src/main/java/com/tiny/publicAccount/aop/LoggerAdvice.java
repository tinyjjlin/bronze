package com.tiny.publicAccount.aop;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;





/**
 * 
 * @author Administrator
 *
 */
@Aspect
@Service
public class LoggerAdvice {
	protected Logger logger =  LoggerFactory.getLogger(this.getClass());
	
	@Before("within(com.tiny..*) && @annotation(logLocation)")
	public void addBeforeLogger(JoinPoint joinPoint, LoggerLocation logLocation) {
		logger.info("执行 " + logLocation.desc() + " 开始");
		logger.info(joinPoint.getSignature().toString());
		logger.info(parseParames(joinPoint.getArgs()));
	}
	
	@AfterReturning("within(com.tiny..*) && @annotation(logLocation)")
	public void addAfterReturningLogger(JoinPoint joinPoint, LoggerLocation logLocation) {
		logger.info("执行 " + logLocation.desc() + " 结束");
	}
	
	private String parseParames(Object[] parames) {
		if (null == parames || parames.length <= 0 || parames.length >1024) {
			return "";
		}
		StringBuffer param = new StringBuffer("传入参数[{}] ");
		for (Object obj : parames) {
			//TODO
			param.append(ToStringBuilder.reflectionToString(obj)).append("  ");
		}
		return param.toString();
	}

}
