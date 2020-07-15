package com.fh.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author xiezhuang
 * @ClassName ShopUserBeanPo
 * @date 2020/6/28 17:17
 */
@Data
@TableName("t_shop_user")
public class ShopUserBeanPo implements Serializable {
    @TableId(value = "shopId",type = IdType.AUTO)
    private Integer shopId;
    @TableField("loginName")
    private String loginName;
    @TableField("phone")
    private String phone;
    @TableField("password")
    private String password;
    @TableField("cartId")
    private String cartId;
    @TableField("userStarts")
    private Integer userStarts;
    @TableField("modifiedTime")
    private LocalDateTime modifiedTime;
    @TableField("images")
    private String images;

}
