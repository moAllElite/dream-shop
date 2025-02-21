    package com.niangsa.dream_shop.entities;

    import jakarta.persistence.*;
    import lombok.*;
    import org.hibernate.annotations.NaturalId;
    import org.springframework.security.core.GrantedAuthority;
    import org.springframework.security.core.authority.SimpleGrantedAuthority;
    import org.springframework.security.core.userdetails.UserDetails;

    import java.util.Collection;
    import java.util.HashSet;
    import java.util.List;
    import java.util.stream.Collectors;

    @Getter
    @Setter@Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Entity
    public class User implements UserDetails {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String firstName;
        private String lastName;
        @NaturalId
        private String email;
        private String password;
        @OneToOne(mappedBy = "user",cascade = CascadeType.ALL)
        private Cart cart;
        @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true)
        private List<Order> orders;
        @ManyToMany(cascade = CascadeType.MERGE,fetch = FetchType.EAGER)
        @JoinTable(
                name = "user_roles",
                joinColumns = @JoinColumn(name = "user_id",referencedColumnName = "id"),
                inverseJoinColumns = @JoinColumn(name = "role_id",referencedColumnName = "id")
        )
        private Collection<Role> roles = new HashSet<>();

        /**
         * @return Collection of GrantedAuthority
         */
        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return  roles.stream()
                    .map(role -> new SimpleGrantedAuthority(role.getName()))
                    .collect(Collectors.toList());
        }

        /**
         * @return Email of User
         */
        @Override
        public String getUsername() {
            return email;
        }


    }
