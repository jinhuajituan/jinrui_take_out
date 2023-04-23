package com.jinli.jinrui.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
菜品口味
 */
@Data
@ApiModel(value = "菜品口味关系表")
@TableName("dish_flavor")
public class DishFlavor implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "口味id")
    @TableField("id")
    private Long id;

    @ApiModelProperty(value = "菜品id")
    @TableField("dish_id")
    private Long dishId;

    @ApiModelProperty(value = "口味名称")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "口味数据list")
    @TableField("value")
    private String value;


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

    @ApiModelProperty(value = "是否删除")
    @TableField("isDeleted")
    private Integer isDeleted;

}
