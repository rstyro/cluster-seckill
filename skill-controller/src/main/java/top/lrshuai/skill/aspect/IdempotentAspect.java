package top.lrshuai.skill.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import top.lrshuai.skill.base.BaseController;
import top.lrshuai.skill.commons.constent.Consts;
import top.lrshuai.skill.commons.result.ApiException;
import top.lrshuai.skill.commons.result.ApiResultEnum;
import top.lrshuai.skill.commons.result.ErrorUtils;

/**
 * 幂等性AOP
 */
@Aspect
@Configuration
@Slf4j
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class IdempotentAspect {

    @Autowired
    private RedissonClient redissonClient;

    @Pointcut("@annotation(top.lrshuai.skill.commons.annotation.Idempotent)")
    public void poincut(){}

    @Around("poincut()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        String idempotentKey = createIdempotentKey();
        System.out.println("idempotentKey="+idempotentKey);
        RLock lock = redissonClient.getLock(idempotentKey);
        try {
            if(lock.tryLock()){
                try {
                    Object proceed = pjp.proceed();
                    return  proceed;
                }catch (ApiException e){
                    throw e;
                }catch (Exception e){
                    throw e;
                }finally {
                    lock.unlock();
                }
            }else {
                ErrorUtils.error(ApiResultEnum.REQUST_LIMIT);
            }
            return  null;
        } catch (Exception e) {
            throw e;
        }
    }

    // 创建幂等的分布式锁的key
    public String createIdempotentKey(){
        String url = BaseController.getRequest().getServletPath();
        // 根据自己的业务创建，userId,token之类的
//        String sessionId = BaseController.getSession().getId();
        String userId = BaseController.getRequest().getParameter("userId");
        return new StringBuilder().append(Consts.IDEMPOTENT_KEY).append("-").append(userId).append("-").append(url).toString();
    }

}
