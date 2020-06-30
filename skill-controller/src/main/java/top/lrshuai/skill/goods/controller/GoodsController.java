package top.lrshuai.skill.goods.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import top.lrshuai.skill.base.BaseController;
import top.lrshuai.skill.commons.annotation.Idempotent;
import top.lrshuai.skill.commons.result.Result;
import top.lrshuai.skill.goods.service.IGoodsService;
import top.lrshuai.skill.goods.service.IGoodsSkillService;

/**
 * <p>
 * 商品表 前端控制器
 * </p>
 *
 * @author rstyro
 * @since 2020-06-08
 */
@RestController
@RequestMapping("/goods/goods")
public class GoodsController extends BaseController {

    @Autowired
    private IGoodsService goodsService;

    @Autowired
    private IGoodsSkillService goodsSkillService;

    /**
     * 商品详情
     * @param goodsId
     * @return
     */
    @GetMapping("/detail")
    public Result getGoodsDetail(String goodsId){
        return goodsService.getGoodsDetail(goodsId);
    }

    /**
     * 秒杀商品列表
     * @return
     */
    @GetMapping("/skillGoods")
    public Result skillGoods(){
        return goodsSkillService.getGoodsSkillList();
    }

    /**
     * 加载 秒杀商品
     * @return
     */
    @GetMapping("/loadGoods")
    @Idempotent
    public Result loadGoods(){
        return goodsSkillService.reloadGoods();
    }

}
