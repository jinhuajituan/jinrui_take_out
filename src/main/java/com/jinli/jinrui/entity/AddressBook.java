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
@ApiModel(description = "地址管理")
@TableName("address_book")
public class AddressBook implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("地址簿id")
    @TableField("id")
    private Long id;

    @ApiModelProperty("用户id")
    @TableField("user_id")
    private Long userId;

    @ApiModelProperty("收货人")
    @TableField("consignee")
    private String consignee;

    @ApiModelProperty("手机号")
    @TableField("phone")
    private String phone;

    @ApiModelProperty("性别 0 女 1 男")
    @TableField("sex")
    private String sex;

    @ApiModelProperty("省级区划编号")
    @TableField("province_code")
    private String provinceCode;

    @ApiModelProperty("省级名称")
    @TableField("province_name")
    private String provinceName;

    @ApiModelProperty("市级区划编号")
    @TableField("city_code")
    private String cityCode;

    @ApiModelProperty("市级名称")
    @TableField("city_name")
    private String cityName;

    @ApiModelProperty("区级区划编号")
    @TableField("district_code")
    private String districtCode;

    @ApiModelProperty("区级名称")
    @TableField("district_name")
    private String districtName;

    @ApiModelProperty("详细地址")
    @TableField("detail")
    private String detail;


    @ApiModelProperty("标签")
    @TableField("label")
    private String label;

    @ApiModelProperty("是否默认 0 否 1是")
    @TableField("is_default")
    private Integer isDefault;

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


    //是否删除
    //private Integer isDeleted;
}
