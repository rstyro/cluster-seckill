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
 *
 * </p>
 *
 * @author rstyro
 * @since 2020-06-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Orders implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 商品价格未进行任何折扣的总价格
     */
    private BigDecimal goodsMoney;

    /**
     * 订单总价格
     */
    private BigDecimal totalMoney;

    /**
     * 实际支付的价格，进行各种折扣之后的金额
     */
    private BigDecimal realTotalMoney;

    /**
     * 用于退款的订单支付总金额
     */
    private BigDecimal totalPayFee;

    /**
     * 支付方式：1--微信，2--支付宝
     */
    private Integer payType;

    /**
     * 是否支付
     */
    private Boolean isPay;

    /**
     * 支付时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime payTime;

    /**
     * 收货姓名
     */
    private String receiveName;

    /**
     * 收货电话
     */
    private String receiveMobile;

    /**
     * 收货地址
     */
    private String receiveAddress;

    /**
     * 订单备注
     */
    private String remark;

    /**
     * 版本号
     */
//    @Version
    private Long version;

    /**
     * 订单状态：-3:用户拒收 -2:未付款的订单 -1：用户取消 0:待发货 1:配送中 2:用户确认收货
     */
    private Integer status;

    /**
     * 下单时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;




}
