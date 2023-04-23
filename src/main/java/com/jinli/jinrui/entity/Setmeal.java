package com.jinli.jinrui.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@ApiModel(value = "套餐表")
@TableName("setmeal")
public class Setmeal {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "套餐id")
    @TableField("id")
    private Long id;

    @ApiModelProperty(value = "分类id")
    @TableField("category_id")
    private Long categoryId;

    @ApiModelProperty(value = "套餐名称")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "套餐价格")
    @TableField("price")
    private BigDecimal price;

    @ApiModelProperty(value = "状态  0:停用 1:启用")
    @TableField("status")
    private Integer status;

    @ApiModelProperty(value = "编码")
    @TableField("code")
    private String code;

    @ApiModelProperty(value = "描述信息")
    @TableField("description")
    private String description;

    @ApiModelProperty(value = "图片")
    @TableField("image")
    private String image;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT,value = "create_time")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE,value = "update_time")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "创建人")
    @TableField(fill = FieldFill.INSERT,value = "create_user")
    private Long createUser;

    @ApiModelProperty(value = "修改人")
    @TableField(fill = FieldFill.INSERT_UPDATE,value = "update_user")
    private Long updateUser;
}
