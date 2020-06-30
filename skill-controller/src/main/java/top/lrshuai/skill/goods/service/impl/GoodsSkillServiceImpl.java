package top.lrshuai.skill.goods.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import top.lrshuai.skill.commons.constent.RedisConstKey;
import top.lrshuai.skill.commons.result.Result;
import top.lrshuai.skill.commons.utils.LocalDateTimeUtils;
import top.lrshuai.skill.goods.entity.GoodsAttrValue;
import top.lrshuai.skill.goods.entity.GoodsSkill;
import top.lrshuai.skill.goods.entity.GoodsSku;
import top.lrshuai.skill.goods.mapper.GoodsSkillMapper;
import top.lrshuai.skill.goods.service.IGoodsAttrValueService;
import top.lrshuai.skill.goods.service.IGoodsSkillService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.lrshuai.skill.goods.service.IGoodsSkuService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 秒杀商品表 服务实现类
 * </p>
 *
 * @author rstyro
 * @since 2020-06-08
 */
@Service
@Transactional
public class GoodsSkillServiceImpl extends ServiceImpl<GoodsSkillMapper, GoodsSkill> implements IGoodsSkillService {

    @Autowired
    private IGoodsAttrValueService goodsAttrValueService;

    @Autowired
    private IGoodsSkuService goodsSkuService;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Override
    public Result getGoodsSkillList() {
        List<GoodsSkill> list = this.list();
        list.forEach(i->{
            GoodsSku sku = goodsSkuService.getById(i.getGoodsSkuId());
            List<GoodsAttrValue> attrValues = goodsAttrValueService.getAttrValues(sku.getAttrPath());
            if(attrValues==null)attrValues=Lists.newArrayList();
            i.setAttrs(attrValues);
        });
        return Result.ok(list);
    }

    @Override
    public Result reloadGoods() {
        List<GoodsSkill> list = this.list(new LambdaQueryWrapper<GoodsSkill>().ge(GoodsSkill::getEndTime, LocalDate.now()));
        list.forEach(i->{
            redisTemplate.opsForValue().set(RedisConstKey.append(RedisConstKey.SKILL_GOODS,i.getId()),i);
            redisTemplate.opsForValue().set(RedisConstKey.append(RedisConstKey.SKILL_GOODS_STOCK,i.getId()),i.getStock());
            redisTemplate.delete(RedisConstKey.append(RedisConstKey.SKILL_GOODS_SUCCESS,i.getId()));
        });
        return Result.ok(list);
    }
}
