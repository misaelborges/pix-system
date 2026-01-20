package com.misael.pix_sistem.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity(name = "accounts")
@Table(name = "accounts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Accounts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Column(unique = true)
    private String cpf;

    @Column(unique = true)
    private String email;
    private String phone;

    @PositiveOrZero
    private BigDecimal balance = BigDecimal.valueOf(0.00);

    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private OffsetDateTime created_at;

    @UpdateTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private OffsetDateTime updated_at;

    public void debit(BigDecimal amount) {
        setBalance(getBalance().subtract(amount));
    }

    public void credit(BigDecimal amount) {
        setBalance(getBalance().add(amount));
    }
}
