package com.kinglin.pet.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户信息表
 * </p>
 *
 * @author huangjl
 * @since 2023-05-12
 */
@Data
@TableName("owner")
@ApiModel(value = "Owner对象", description = "用户信息表")
public class Owner implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id")
    private Long id;

    @ApiModelProperty("真实姓名")
    @TableField("real_name")
    private String realName;

    @ApiModelProperty("用户名")
    @TableField("username")
    private String username;

    @ApiModelProperty("手机号")
    @TableField("phone")
    private String phone;

    @ApiModelProperty("密码")
    @TableField("password")
    private String password;

    @ApiModelProperty("盐值")
    @TableField("salt")
    private String salt;

    @ApiModelProperty("性别")
    @TableField("gender")
    private String gender;

    @ApiModelProperty("邮箱")
    @TableField("email")
    private String email;

    @ApiModelProperty("头像")
    @TableField("avatar_url")
    private String avatarUrl;

    @ApiModelProperty("国家")
    @TableField("country")
    private String country;

    @ApiModelProperty("省份")
    @TableField("province")
    private String province;

    @ApiModelProperty("城市")
    @TableField("city")
    private String city;

    @ApiModelProperty("openId")
    @TableField("open_id")
    private String openId;

    @ApiModelProperty("unionId")
    @TableField("union_id")
    private String unionId;

    @ApiModelProperty("token")
    @TableField("token")
    private String token;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;

}
