package com.fh.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xiezhuang
 * @ClassName AddressBeanPo
 * @date 2020/7/6 13:33
 */
@Data
@TableName("t_shop_address")
public class AddressBeanPo implements Serializable {
    /**
     * 主键ID
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    /**
     * 收货人
     */
    @TableField("consignee")
    private String consignee;
    /**
     * 收货地址
     */
    @TableField("address")
    private String address;
    /**
     * 收货人的手机号
     */
    @TableField("phone")
    private String phone;
    /**
     * 收货邮箱
     */
    @TableField("email")
    private String email;
    /**
     * 地址类型
     */
    @TableField("alias")
    private String alias;
    /**
     * 对应的userId
     */
    @TableField("creatorId")
    private Integer creatorId;
    /**
     * 添加地址时间
     */
    @TableField("createTime")
    private Date createTime;

}
