package top.lrshuai.skill.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import top.lrshuai.skill.commons.annotation.RRateLimit;
import top.lrshuai.skill.commons.result.ApiResultEnum;
import top.lrshuai.skill.commons.result.ErrorUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * 限流aop
 */
@Aspect
@Configuration
@Slf4j
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class LimitAspect {

    @Autowired
    private RedissonClient redissonClient;

    @Pointcut("@annotation(top.lrshuai.skill.commons.annotation.RRateLimit)")
    public void pointRRateLimit(){}

    @Before("pointRRateLimit()")
    public void beforeRateLimit(JoinPoint joinPoint) throws Exception {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        RRateLimit rRateLimitAnnotate = getTagAnnotation(method, RRateLimit.class);
        if(rRateLimitAnnotate != null){
            RRateLimiter rateLimiter = redissonClient.getRateLimiter(rRateLimitAnnotate.limitKey());
            checkRateLimitConfig(rateLimiter,rRateLimitAnnotate);
            if(!rateLimiter.tryAcquire()){
                ErrorUtils.error(ApiResultEnum.LIMIT_SILL);
            }

        }
    }

    // 初始化配置
    public void checkRateLimitConfig(RRateLimiter rateLimiter,RRateLimit rRateLimitAnnotate){
        if(!rateLimiter.isExists()){
            rateLimiter.trySetRate(RateType.PER_CLIENT, rRateLimitAnnotate.limit(), rRateLimitAnnotate.milliseconds(), RateIntervalUnit.MILLISECONDS);
        }else {
            // 如果存在，但是修改了速率则更新配置
            RateLimiterConfig config = rateLimiter.getConfig();
            if(config.getRate().compareTo(rRateLimitAnnotate.limit()) !=0 || config.getRateInterval().compareTo(rRateLimitAnnotate.milliseconds()) !=0){
                rateLimiter.delete();
                rateLimiter.trySetRate(RateType.PER_CLIENT, rRateLimitAnnotate.limit(), rRateLimitAnnotate.milliseconds(), RateIntervalUnit.MILLISECONDS);
            }
        }
    }

    /**
     * 获取方法获取类上的注解
     * @param method
     * @param annotationClass
     * @param <A>
     * @return
     */
    public static  <A extends Annotation> A getTagAnnotation(Method method, Class<A> annotationClass) {
        // 获取方法中是否包含注解
        Annotation methodAnnotate = method.getAnnotation(annotationClass);
        //获取 类中是否包含注解，也就是controller 是否有注解
        Annotation classAnnotate = method.getDeclaringClass().getAnnotation(annotationClass);
        return (A) (methodAnnotate!= null?methodAnnotate:classAnnotate);
    }
}
