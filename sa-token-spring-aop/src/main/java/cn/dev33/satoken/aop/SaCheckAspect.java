package cn.dev33.satoken.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import cn.dev33.satoken.stp.StpLogic;
import cn.dev33.satoken.stp.StpUtil;

/**
 * sa-token 基于 Spring Aop 的注解鉴权
 * 
 * @author kong
 */
@Aspect
@Component
@Order(SaCheckAspect.aspectOrder)
public class SaCheckAspect {
	
	/**
	 * 切面执行顺序
	 */
	public static final int aspectOrder = -100;
	
	/**
	 * 构建
	 */
	public SaCheckAspect() {
	}

	/**
	 * 获取本切面使用的StpLogic 
	 */
	public StpLogic getStpLogic() {
		return StpUtil.stpLogic;
	}

	/**
	 * 定义AOP签名 (切入所有使用sa-token鉴权注解的方法)
	 */
	public static final String POINTCUT_SIGN = "@within(cn.dev33.satoken.annotation.SaCheckLogin) || @annotation(cn.dev33.satoken.annotation.SaCheckLogin) || "
			+ "@within(cn.dev33.satoken.annotation.SaCheckRole) || @annotation(cn.dev33.satoken.annotation.SaCheckRole) || "
			+ "@within(cn.dev33.satoken.annotation.SaCheckPermission) || @annotation(cn.dev33.satoken.annotation.SaCheckPermission)";

	/**
	 * 声明AOP签名
	 */
	@Pointcut(POINTCUT_SIGN)
	public void pointcut() {
	}

	/**
	 * 环绕切入
	 * 
	 * @param joinPoint 切面对象
	 * @return 底层方法执行后的返回值
	 * @throws Throwable 底层方法抛出的异常
	 */
	@Around("pointcut()")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		// 注解鉴权
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		getStpLogic().checkMethodAnnotation(signature.getMethod());
		try {
			// 执行原有逻辑
			Object obj = joinPoint.proceed();
			return obj;
		} catch (Throwable e) {
			throw e;
		}
	}

}
