package top.lrshuai.skill.goods.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 商品表
 * </p>
 *
 * @author rstyro
 * @since 2020-06-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Goods implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 店铺ID
     */
    private Long shopId;

    /**
     * 商品编号
     */
    private String goodsNo;

    /**
     * 商品价格
     */
    private BigDecimal goodsPrice;

    /**
     * 商品所有规格总库存
     */
    private Integer stock;

    /**
     * 商品总销量
     */
    private Integer salesNum;

    /**
     * 总访问量
     */
    private Integer visitNum;

    /**
     * 总评论数
     */
    private Integer commentNum;

    /**
     * 商品单位
     */
    private String goodsUnit;

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 商品标题
     */
    private String goodsTitle;

    /**
     * 商品图片
     */
    private String goodsImg;

    /**
     * 商品视频
     */
    private String goodsVideo;

    /**
     * 商品详情
     */
    private String goodsDetail;

    /**
     * 商品状态：-1:违规 0:未审核 1:已审核
     */
    private Integer goodsStatus;

    /**
     * 上架时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime saleTime;

    /**
     * 是否上架
     */
    private Boolean isSale;

    /**
     * 是否新品
     */
    private Boolean isNew;

    /**
     * 邮费
     */
    private BigDecimal logisticsFee;

    /**
     * 版本号
     */
    private Long version;

    /**
     * 是否删除
     */
    private Boolean isDel;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 创建人
     */
    private Long createBy;


    @TableField(exist = false)
    private List<GoodsAttrValue> attrs;


}
