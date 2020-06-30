package top.lrshuai.skill.commons.annotation;


import java.lang.annotation.*;

/**
 * redisson 限流
 */
@Documented
@Inherited
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RRateLimit {
    String limitKey() default "limit"; //key
    long limit() default 10;
    long milliseconds() default 1000;
}
