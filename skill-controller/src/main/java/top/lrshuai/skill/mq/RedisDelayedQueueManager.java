package top.lrshuai.skill.mq;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.lrshuai.skill.commons.constent.RedisConstKey;
import top.lrshuai.skill.order.service.IOrderService;

import java.util.concurrent.TimeUnit;

/**
 * 延迟队列，订单长时间不付款则关闭订单
 * @param <T>
 */
@Component
@Slf4j
public class RedisDelayedQueueManager<T> {

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private IOrderService orderService;

    private volatile RDelayedQueue<T> delayedQueue;

    private volatile RBlockingQueue<T> rBlockingQueue;

    private volatile boolean isRun;

    private Thread workThread;

    public void start(){
        isRun=true;
        run();
    }

    private void run(){
        if(workThread ==null){
            Work work = new Work();
            workThread=new Thread(work);
        }
        log.info("{}:启动中...",RedisConstKey.SKILL_DELAYED_GOODS_QUEUE);
        workThread.start();
    }

    public void offer(T orderNo, long delay, TimeUnit timeUnit){
        getRDelayedQueue().offer(orderNo,delay,timeUnit);
    }

    private RDelayedQueue<T> getRDelayedQueue(){
        synchronized (this){
            if(delayedQueue == null){
                delayedQueue=redissonClient.getDelayedQueue(getRBlockingQueue());
            }
        }
        return delayedQueue;
    }
    private RBlockingQueue<T> getRBlockingQueue(){
        synchronized (this){
            if(rBlockingQueue == null){
                rBlockingQueue = redissonClient.getBlockingQueue(RedisConstKey.SKILL_DELAYED_GOODS_QUEUE);
            }
        }
        return rBlockingQueue;
    }

    public void stop(){
        log.info("{}:正在停止！！！",RedisConstKey.SKILL_DELAYED_GOODS_QUEUE);
        isRun=false;
    }

    private class Work implements Runnable{

        @Override
        public void run() {
            while (isRun){
                try {
                    T t = getRBlockingQueue().take();
                    if(null==t)continue;
                    System.out.println("订单号orderNo=="+t);
                    orderService.cancelOrder((String) t);
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
