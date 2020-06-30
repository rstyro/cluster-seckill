package top.lrshuai.skill.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.lrshuai.skill.commons.constent.Consts;
import top.lrshuai.skill.commons.constent.RedisConstKey;
import top.lrshuai.skill.commons.result.ApiResultEnum;
import top.lrshuai.skill.commons.result.ErrorUtils;
import top.lrshuai.skill.commons.result.Result;
import top.lrshuai.skill.commons.utils.LocalDateTimeUtils;
import top.lrshuai.skill.commons.utils.SnowFlake;
import top.lrshuai.skill.goods.entity.GoodsSkill;
import top.lrshuai.skill.goods.entity.GoodsSkillSuccess;
import top.lrshuai.skill.goods.entity.GoodsSku;
import top.lrshuai.skill.goods.service.IGoodsSkillService;
import top.lrshuai.skill.goods.service.IGoodsSkillSuccessService;
import top.lrshuai.skill.goods.service.IGoodsSkuService;
import top.lrshuai.skill.manager.ManagerContainer;
import top.lrshuai.skill.manager.impl.OrderManager;
import top.lrshuai.skill.mq.RedisDelayedQueueManager;
import top.lrshuai.skill.order.entity.OrderGoods;
import top.lrshuai.skill.order.entity.Orders;
import top.lrshuai.skill.order.mapper.OrderMapper;
import top.lrshuai.skill.order.service.IOrderGoodsService;
import top.lrshuai.skill.order.service.IOrderService;
import top.lrshuai.skill.vo.SkillVo;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author rstyro
 * @since 2020-06-08
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Orders> implements IOrderService {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Autowired
    private RedisDelayedQueueManager<String> redisDelayedQueueManager;

    @Autowired
    private IGoodsSkillSuccessService goodsSkillSuccessService;

    @Autowired
    private IGoodsSkillService goodsSkillService;

    @Autowired
    private IGoodsSkuService goodsSkuService;

    @Autowired
    private IOrderGoodsService orderGoodsService;

    @Autowired
    private IOrderService orderService;

    @Transactional
    public Result skill(SkillVo vo) {
        if(redisTemplate.opsForHash().hasKey(RedisConstKey.append(RedisConstKey.SKILL_GOODS_SUCCESS,vo.getSkillId()),vo.getUserId().toString())){
            ErrorUtils.error(ApiResultEnum.GOODS_NOT_REPEATABLE_PURCHASE);
        }
        String stock = RedisConstKey.append(RedisConstKey.SKILL_GOODS_STOCK,vo.getSkillId());
        // redis 原子操作
        if(redisTemplate.opsForValue().decrement(stock)>=0){
            redisTemplate.opsForHash().put(RedisConstKey.append(RedisConstKey.SKILL_GOODS_SUCCESS,vo.getSkillId()),vo.getUserId().toString(),"1");
            GoodsSkillSuccess goodsSkillSuccess = new GoodsSkillSuccess().setGoodsSkillId(vo.getSkillId()).setUserId(vo.getUserId()).setIsDeal(false).setCreateTime(LocalDateTime.now());
            // 搞个备份
            goodsSkillSuccessService.save(goodsSkillSuccess);
            // 放入队列 解耦
            OrderManager manager = ManagerContainer.getManager(OrderManager.class);
            manager.put(goodsSkillSuccess);
        }else {
            ErrorUtils.error(ApiResultEnum.GOODS_SKILL_STOCK_SHORTAGE);
        }
        return Result.ok();
    }

    @Transactional
    public void placeOrder(GoodsSkillSuccess obj) {
        String orderNo = saveOrder(obj);
        updateOrder(obj);
        // 时间内未付款，则自动关闭订单
        redisDelayedQueueManager.offer(orderNo,1, TimeUnit.MINUTES);
    }

    /**
     * 到期未支付，自动关闭订单
     * @param orderNo
     */
    @Override
    public void cancelOrder(String orderNo) {
        Orders orders = orderService.getOne(new LambdaQueryWrapper<Orders>().eq(Orders::getOrderNo, orderNo));
        if(Consts.OrderStatus.UN_PAY==orders.getStatus().intValue()){
            orders.setStatus(Consts.OrderStatus.CANCEL);
            orderService.updateById(orders);
        }
    }

    public String saveOrder(GoodsSkillSuccess obj){
        GoodsSkill goodsSkill = (GoodsSkill) redisTemplate.opsForValue().get(RedisConstKey.append(RedisConstKey.SKILL_GOODS,obj.getGoodsSkillId()));
        GoodsSku goodsSku = goodsSkuService.getById(goodsSkill.getGoodsSkuId());
        Orders orders = new Orders();
        String orderNo=getOrderNo();
        orders.setOrderNo(orderNo);
        orders.setUserId(obj.getUserId());
        orders.setRealTotalMoney(goodsSku.getGoodsPrice());
        orders.setGoodsMoney(goodsSku.getGoodsPrice());
        orders.setTotalMoney(goodsSku.getGoodsPrice());
        orders.setTotalPayFee(goodsSku.getGoodsPrice());
        orders.setIsPay(false);
        orders.setStatus(Consts.OrderStatus.UN_PAY);
        orders.setCreateTime(LocalDateTime.now());
        orderService.save(orders);
        OrderGoods orderGoods = new OrderGoods();
        orderGoods.setOrderNo(orderNo);
        orderGoods.setGoodsType(Consts.GoodsType.SKILL);
        orderGoods.setGoodsId(goodsSkill.getGoodsId());
        orderGoods.setGoodsSkuId(goodsSku.getId());
        orderGoods.setShopId(1l);
        orderGoods.setGoodsId(goodsSku.getGoodsId());
        orderGoods.setGoodsNum(1);
        orderGoods.setMoney(goodsSku.getGoodsPrice());
        orderGoods.setRealMoney(goodsSku.getGoodsPrice());
        orderGoods.setVersion(1l);
        orderGoods.setCreateTime(LocalDateTime.now());
        orderGoodsService.save(orderGoods);
        return orderNo;
    }
    // 订单编号
    public String getOrderNo(){
        Long snowId = SnowFlake.newInstance().nextId();
        String s = LocalDateTimeUtils.formatNow("yyyyMMdd");
        return s.concat(snowId.toString());
    }

    public void updateOrder(GoodsSkillSuccess obj){
        obj.setIsDeal(true);
        goodsSkillSuccessService.updateById(obj);
        GoodsSkill goodsSkill = goodsSkillService.getById(obj.getGoodsSkillId());
        goodsSkill.setStock(goodsSkill.getStock()-1);
        goodsSkillService.updateById(goodsSkill);
    }



}
