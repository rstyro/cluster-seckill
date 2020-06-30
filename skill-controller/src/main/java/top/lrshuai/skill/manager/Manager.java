package top.lrshuai.skill.manager;

public interface Manager {

    // 获取Manager名字
    String getName();

    // 启动前
    void onStart();

    // 启动
    void start();

    // 添加对象到队列
    boolean put(Object data);

    // 业务处理方法
    boolean handle(Object data) throws Exception;

    // 停止前
    void onStop();

    // 停止
    void stop();

    // 队列size，未处理完的任务数
    long size();
}
