package org.hutrace.handy.mybatis.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.hutrace.handy.annotation.Controller;
import org.hutrace.handy.mybatis.MyBatisConfig;

/**
 * <p>通过此注解可以指定使用的数据库
 * <p>注：配置多个值将会使用配置的多个数据库工厂执行对应的业务，此处一般用于多库备份。
 * <p>此注解仅在{@link Controller}注解下生效
 * @author hutrace
 * @since 1.8
 * @version 1.0
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DatabaseFactory {
	
	/**
	 * @return 使用的数据库实列工厂
	 */
	String value() default MyBatisConfig.DEFAULT_ID;
	
}
