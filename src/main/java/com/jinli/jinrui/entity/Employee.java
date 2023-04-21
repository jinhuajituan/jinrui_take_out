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
@ApiModel(description = "用户")
@TableName("employee")
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户id")
    @TableField("id")
    private Long id;

    @ApiModelProperty(value = "用户名")
    @TableField("username")
    private String username;

    @ApiModelProperty(value = "账号名")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "密码")
    @TableField("password")
    private String password;

    @ApiModelProperty(value = "电话号码")
    @TableField("phone")
    private String phone;

    @ApiModelProperty(value = "性别")
    @TableField("sex")
    private String sex;

    @ApiModelProperty(value = "身份证号")
    @TableField("id_number")
    private String idNumber;

    @ApiModelProperty(value = "状态")
    @TableField("status")
    private Integer status;

    @ApiModelProperty(value = "创建时间")//插入时填充字段
    @TableField(fill = FieldFill.INSERT,value = "create_time")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间")//插入和更新时填充字段
    @TableField(fill = FieldFill.INSERT_UPDATE,value = "update_time")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "创建人")
    @TableField(fill = FieldFill.INSERT,value = "create_user")//插入时填充字段
    private Long createUser;

    @ApiModelProperty(value = "修改人")
    @TableField(fill = FieldFill.INSERT_UPDATE,value = "update_user")//插入和更新时填充字段
    private Long updateUser;

}
