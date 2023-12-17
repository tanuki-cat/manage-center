package me.zhengjie.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * @author wangzy
 * @date 2023/12/17 10:41
 * @describe 检验创建人
 * @since version 1.0
 */
@Inherited
@Documented
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Target({java.lang.annotation.ElementType.METHOD})
public @interface CheckCreate {
    String[] roles() default {};
    Class  clazz() default Object.class;
    String[] filedMethod() default {};
}
