package com.jinli.jinrui.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "用户信息")
@TableName("user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户id")
    private Long id;

    @ApiModelProperty("姓名")
    @TableField("name")
    private String name;

    @ApiModelProperty("手机号")
    @TableField("phone")
    private String phone;

    @ApiModelProperty("性别 0 女 1 男")
    @TableField("sex")
    private String sex;

    @ApiModelProperty("身份证号")
    @TableField("id_number")
    private String idNumber;

    @ApiModelProperty("头像")
    @TableField("avatar")
    private String avatar;

    @ApiModelProperty("状态 0:禁用，1:正常")
    @TableField("status")
    private Integer status;
}
