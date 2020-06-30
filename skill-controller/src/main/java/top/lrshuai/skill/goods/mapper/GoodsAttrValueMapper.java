package top.lrshuai.skill.goods.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import top.lrshuai.skill.goods.entity.GoodsAttrValue;

import java.util.List;

/**
 * <p>
 * 商品属性值表 Mapper 接口
 * </p>
 *
 * @author rstyro
 * @since 2020-06-08
 */
@Component
public interface GoodsAttrValueMapper extends BaseMapper<GoodsAttrValue> {
    public List<GoodsAttrValue> getGoodsAttrValues(@Param("ids") String ids);
}
