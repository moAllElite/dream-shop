package com.niangsa.dream_shop.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.Collection;
import java.util.HashSet;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToMany(mappedBy = "roles")
    private Collection<User> users = new HashSet<>();

    //constructor with params
    public Role(String name) {
        this.name = name;
    }


}
