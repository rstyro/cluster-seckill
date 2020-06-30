package top.lrshuai.skill.test;

import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.lrshuai.skill.commons.annotation.RRateLimit;
import top.lrshuai.skill.commons.result.Result;

@RestController
@RequestMapping("/limit")
public class LimitController {

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @GetMapping("/req1")
    @RRateLimit(limitKey = "req1")
    public Object req1() {
        System.out.println("req1===");
//        redisTemplate.opsForValue().set("test", ApiResultEnum.ERROR);
        return Result.ok();
    }

    @GetMapping("/req2")
    @RRateLimit(limit = 100,limitKey = "req2")
    public Object req2(){
        System.out.println("req2===");
//        Object test = redisTemplate.opsForValue().get("test");
//        System.out.println("test="+test);
        return Result.ok();
    }


    @GetMapping("/req3")
    @RRateLimit(limit = 100,limitKey = "req3",milliseconds = 2000)
    public Object req3(){
        System.out.println("req3===");
        return Result.ok();
    }
}
