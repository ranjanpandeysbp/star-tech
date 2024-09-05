package com.ims.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column
    private ERole name;

    public void setName(ERole name) {
        this.name = name;
    }

    public ERole getName() {
        return name;
    }
}
