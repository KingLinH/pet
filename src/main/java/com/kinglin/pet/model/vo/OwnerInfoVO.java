package com.kinglin.pet.model.vo;

import lombok.Data;

/**
 * @author huangjl
 * @description 用户信息VO
 * @since 2023-05-12 14:03
 */
@Data
public class OwnerInfoVO {

    private String realName;

    private String displayName;

    private String gender;

    private String phone;

    private String email;

}
