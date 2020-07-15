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
 * @ClassName PayLogBeanPo
 * @date 2020/7/6 22:08
 */
@Data
@TableName("t_paylog")
public class PayLogBeanPo implements Serializable {
    @TableId(value = "outTradeNo",type = IdType.INPUT)
    private String outTradeNo;
    @TableField(value = "orderId")
    private String orderId;
    @TableField(value = "userId")
    private Integer userId;
    @TableField(value = "transactionId")
    private String transactionId;
    @TableField(value = "createTime")
    private LocalDateTime createTime;
    @TableField(value = "payTime")
    private LocalDateTime payTime;
    @TableField(value = "payMoney")
    private BigDecimal payMoney;
    @TableField(value = "payType")
    private Integer payType;
    @TableField(value = "payStatus")
    private Integer payStatus;

}
