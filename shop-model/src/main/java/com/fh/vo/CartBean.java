package com.fh.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author xiezhuang
 * @ClassName CartBean
 * @date 2020/7/5 13:52
 */
@Data
public class CartBean implements Serializable {

    private Integer productId;
    private String productName;
    private Integer stock;
    private BigDecimal price;
    private BigDecimal subtotal;
    private Integer num;
    private boolean checked=false;


}
