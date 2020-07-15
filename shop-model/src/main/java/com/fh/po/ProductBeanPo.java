package com.fh.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author xiezhuang
 * @ClassName ProductBeanPo
 * @date 2020/6/30 12:32
 */
@Data
@TableName("t_shop_product")
public class ProductBeanPo {
    @TableId(value = "id",type = IdType.AUTO)
    /**
     * id
     * 商品主键ID
     */
    private Integer id;
    @TableField("brandId")
    /**
     * brandId
     * 对应的品牌ID
     */
    private Integer brandId;
    @TableField("name")
    /**
     * name
     * 商品名称
     */
    private String name;
    @TableField("subtitle")
    /**
     * subtitle
     * 宣传标题
     */
    private String subtitle;
    @TableField("mainImg")
    /**
     * mainImg
     * 主图片
     */
    private String mainImg;
    @TableField("subImgs")
    private String subImgs;
    @TableField("detail")
    private String detail;
    @TableField("attributeList")
    private String attributeList;
    @TableField("price")
    private BigDecimal price;
    @TableField("stock")
    private Integer stock;
    @TableField("status")
    private Integer status;
    @TableField("createTime")
    private LocalDateTime createTime;
    @TableField("updateTime")
    private LocalDateTime updateTime;

}
