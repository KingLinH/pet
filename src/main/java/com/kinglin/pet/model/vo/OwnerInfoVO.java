package com.kinglin.pet.model.vo;

import lombok.Data;

/**
 * @author huangjl
 * @description 用户信息VO
 * @since 2023-05-12 14:03
 */
@Data
public class OwnerInfoVO {

    private String username;

    private String avatarUrl;

    private String gender;

    private String city;

    private String country;

    private String province;

}
