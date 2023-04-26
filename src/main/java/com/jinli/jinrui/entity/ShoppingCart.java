package com.jinli.jinrui.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@ApiModel(description = "购物车")
@TableName("shopping_cart")
public class ShoppingCart implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("购物车id")
    private Long id;

    @ApiModelProperty("名称")
    @TableField("name")
    private String name;

    @ApiModelProperty("用户id")
    @TableField("user_id")
    private Long userId;

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

    @ApiModelProperty("下单时间")
    @TableField("create_time")
    private LocalDateTime createTime;
}
