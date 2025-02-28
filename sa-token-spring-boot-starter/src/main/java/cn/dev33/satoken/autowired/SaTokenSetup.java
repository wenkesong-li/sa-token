package cn.dev33.satoken.autowired;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 将此注解加到springboot启动类上，即可完成sa-token与springboot的集成 
 * <p>注: v1.7版本以上已不再需要此注解，直接引入sa-token-spring-boot-starter依赖即可
 * <p>请直接忽略此注解 
 * @author kong
 *
 */
@Documented
@Target({java.lang.annotation.ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Configuration
@Import({SaTokenSpringAutowired.class})
public @interface SaTokenSetup {
	
}
