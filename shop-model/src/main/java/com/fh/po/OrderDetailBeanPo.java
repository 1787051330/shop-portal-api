package com.fh.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author xiezhuang
 * @ClassName OrderDetailBeanPo
 * @date 2020/7/6 22:04
 */
@Data
@TableName("t_shop_order_detail")
public class OrderDetailBeanPo implements Serializable {
    @TableId(value = "orderId",type = IdType.UUID)
    private String orderId;
    @TableField("userId")
    private Integer userId;
    @TableField("productId")
    private Integer productId;
    @TableField("productName")
    private String productName;
    @TableField("price")
    private BigDecimal price;
    @TableField("subTotalPrice")
    private BigDecimal subTotalPrice;
    @TableField("image")
    private String image;
    @TableField("count")
    private Integer count;

}
