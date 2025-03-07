package com.niangsa.dream_shop.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "user_id")
    private Cart cart;
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Order> orders;
    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_role",
            joinColumns =@JoinColumn(name = "user_id",columnDefinition = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id",
                    referencedColumnName = "id"
            )
    )
    private Collection<Role> roles = new ArrayList<>();
    /**
     * @return Collections of GrantedAuthority
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .toList();
    }
}
