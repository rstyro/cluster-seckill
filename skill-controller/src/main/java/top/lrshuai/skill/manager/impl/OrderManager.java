package top.lrshuai.skill.manager.impl;

import com.alibaba.fastjson.JSON;
import top.lrshuai.skill.goods.entity.GoodsSkillSuccess;
import top.lrshuai.skill.manager.AbstractManager;
import top.lrshuai.skill.order.service.IOrderService;
import top.lrshuai.skill.util.SpringContextUtil;

import java.util.concurrent.BlockingQueue;

public class OrderManager extends AbstractManager {

    public OrderManager(BlockingQueue<Object> queue) {
        super(queue);
    }

    @Override
    public boolean handle(Object data) throws Exception {
        GoodsSkillSuccess goodsSkillSuccess = JSON.parseObject(JSON.toJSONString(data), GoodsSkillSuccess.class);
        IOrderService orderService = SpringContextUtil.getBean(IOrderService.class);
        orderService.placeOrder(goodsSkillSuccess);
        return true;
    }
}
