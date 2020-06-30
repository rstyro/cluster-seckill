package top.lrshuai.skill.order.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 订单子表，订单明细表
 * </p>
 *
 * @author rstyro
 * @since 2020-06-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class OrderGoods implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 店铺ID
     */
    private Long shopId;

    /**
     * 商品ID
     */
    private Long goodsId;

    /**
     * skuid
     */
    private Long goodsSkuId;

    /**
     * 商品数量
     */
    private Integer goodsNum;

    /**
     * 优惠券减免金额
     */
    private BigDecimal couponVal;

    /**
     * 满减减免金额
     */
    private BigDecimal rewardVal;

    /**
     * 物流公司ID
     */
    private String expressId;

    /**
     * 物流编号
     */
    private String expressNo;

    /**
     * 收货时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime receiveTime;

    /**
     * 发货时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime deliveryTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 商品价格
     */
    private BigDecimal money;

    /**
     * 实际支付价格
     */
    private BigDecimal realMoney;

    /**
     * 版本号
     */
    @Version
    private Long version;

    /**
     * 商品类型：1-普通商品类型、2-秒杀商品类型、3-活动商品
     */
    private Integer goodsType;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;


}
