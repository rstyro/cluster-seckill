package top.lrshuai.skill.goods.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.lrshuai.skill.commons.constent.Consts;
import top.lrshuai.skill.commons.result.Result;
import top.lrshuai.skill.goods.entity.Goods;
import top.lrshuai.skill.goods.entity.GoodsAttrValue;
import top.lrshuai.skill.goods.entity.GoodsSku;
import top.lrshuai.skill.goods.mapper.GoodsMapper;
import top.lrshuai.skill.goods.service.IGoodsAttrValueService;
import top.lrshuai.skill.goods.service.IGoodsService;
import top.lrshuai.skill.goods.service.IGoodsSkuService;

import java.util.List;

/**
 * <p>
 * 商品表 服务实现类
 * </p>
 *
 * @author rstyro
 * @since 2020-06-08
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements IGoodsService {

    @Autowired
    private IGoodsSkuService goodsSkuService;

    @Autowired
    private IGoodsAttrValueService goodsAttrValueService;

    @Override
    public Result getGoodsDetail(String goodsId) {
        GoodsSku one = goodsSkuService.getOne(new LambdaQueryWrapper<GoodsSku>().eq(GoodsSku::getIsDefault, Consts.YES).eq(GoodsSku::getIsDel, Consts.NO));
        Goods goods = this.getById(goodsId);
        if(one !=null){
            List<GoodsAttrValue> attrValues = goodsAttrValueService.getAttrValues(one.getAttrPath());
            if(attrValues!=null)goods.setAttrs(attrValues);
        }
        return Result.ok(goods);
    }
}
