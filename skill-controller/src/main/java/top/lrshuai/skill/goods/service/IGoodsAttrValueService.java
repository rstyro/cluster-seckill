package top.lrshuai.skill.goods.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.lrshuai.skill.goods.entity.GoodsAttrValue;

import java.util.List;

/**
 * <p>
 * 商品属性值表 服务类
 * </p>
 *
 * @author rstyro
 * @since 2020-06-08
 */
public interface IGoodsAttrValueService extends IService<GoodsAttrValue> {
    public List<GoodsAttrValue> getAttrValues(String ids);
}
