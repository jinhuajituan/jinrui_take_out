package com.jinli.jinrui.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ApiModel(description = "订单明细")
@TableName("order_detail")
public class OrderDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("地址id")
    @TableField("id")
    private Long id;

    @ApiModelProperty("名称")
    @TableField("name")
    private String name;

    @ApiModelProperty("订单id")
    @TableField("order_id")
    private Long orderId;

    @ApiModelProperty("菜品id")
    @TableField("dish_id")
    private Long dishId;

    @ApiModelProperty("套餐id")
    @TableField("setmeal_id")
    private Long setmealId;

    @ApiModelProperty("口味")
    @TableField("dish_flavor")
    private String dishFlavor;

    @ApiModelProperty("数量")
    @TableField("number")
    private Integer number;

    @ApiModelProperty("金额")
    @TableField("amount")
    private BigDecimal amount;

    @ApiModelProperty("图片")
    @TableField("image")
    private String image;
}
