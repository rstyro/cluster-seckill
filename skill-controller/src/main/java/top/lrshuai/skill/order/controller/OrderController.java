package top.lrshuai.skill.order.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.lrshuai.skill.base.BaseController;
import top.lrshuai.skill.commons.annotation.RRateLimit;
import top.lrshuai.skill.commons.constent.RedisConstKey;
import top.lrshuai.skill.commons.result.ApiResultEnum;
import top.lrshuai.skill.commons.result.ErrorUtils;
import top.lrshuai.skill.commons.result.Result;
import top.lrshuai.skill.goods.entity.GoodsSkill;
import top.lrshuai.skill.order.service.IOrderService;
import top.lrshuai.skill.vo.SkillVo;

import java.time.LocalDateTime;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author rstyro
 * @since 2020-06-08
 */
@RestController
@RequestMapping("/order/order")
public class OrderController extends BaseController {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Autowired
    private IOrderService orderService;

    @GetMapping("/detail")
    public Result detail(String orderId){
        if (StringUtils.isEmpty(orderId)) ErrorUtils.error(ApiResultEnum.PARAMETER_NULL);
        return Result.ok();
    }


    /**
     * 抢购
     * @return
     */
    @RRateLimit(limitKey = "skill",limit = 1000)
    @PostMapping("/skill")
    public Result skill(SkillVo vo){
        if (StringUtils.isEmpty(vo.getSkillId()) || StringUtils.isEmpty(vo.getUserId())) ErrorUtils.error(ApiResultEnum.PARAMETER_NULL);
        checkCanSkill(vo.getSkillId());
        return orderService.skill(vo);
    }

    public void checkCanSkill(Long skillId){
        GoodsSkill goodsSkill = (GoodsSkill) redisTemplate.opsForValue().get(RedisConstKey.append(RedisConstKey.SKILL_GOODS, skillId));
        if(goodsSkill==null)ErrorUtils.error(ApiResultEnum.GOODS_SKILL_NOT_FOUND);
        if(goodsSkill.getStartTime().compareTo(LocalDateTime.now())>0)ErrorUtils.error(ApiResultEnum.GOODS_SKILL_NO_START);
    }

}
