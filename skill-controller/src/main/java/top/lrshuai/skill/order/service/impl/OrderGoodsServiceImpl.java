package top.lrshuai.skill.order.service.impl;

import top.lrshuai.skill.order.entity.OrderGoods;
import top.lrshuai.skill.order.mapper.OrderGoodsMapper;
import top.lrshuai.skill.order.service.IOrderGoodsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单商品表 服务实现类
 * </p>
 *
 * @author rstyro
 * @since 2020-06-08
 */
@Service
public class OrderGoodsServiceImpl extends ServiceImpl<OrderGoodsMapper, OrderGoods> implements IOrderGoodsService {

}
