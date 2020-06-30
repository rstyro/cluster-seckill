package top.lrshuai.skill.goods.service;

import top.lrshuai.skill.commons.result.Result;
import top.lrshuai.skill.goods.entity.Goods;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 商品表 服务类
 * </p>
 *
 * @author rstyro
 * @since 2020-06-08
 */
public interface IGoodsService extends IService<Goods> {
    public Result getGoodsDetail(String goodsId);
}
