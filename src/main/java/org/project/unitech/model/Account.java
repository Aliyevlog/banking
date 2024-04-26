package org.project.unitech.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.project.unitech.model.enums.Status;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "accounts")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Account extends AbstractAuditEntity {

    @Column(name = "iban", unique = true, nullable = false)
    String iban;

    @Column(nullable = false)
    Status status;

    @Column(nullable = false)
    BigDecimal balance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    @JsonIgnore
    User user;
}
