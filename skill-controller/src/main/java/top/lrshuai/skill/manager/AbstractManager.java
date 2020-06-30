package top.lrshuai.skill.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.lrshuai.skill.commons.result.ApiException;
import top.lrshuai.skill.commons.result.ApiResultEnum;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public abstract class AbstractManager implements Manager {
    protected Logger log;
    // 是否运行状态标识
    protected volatile boolean isRun = false;
    // 是否同步执行
    protected volatile boolean sync;
    // 是否执行错误后重试
    protected volatile boolean retry;
    // 名称
    protected String name;

    // 数据队列
    protected BlockingQueue<Object> queue;

    // 超时时间，毫秒
    public static final int TIMEOUT = 300;

    // 处理信号,非0表示有正在处理的数据
    protected volatile AtomicLong signal = new AtomicLong(0);

    protected Thread workThread;
    // 异步任务线程池
    public ThreadPoolExecutor pool;

    public AbstractManager(BlockingQueue<Object> queue){
        this(queue,null);
    }
    public AbstractManager(BlockingQueue<Object> queue,String name){
        this(queue,name,true);
    }
    public AbstractManager(BlockingQueue<Object> queue,String name,boolean isSync){
        this(queue,name,isSync,false);
    }

    public AbstractManager(BlockingQueue<Object> queue,String name,boolean isSync, boolean isRetry) {
        this(queue,name, isSync, isRetry, 2);
    }

    public AbstractManager(BlockingQueue<Object> queue,String name, boolean isSync, boolean isRetry, int poolSize) {
        this.queue=queue;
        this.name=name==null?this.getClass().getSimpleName():name;
        String newThreadName = this.name;
        this.workThread = new Thread(getWorker());
        this.workThread.setName(this.name);
        this.workThread.setDaemon(true);
        this.sync = isSync;
        this.retry = isRetry;
        this.log = LoggerFactory.getLogger(this.getClass());
        if (!sync) pool = new ThreadPoolExecutor(poolSize, poolSize, 0l, TimeUnit.SECONDS, new LinkedBlockingQueue<>(), new ThreadFactory() {
            AtomicInteger num = new AtomicInteger(0);

            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setName(newThreadName + "(" + num.incrementAndGet() + ")");
                return t;
            }
        });

    }

    public <T extends Manager> T getManager(Class<T> cls) {
        return ManagerContainer.getManager(cls);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void onStart() {
        log.info("正在启动...");
    }

    @Override
    public synchronized void start() {
        if (this.isRun) {
            return;
        }
        isRun=true;
        this.onStart();
        workThread.start();
    }

    @Override
    public boolean put(Object data) {
        if (!isRun) return false;
        return queue.offer(data);
    }

    @Override
    public void onStop() {
        log.info("即将关闭...");
    }

    @Override
    public void stop() {
        isRun=false;
        onStop();
        log.info("[" +  this.name + "] stopped!!!");
    }

    @Override
    public long size() {
        return queue.size();
    }

    // 重载方法
    protected Runnable getWorker() {
        return new Worker();
    }
    private class Worker implements Runnable{

        private void working(Object msg){
            signal.incrementAndGet();
            // 同步执行
            if (sync) {
                try {
                    boolean ok = handle(msg);
                    if (!ok && retry) throw new ApiException(ApiResultEnum.BUSINESS_RUN_ERROR);
                    signal.decrementAndGet();
                } catch (Exception e) {
                    if (retry) queue.add(msg);
                    signal.decrementAndGet();
                    log.error(e.getMessage());
                }
            }
            // 异步执行
            else {
                pool.submit(() -> {
                    try {
                        boolean ok = handle(msg);
                        if (!ok && retry) throw new ApiException(ApiResultEnum.BUSINESS_RUN_ERROR);
                        signal.decrementAndGet();
                    } catch (Exception e) {
                        if (retry) {
                            try {
                                queue.add(msg);
                            } catch (Exception err) {
                                err.printStackTrace();
                            }
                        }
                        signal.decrementAndGet();
                        log.error(e.getMessage());
                    }
                });
            }
        }

        @Override
        public void run() {
            while (isRun || queue.size() != 0){
                Object object = null;
                try {
                    object = queue.poll(TIMEOUT,TimeUnit.MILLISECONDS);
                    Thread.sleep(0);
                    if (object == null)continue;
                    working(object);
                }catch (Throwable e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
    }

}
