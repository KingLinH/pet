package com.kinglin.pet.model;

import com.kinglin.pet.entity.Owner;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @author huangjl
 * @description
 * @since 2024-03-19 18:38
 */
@Data
public class LoginUser implements UserDetails {

    private Owner owner;

    /**
     * 从数据库中查询到的权限存在这里
     */
    // private List<String> permissions;

    /**
     * 框架从这里获取权限信息，因为LoginUser实现的是UserDetails接口
     */
    //@JsonIgnore
    //@JSONField(serialize = false)
    //private List<GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        /*if (CollectionUtil.isNotEmpty(authorities)) {
            return authorities;
        }
        // 把permissions中String类型的权限信息封装成SimpleGrantedAuthority对象，存入authorities中
        authorities = permissions.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        return authorities;*/
        return null;
    }

    @Override
    public String getPassword() {
        return owner.getPassword();
    }

    @Override
    public String getUsername() {
        return owner.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
