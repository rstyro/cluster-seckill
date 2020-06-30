package top.lrshuai.skill.order.service;

import top.lrshuai.skill.commons.result.Result;
import top.lrshuai.skill.goods.entity.GoodsSkillSuccess;
import top.lrshuai.skill.order.entity.Orders;
import com.baomidou.mybatisplus.extension.service.IService;
import top.lrshuai.skill.vo.SkillVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author rstyro
 * @since 2020-06-08
 */
public interface IOrderService extends IService<Orders> {
    public Result skill(SkillVo vo);
    public void placeOrder(GoodsSkillSuccess obj);
    public void cancelOrder(String orderNo);
}
