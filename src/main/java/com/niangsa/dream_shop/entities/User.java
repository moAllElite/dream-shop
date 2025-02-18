    package com.niangsa.dream_shop.entities;

    import jakarta.persistence.*;
    import lombok.AllArgsConstructor;
    import lombok.Getter;
    import lombok.NoArgsConstructor;
    import lombok.Setter;
    import org.hibernate.annotations.NaturalId;

    import java.util.List;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Entity
    public class User {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String username;
        private String firstName;
        private String lastName;
        @NaturalId
        private String email;
        private String password;
        @OneToOne(mappedBy = "user",cascade = CascadeType.ALL)
        private Cart cart;
        @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true)
        private List<Order> orders;
    }
