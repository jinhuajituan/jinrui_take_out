package com.jinli.jinrui.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@ApiModel(description = "菜品及套餐分类")
@TableName("category")
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "菜品id")
    @TableField("id")
    private Long id;

    @ApiModelProperty(value = "类型 1 菜品分类； 2 套餐分类")
    @TableField("type")
    private Integer type;

    @ApiModelProperty(value = "分类名称")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "顺序")
    @TableField("sort")
    private Integer sort;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT,value = "create_time")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.UPDATE,value = "update_time")
    private LocalDateTime updateTime;


    @ApiModelProperty(value = "创建人")
    @TableField(fill = FieldFill.INSERT,value = "create_user")
    private Long createUser;

    @ApiModelProperty(value = "修改人")
    @TableField(fill = FieldFill.INSERT_UPDATE,value = "update_user")
    private Long updateUser;
}
