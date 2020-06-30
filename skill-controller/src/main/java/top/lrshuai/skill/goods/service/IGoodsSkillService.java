package top.lrshuai.skill.goods.service;

import top.lrshuai.skill.commons.result.Result;
import top.lrshuai.skill.goods.entity.GoodsSkill;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 秒杀商品表 服务类
 * </p>
 *
 * @author rstyro
 * @since 2020-06-08
 */
public interface IGoodsSkillService extends IService<GoodsSkill> {
    public Result getGoodsSkillList();
    public Result reloadGoods();
}
