package top.lrshuai.skill.commons.annotation;

import java.lang.annotation.*;

/**
 * 幂等性 注解
 */
@Documented
@Inherited
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Idempotent {
}
