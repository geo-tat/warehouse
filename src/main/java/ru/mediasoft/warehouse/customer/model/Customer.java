package ru.mediasoft.warehouse.customer.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "customer")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_sequence")
    @SequenceGenerator(name = "customer_sequence", sequenceName = "customer_sequence", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
   private Long id;
    @Column(name = "login", unique = true)
    @NotNull
   private String login;
    @Column(name = "email")
    @NotNull
   private String email;


}
