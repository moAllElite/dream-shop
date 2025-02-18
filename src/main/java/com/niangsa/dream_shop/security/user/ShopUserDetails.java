package com.niangsa.dream_shop.security.user;

import com.niangsa.dream_shop.entities.User;
import lombok.*;
import org.hibernate.annotations.NaturalId;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShopUserDetails implements UserDetails {
    //params
    private Long id;
    private String username;
    private String email;
    private String password;
    private Collection<GrantedAuthority> authorities;

    public static ShopUserDetails buildUserDetail(User user){
       List<GrantedAuthority> grantedAuthorities = user.getRoles().stream()
               .map(role -> new SimpleGrantedAuthority(role.getName()))
               .collect(Collectors.toList());
        return ShopUserDetails.builder().authorities(grantedAuthorities)
                .email(user.getEmail())
                .password(user.getPassword())
                .username(user.getUsername())
                .build();
    }

    /**
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return List.of();
    }

    /**
     * @return
     */
    @Override
    public String getPassword() {
        return "";
    }

    /**
     * @return
     */
    @Override
    public String getUsername() {
        return "";
    }

    /**
     * @return
     */
    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    /**
     * @return
     */
    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    /**
     * @return
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    /**
     * @return
     */
    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
