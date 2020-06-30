package top.lrshuai.skill.goods.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.lrshuai.skill.goods.entity.GoodsAttrValue;
import top.lrshuai.skill.goods.mapper.GoodsAttrValueMapper;
import top.lrshuai.skill.goods.service.IGoodsAttrValueService;

import java.util.List;

/**
 * <p>
 * 商品属性值表 服务实现类
 * </p>
 *
 * @author rstyro
 * @since 2020-06-08
 */
@Service
public class GoodsAttrValueServiceImpl extends ServiceImpl<GoodsAttrValueMapper, GoodsAttrValue> implements IGoodsAttrValueService {
    @Autowired
    private GoodsAttrValueMapper getGoodsAttrValues;

    public List<GoodsAttrValue> getAttrValues(String ids) {
        return getGoodsAttrValues.getGoodsAttrValues(ids);
    }
}
