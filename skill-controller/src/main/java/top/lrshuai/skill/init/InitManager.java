package top.lrshuai.skill.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.lrshuai.skill.manager.ManagerContainer;
import top.lrshuai.skill.manager.impl.OrderManager;
import top.lrshuai.skill.mq.RedisDelayedQueueManager;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.LinkedBlockingQueue;

@Component
public class InitManager {

    @Autowired
    private RedisDelayedQueueManager redisDelayedQueueManager;

    @PostConstruct
    public void init() throws Exception {
        ManagerContainer.put(new OrderManager(new LinkedBlockingQueue<>()));
        ManagerContainer.start();
        redisDelayedQueueManager.start();
    }

    @PreDestroy
    public void preDestroy(){
        ManagerContainer.stop();
        redisDelayedQueueManager.stop();
    }
}
