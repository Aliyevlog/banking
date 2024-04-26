package org.project.unitech.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends AbstractAuditEntity{

    @Column(name = "pin",unique=true)
    String pin;

    @Column(name = "password", nullable = false)
    String password;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "surname",nullable = false)
    private String surname;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    Set<Account> accounts;
}
