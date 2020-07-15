package com.fh.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author xiezhuang
 * @ClassName OrderBeanPo
 * @date 2020/7/6 21:59
 */

/**
 * 订单表
 */
@Data
@TableName("t_shop_order")
public class OrderBeanPo implements Serializable {
    @TableId(value = "id",type = IdType.ID_WORKER_STR)
    private String id;
    @TableField("userId")
    private Integer userId;
    @TableField("status")
    private Integer status;
    @TableField("totalPrice")
    private BigDecimal totalPrice;
    @TableField("totalCount")
    private Integer totalCount;
    @TableField("payType")
    private Integer payType;
    @TableField("addressId")
    private Integer addressId;
    @TableField("payTime")
    private LocalDateTime payTime;
    @TableField("createTime")
    private LocalDateTime createTime;


}
